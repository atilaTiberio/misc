package mx.iteso;


import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseaMedsDOM {



    public static void main(String[] args) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();


        Document document = builder.parse(new File( "total.html" ));
        document.getDocumentElement().normalize();

        NodeList divPages=document.getElementsByTagName("div");
        String infoActual="",tmp="",medicamento="",generalidades="",riesgoEmbarazo="",efectosAdversos="",contraindicaciones="",interacciones="";

        Integer nodoNombreMedicamento,estado=0;

        List<String> descripcion= new ArrayList<String>();
        List<String> claves= new ArrayList<String>();
        List<String> administracion= new ArrayList<String>();
        List<String> indicaciones= new ArrayList<String>();
        List<String> generalidadesL= new ArrayList<String>();
        List<String> riesgoEm= new ArrayList<String>();
        List<String> efectosL= new ArrayList<String>();
        List<String> contraL= new ArrayList<String>();
        List<String> interL= new ArrayList<String>();

        List<String> onlyMeds= new ArrayList<String>();
        List<String> medsEmpty= new ArrayList<String>();

        cuentaMeds();

        /*System.out.println("*******claves****");
        System.out.println(StringUtils.join(claves,","));
        System.out.println("*******descripcion****");
        System.out.println(StringUtils.join(descripcion,","));
        System.out.println("*******indicaciones****");
        System.out.println(StringUtils.join(indicaciones,","));
        System.out.println("*******administracion****");
        System.out.println(StringUtils.join(administracion,","));
        System.out.println("*******generalidades****");
        System.out.println(StringUtils.join(generalidadesL,","));
        System.out.println("*******riesgo****");
        System.out.println(StringUtils.join(riesgoEm,","));
        System.out.println("*******efectos****");
        System.out.println(StringUtils.join(efectosL,","));
        System.out.println("*******contra****");
        System.out.println(StringUtils.join(contraL,","));
        System.out.println("*******interacciones****");
        System.out.println(StringUtils.join(interL,","));
        */


        System.out.println("Total de medicamentos procesados: "+onlyMeds.size());
        System.out.println(StringUtils.join(onlyMeds,","));
        System.out.println("Total de medicamentos Vacios: "+medsEmpty.size());
        System.out.println(StringUtils.join(medsEmpty,","));




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
        String[] tokensEspacio=null;
        boolean encontrado=false;



        for(String infoActual: tokensPunto) {

                if (infoActual.contains("postoperatorio")||
                        infoActual.contains("agudo")||
                        infoActual.contains("moderado")||
                        infoActual.contains("crónico")||
                        infoActual.contains("severo") ||
                        isContain(infoActual,"dolor") ||
                        isContain(infoActual,"anestesia")||
                    infoActual.contains("relajación") ||
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
                    //infoActual.contains("local") ||
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
                    infoActual.contains("candidiasis")




                        ) {
                    retorno = infoActual;

                    encontrado=true;
                    break;
                }
            }

        return retorno;
    }

    private static boolean isContain(String source, String subItem){
        String pattern = "\\b"+subItem+"\\b";
        Pattern p=Pattern.compile(pattern);
        Matcher m=p.matcher(source);
        return m.find();
    }


    public static void cuentaMeds() throws Exception{

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();


        Document document = builder.parse(new File( "omeds.html" ));
        document.getDocumentElement().normalize();

        NodeList parrafos=document.getElementsByTagName("p");

        String contenido;
        String[] tokens;

        List<String> medicamentos=new ArrayList<String>();

        Integer i=0;

        for(int it=0; it<parrafos.getLength();it++){

            contenido=parrafos.item(it).getTextContent().trim();

            if(contenido.length()>1){
                tokens=contenido.split("\n");

                if(tokens.length>1 ) {

                    for(String ite: tokens) {

                        ite=ite.trim();

                        if(ite.length()>0 && !ite.contains("INDICE GENERAL")&&!ite.contains("Cuadro") && !ite.contains("Grupo") &&!ite.contains("Catálogo")&& !ite.contains("Sustitutos del Plasma")&& !ite.contains("Inmunoglobulinas")){
                          //  System.out.println(ite);
                            medicamentos.add(ite.trim());
                            i++;
                        }


                    }
                }

            }
        }

        List<String> meds = Medicamentos_DOM.getMeds();
        List<String> inter=new ArrayList<String>(meds);
        inter.removeAll(medicamentos);

        System.out.println(StringUtils.join(medicamentos,","));

       /* System.out.println(StringUtils.join(inter,"\n"));
        System.out.println(inter.size());
        System.out.println(i);
        */

    }

}
