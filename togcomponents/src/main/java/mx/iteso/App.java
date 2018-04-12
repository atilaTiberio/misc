package mx.iteso;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {


        String fields = "<column comment=\"\" default=\"\" key=\"false\" label=\"$COLNAME\" " +
                "length=\"-1\" nullable=\"true\" originalDbColumnName=\"$COLNAME\" " +
                "originalLength=\"-1\" pattern=\"\" precision=\"-1\" " +
                "talendType=\"id_String\" type=\"\"/>";

     /*String split="<elementValue elementRef=\"CLUES\" value=\"row1.CLUES\"/>\n" +
 "<elementValue elementRef=\"CLAVE_ENTIDAD\" value=\"row1.CLAVE_ENTIDAD\"/>\n" +
 "<elementValue elementRef=\"ENTIDAD\" value=\"row1.ENTIDAD\"/>\n" +
 "<elementValue elementRef=\"CLAVE_MUNICIPIO\" value=\"row1.CLAVE_MUNICIPIO\"/>\n" +
 "<elementValue elementRef=\"MUNICIPIO\" value=\"row1.MUNICIPIO\"/>\n" +
 "<elementValue elementRef=\"CLAVE_LOCALIDAD\" value=\"row1.CLAVE_LOCALIDAD\"/>\n" +
 "<elementValue elementRef=\"LOCALIDAD\" value=\"row1.LOCALIDAD\"/>\n" +
 "<elementValue elementRef=\"TIPO_DE_ESTABLECIMIENTO\" value=\"row1.TIPO_DE_ESTABLECIMIENTO\"/>\n" +
 "<elementValue elementRef=\"TIPOLOGIA\" value=\"row1.TIPOLOGIA\"/>\n" +
 "<elementValue elementRef=\"NOMBRE_DEL_ESTABLECIMIENTO\" value=\"row1.NOMBRE_DEL_ESTABLECIMIENTO\"/>\n" +
 "<elementValue elementRef=\"UNIDADES\" value=\"row1.UNIDADES\"/>\n" +
 "<elementValue elementRef=\"campo\" value=\"&quot;$FIELD&quot;\"/>\n" +
 "<elementValue elementRef=\"valor\" value=\"row1.$FIELD\"/>\n" +
 "<elementValue elementRef=\"campoParent\" value=\"&quot;$PARENT&quot;\"/>\n";
 */

  BufferedReader br= new BufferedReader(new FileReader("catclues"));
  BufferedWriter wr= new BufferedWriter(new FileWriter("clues"));
  String line=null;

  while((line=br.readLine())!=null){


wr.write(fields.replaceAll("\\$COLNAME",line.toLowerCase()));
wr.newLine();


  }

  br.close();
  wr.close();


  /*BufferedReader br= new BufferedReader(new FileReader("splitcols"));
  BufferedWriter wr= new BufferedWriter(new FileWriter("split_wr"));
  String line[]=null;
  String cadena=null;
  String temp="";

  while((cadena=br.readLine())!=null){
line=cadena.split(",");

System.out.println(Arrays.toString(line)+" "+line.length);
temp=split;
temp=temp.replaceAll("\\$FIELD",line[0]);

if(line.length==1)
    temp=temp.replaceAll("\\$PARENT",line[0]);
else
    temp=temp.replaceAll("\\$PARENT",line[1]);

wr.write(temp);


  }

  br.close();
  wr.close();

    }
    */
    }
}
