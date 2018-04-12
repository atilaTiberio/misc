package mx.iteso;



import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MedicamentosDOM {



    public static void main(String[] args) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();


        Document document = builder.parse(new File( "total.html" ));
        document.getDocumentElement().normalize();
        /*
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr = xpath.compile("//div[@style]");
        NodeList nl = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
        */

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

        System.out.println("------------");




        for(int i=0; i<divPages.getLength();i++){

            Node n= divPages.item(i);

            NodeList parrafos = n.getChildNodes();

            for (int it = 3; it < parrafos.getLength(); it++) {
                Node pa = parrafos.item(it);
                NodeList child=pa.getChildNodes();
                NodeList ex=null;

                if(medicamento.equals("INMUNOGLOBULINA HUMANA"))
                    System.out.print("");

                //child.getLength() > 0 &&
                infoActual = pa.getTextContent().trim().toLowerCase();
                if (infoActual.length()>0&&!infoActual.contains("indice")) {
                    switch (estado) {
                        case 0: //nombre del medicamento

                            Integer sub= child.item(0).getChildNodes().getLength();
                            if(sub>0 ){
                                 ex= ((Element)child.item(0)).getElementsByTagName("i");
                                 if(ex.item(0)!=null) {
                                     medicamento = ex.item(0).getTextContent().replace("\n", "").replace("Cuadro Básico", "").replace("Catálogo", "").trim();
                                     onlyMeds.add(medicamento);
                                     estado = 1;
                                 }
                            }

                            break;

                        case 1: /*Tabla, claves de medicamento pueden ser varias claves
                                 */

                            if(infoActual.contains("clave")){
                                break;
                            }
                            if(infoActual.startsWith("0")){ //Clave de medicamento
                                claves.add(infoActual.replace(" ","").replace("\n",","));
                            }else{
                                String esInd=candidatoIndicacion(infoActual);

                                if(esInd==null && (infoActual.contains("crema")||infoActual.contains("envase")||infoActual.contains("tableta")||infoActual.contains("parche")||infoActual.contains("supositorio"))){//Descripcion
                                    descripcion.add(infoActual);

                                }else{
                                    String ad=candidatoAdministracion(infoActual);
                                    String ind=candidatoIndicacion(infoActual);

                                    if(infoActual.contains("generalidades")) {
                                        generalidadesL.add(infoActual);
                                        estado = 2;
                                    }else {
                                        if (ad != null && ind != null) {
                                            administracion.add(infoActual.replace(ind,""));
                                            indicaciones.add(ind.replace("\n", ""));
                                            System.out.println("++------------++");
                                            System.out.println(medicamento);
                                            System.out.println(infoActual);
                                            System.out.println("++++++++++++++++");
                                        } else {
                                            if (ad != null) {
                                                administracion.add(infoActual);
                                            } else {
                                                    indicaciones.add(infoActual.replace("\n", ""));

                                            }
                                        }
                                    }

                                }
                            }

                            break;
                        case 2:
                            if(infoActual.contains("riesgo en el embarazo")){
                                riesgoEm.add(infoActual);
                                estado=3;
                            }
                            else{
                                generalidadesL.add(infoActual);
                            }
                            break;

                        case 3:
                            if(infoActual.contains("efectos adversos")){
                                efectosL.add(infoActual);
                                estado =4;
                            }else{
                                riesgoEm.add(infoActual);
                            }

                            break;
                        case 4:

                            if(infoActual.contains("contraindicaciones y precauciones")){
                                contraL.add(infoActual);
                                estado=5;
                            }else{
                                efectosL.add(infoActual);
                            }

                            break;

                        case 5:

                            if(infoActual.contains("interacciones")){
                                interL.add(infoActual);
                                estado=6;
                            }else{
                                contraL.add(infoActual);
                            }

                            break;

                        case 6:

                            ex=null;
                            if(!child.item(0).getNodeName().equals("#text"))
                                ex= ((Element)child.item(0)).getElementsByTagName("i");


                            if(ex!=null && ex.item(0)!=null){


                                if(indicaciones.size()==0) {
                                    medsEmpty.add(medicamento);
                                }



                                claves=new ArrayList<String>();
                                descripcion=new ArrayList<String>();
                                indicaciones=new ArrayList<String>();
                                administracion=new ArrayList<String>();
                                generalidadesL=new ArrayList<String>();
                                riesgoEm=new ArrayList<String>();
                                efectosL=new ArrayList<String>();
                                contraL=new ArrayList<String>();
                                interL=new ArrayList<String>();



                                if(infoActual.equals("catálogo")){
                                    estado=0;
                                }
                                else {
                                    medicamento = ex.item(0).getTextContent().replace("\n", "").replace("Cuadro Básico", "").replace("Catálogo", "").trim();
                                    onlyMeds.add(medicamento);
                                    estado = 1;
                                }
                            }

                            else{
                                interL.add(infoActual);
                            }
                            break;
                    }

                }


            }





        }

        if(indicaciones.size()==0) {
            medsEmpty.add(medicamento);
        }

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
        System.out.println(StringUtils.join(medsEmpty,"\n"));




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
                        isContain(infoActual,"dolor")
                     /*   isContain(infoActual,"anestesia")||
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
                    infoActual.contains("candidiasis")*/




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


}
