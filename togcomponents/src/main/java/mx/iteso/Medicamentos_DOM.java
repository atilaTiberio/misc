package mx.iteso;


import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Medicamentos_DOM {


    public static void main(String[] args) throws Exception{

        getMeds();
    }


    public static List<String> getMeds() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();


        Document document = builder.parse(new File( "total.html" ));
        document.getDocumentElement().normalize();

        NodeList divPages=document.getElementsByTagName("i");
        String infoActual;

        List<String> meds=new ArrayList<String>();


        for(int i=0; i<divPages.getLength();i++) {

            infoActual=divPages.item(i).getTextContent().replace("Cuadro Básico","").replace("Catálogo","").trim();

            if(infoActual.length()>0 && isMed(infoActual)) {

                if(!infoActual.equals("KRAS")) {
                    meds.add(infoActual.replace("\n", ""));
                }
            }


        }
        System.out.println(meds.size());
        return meds;
    }



    public static boolean isMed(String info){


        String tmp=info.replace(",","").replace("-","").replace("/","");

        String[] tokens=tmp.split(" ");

        return StringUtils.isAllUpperCase(tokens[0]);

    }

    private static boolean isContain(String source, String subItem){
        String pattern = "\\b"+subItem+"\\b";
        Pattern p=Pattern.compile(pattern);
        Matcher m=p.matcher(source);
        return m.find();
    }


}
