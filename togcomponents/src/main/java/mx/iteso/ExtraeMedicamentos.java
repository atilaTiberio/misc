package mx.iteso;


import org.apache.commons.collections.map.MultiValueMap;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class ExtraeMedicamentos {


    public static List<String> medsFiltro;

    public static List<String> descripcion;
    public static List<String> claves;
    public static List<String> administracion;
    public static List<String> indicaciones;
    public static List<String> generalidadesL;
    public static List<String> riesgoEm;
    public static List<String> efectosL;
    public static List<String> contraL;
    public static List<String> interL;
    public static List<String> medsEmpty;

    public static void main(String[] args) throws Exception {

medicamentos();

    }

    public static void medicamentos() throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();


        Document document = builder.parse(new File( "total.html" ));
        document.getDocumentElement().normalize();

        NodeList parrafos=document.getElementsByTagName("p");

        List<String> procesarMeds=Medicamentos_DOM.getMeds();
        descripcion= new ArrayList<String>();
        claves= new ArrayList<String>();
        administracion= new ArrayList<String>();
        indicaciones= new ArrayList<String>();
        generalidadesL= new ArrayList<String>();
        riesgoEm= new ArrayList<String>();
        efectosL= new ArrayList<String>();
        contraL= new ArrayList<String>();
        interL= new ArrayList<String>();
        medsEmpty= new ArrayList<String>();

        BufferedWriter wb = new BufferedWriter(new FileWriter("medicamentos.txt"));



        String info;
        Integer currentMed=0;
        String med="";
        String nextMed="";
        boolean actual=false;
        boolean siguiente=false;
        Integer estado=1;
        Integer lines=0;
        String infoMed="";

        Map<String,List<String>> secciones=null;
        MultiValueMap docs = new MultiValueMap();
        List<String> contenido=null;


        for(int ite=0; ite<parrafos.getLength();ite++){

            info=parrafos.item(ite).getTextContent().trim();
            infoMed=info.replace("\n","").trim();
            med=procesarMeds.get(currentMed);

            if((currentMed+1)<procesarMeds.size())
                nextMed=procesarMeds.get(currentMed+1);

            if(info.length()>1 ) {

                if(!actual){
                    actual = infoMed.contains(med);
                }
                siguiente = infoMed.contains(nextMed);


                if (actual&&!siguiente) {

                    if(!info.contains("INDICE")&& !info.contains("Grupo N") && !info.contains("Clave")) {
                            estado=parseValores(info.toLowerCase(),estado,med);
                        }
                    lines++;
                }


                if (siguiente) {


                        //secciones=new HashMap<String, List<String>>();
                        contenido= new ArrayList<>();

                        /*secciones.put("descripcion",descripcion);
                        secciones.put("indicaciones", indicaciones);
                        secciones.put("administracion", administracion);
                        */

                        contenido.addAll(descripcion);
                        contenido.addAll(indicaciones);
                        contenido.addAll(administracion);
                        docs.put(med,contenido);

                    if(indicaciones.size()==0)
                        medsEmpty.add(med);

                        descripcion= new ArrayList<String>();
                        claves= new ArrayList<String>();
                        administracion= new ArrayList<String>();
                        indicaciones= new ArrayList<String>();
                        generalidadesL= new ArrayList<String>();
                        riesgoEm= new ArrayList<String>();
                        efectosL= new ArrayList<String>();
                        contraL= new ArrayList<String>();
                        interL= new ArrayList<String>();

                    med = nextMed;
                    currentMed++;
                    lines=0;
                    actual=siguiente;
                    siguiente=false;
                    estado=1;
                }
            }



        }


        System.out.println(currentMed+"<-->"+procesarMeds.get(currentMed));
        System.out.println(docs.totalSize());

       /* docs.forEach( (k,l) -> {
                    System.out.println(k + "<->" + l.);
                }
        );
        */





        Set<String> medsProcesar= docs.keySet();

        for(String ite: medsProcesar){


            /*
            informacion del medicamento
             */
            List<List<String>> as= (List<List<String>>) docs.get(ite);

            /*
              N cadenas de texto a las que le hare un mapReduce
             */
            List<String> ss= as.stream()
                    .flatMap(l -> l.stream()).
                    collect(Collectors.toList());

            /*
            texto reducido
             */
            Map<String,Long> a=ss.stream()
                    .flatMap(l ->
                            {
                                String tmp=l.replaceAll(",","").replaceAll("[0-9]{1,7}","");
                                tmp=tmp.replaceAll("%","");
                                return Arrays.stream(tmp.split(" "));
                            }
                    )
                    .filter(l -> l.length()>0)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            System.out.println(ite);

            System.out.println(StringUtils.join(ss," "));
            List<String> maped= new ArrayList<String>();

            for(Map.Entry<String,Long> iterador: a.entrySet()){
                maped.add(iterador.getValue()+"-"+iterador.getKey());
            }
            Collections.sort(maped,Comparator.reverseOrder());

            System.out.println(StringUtils.join(maped," "));

            wb.write(ite);
            wb.write("|");
            wb.write(StringUtils.join(ss," "));
            wb.write("|");
            wb.write(StringUtils.join(maped," "));
            wb.newLine();


        }

        wb.close();



















    }

    public static Integer parseValores(String infoActual,Integer estado,String medicamento){


        switch (estado) {

            case 1:
                if(infoActual.startsWith("0")){ //Clave de medicamento
                    claves.add(cleanString(infoActual));
                }else{

                    if(candidatoDescripcion(infoActual)){
                        String ad=candidatoAdministracion(infoActual);
                        String ind=candidatoIndicacion(infoActual);
                        descripcion.add(cleanString(infoActual));

                        if(ad!=null)
                            administracion.add(cleanString(ad));
                        if(ind!=null)
                            indicaciones.add(cleanString(ind));

                    }else{
                        String ad=candidatoAdministracion(infoActual);
                        String ind=candidatoIndicacion(infoActual);

                        if(infoActual.contains("generalidades")) {
                            generalidadesL.add(cleanString(infoActual));
                            estado = 2;
                        }else {
                            if (ad != null && ind != null) {
                                administracion.add(cleanString(infoActual));
                                indicaciones.add(cleanString(ind));
                            } else {
                                if (ad != null) {
                                    administracion.add(cleanString(infoActual));
                                } else {
                                        indicaciones.add(cleanString(infoActual));

                                }
                            }
                        }

                    }
                }

                break;
            case 2:
                if(infoActual.contains("riesgo en el embarazo")){
                    riesgoEm.add(cleanString(infoActual));
                    estado=3;
                }
                else{
                    generalidadesL.add(cleanString(infoActual));
                }
                break;

            case 3:
                if(infoActual.contains("efectos adversos")){
                    efectosL.add(cleanString(infoActual));
                    estado =4;
                }else{
                    riesgoEm.add(cleanString(infoActual));
                }

                break;
            case 4:

                if(infoActual.contains("contraindicaciones y precauciones")){
                    contraL.add(cleanString(infoActual));
                    estado=5;
                }else{
                    efectosL.add(cleanString(infoActual));
                }

                break;

            case 5:

                if(infoActual.contains("interacciones")){
                    interL.add(cleanString(infoActual));
                    estado=6;
                }else{
                    contraL.add(cleanString(infoActual));
                }

                break;

            case 6:
                    interL.add(cleanString(infoActual));
                break;
        }

        return estado;

    }

    public static String candidatoAdministracion(String infoActual){

        String retorno=null;
        if (infoActual.contains("infusión") ||
                infoActual.contains("intravenosa") ||
                isContain(infoActual,"oral") ||
                isContain(infoActual,"día") ||
                infoActual.contains("adulto:") ||
                infoActual.contains("adultos:") ||
                infoActual.contains("niños:") ||
                infoActual.contains("horas") ||
                infoActual.contains("peso corporal")||
                infoActual.contains("inyectable")||
                infoActual.contains("ampolleta") ||
                infoActual.contains("aplicar")
                ) {
            retorno=infoActual;

        }

        return retorno;
    }
    public static String candidatoIndicacion(String info){

        String retorno=null;
        String[] tokensPunto=info.split("\\.");
        boolean encontrado=false;


        if(info.length()<=3 &&isContain(info,"[0-9]{1,3}")){
            return null;

        }


        for(String infoActual: tokensPunto) {

            if (infoActual.contains("postoperatorio")||
                    infoActual.contains("agudo")||
                    infoActual.contains("moderado")||
                    infoActual.contains("crónico")||
                    infoActual.contains("severo") ||
                    isContain(infoActual,"dolor") ||
                     isContain(infoActual,"anestesia")||
                    infoActual.contains("relaja") ||
                    infoActual.contains("inducción") ||
                    infoActual.contains("opioides")||
                    infoActual.contains("opiáceos")||
                    infoActual.contains("intoxicación")||
                    infoActual.contains("hiper")||
                    infoActual.contains("insuficiencia") ||
                    infoActual.contains("taquicardia")||
                    infoActual.contains("hipo")||
                    infoActual.contains("dermatitis")||
                    infoActual.contains("psoriasis")||
                    infoActual.contains("cutánea")||
                    infoActual.contains("diabetes")||
                    infoActual.contains("colesterol") ||
                    infoActual.contains("dismo") ||
                    infoActual.contains("lepra") ||
                    infoActual.contains("losis") ||
                    isContain(infoActual,"hepatitis") ||
                    infoActual.contains("farínge") ||
                    infoActual.contains("micosis") ||
                    infoActual.contains("amibiasis") ||
                    infoActual.contains("asma") ||
                    isContain(infoActual,"inmunodeficiencia humoral") ||
                    infoActual.contains("rinitis") ||
                    infoActual.contains("diarre") ||
                    infoActual.contains("estreñimiento") ||
                    infoActual.contains("hemorragia") ||
                    infoActual.contains("anovulación") ||
                    infoActual.contains("síndrome") ||
                    infoActual.contains("anticoagulante") ||
                    infoActual.contains("bronquitis") ||
                    infoActual.contains("deficiencia") ||
                    infoActual.contains("disfunción") ||
                    infoActual.contains("tos irritativa") ||
                    isContain(infoActual,"tos")||
                    isContain(infoActual,"broncodilatador")||
                    infoActual.contains("epilepsia")||
                    infoActual.contains("ansiedad")||
                    infoActual.contains("alzheimer")||
                    infoActual.contains("vértigo")||
                    infoActual.contains("parkinson")||
                    infoActual.contains("antioxidante")||
                    infoActual.contains("oftálm")||
                    infoActual.contains("leucemia")||
                    infoActual.contains("cáncer")||
                    infoActual.contains("hodgkin")||
                    infoActual.contains("anticonce")||
                    infoActual.contains("depresión")||
                    infoActual.contains("psicosis")||
                    infoActual.contains("insomnio")||
                    infoActual.contains("candidiasis") ||
                    infoActual.contains("parto prematuro")||
                    infoActual.contains("pubertad precoz")
                    ) {
                retorno = info;
                break;
            }
        }

        return retorno;
    }

    private static boolean candidatoDescripcion(String infoActual){
        boolean retorno=false;
        if(infoActual.contains("crema")||infoActual.contains("envase")
                ||infoActual.contains("tableta")
                ||infoActual.contains("parche")
                ||infoActual.contains("supositorio")
                || isContain(infoActual,"mg")
                || isContain(infoActual,"oral")
                ){
            retorno=true;
        }

        return retorno;

    }

    private static boolean isContain(String source, String subItem){
        String pattern = "\\b"+subItem+"\\b";
        Pattern p=Pattern.compile(pattern);
        Matcher m=p.matcher(source);
        return m.find();
    }


    public static String cleanString(String info){

        String ret=info.replace("\n","")
                .replaceAll(":","")
                .replaceAll("\\.","")
                .replaceAll(",","")
                .replaceAll(" +"," ")
                .toLowerCase()
                .trim();


        return ret;

    }
}
