package mx.iteso;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class Rest {


    public static void main(String[] args) throws IOException {


     /*   String url = "http://www3.inegi.org.mx//sistemas/api/indicadores/v1/Indicador/6200240321/00000/es/false/json/0345de0a-9b0f-c7ee-b657-5e123cb2da5f";

        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper= new ObjectMapper();
        TypeFactory typeFactory= mapper.getTypeFactory();
        String response=restTemplate.getForObject(url,String.class);
        JsonNode node=mapper.readTree(response);
        JsonNode serie=node.at("/Data/Serie");
        List<InegiReporte> reporte= mapper.readValue(serie.toString(),typeFactory.constructCollectionType(List.class,InegiReporte.class));

        String a;

        */


        //Map<String,HashMap<String,Object>> res= restTemplate.getForObject(url,Map.class);
/*

ResponseEntity<String> obj=restTemplate.getForEntity(url,String.class);

        InegiReporte[] res=mapper.readValue(obj.getBody(),InegiReporte[].class);
            ResponseEntity<List<InegiReporte>> inegiResponse=restTemplate.exchange(url,
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<InegiReporte>>() {
                    });

            List<InegiReporte> inegiReporteList=inegiResponse.getBody();
            */

        /*ResponseEntity<String> responseEntity = restTemplate.getForEntity(Url,String.class);
        Gson gson = new GsonBuilder().create();
        Response reponse = gson.fromJson(responseEntity , Response.class);
        */
        System.out.println(maskEmail("horacio.iturbe@demandforce.com"));


    }

    public static String maskEmail(String email) {

        StringBuilder finalString = new StringBuilder();
        StringBuilder sb;
        StringTokenizer stAt = new StringTokenizer(email, "@");
        sb = new StringBuilder(stAt.nextToken());


        String a=sb.substring(2,sb.length()-1);
        a=a.replaceAll(".",".");
        char[] ss= a.toCharArray();


        System.out.println(sb.replace(2,sb.length()-1,a).toString());

       /* for (int i = 2; i < sb.length() - 1; ++i) {
            sb.setCharAt(i, '.');
        }


        //System.out.println(sb.replace(2,sb.length()-1,"."));
        finalString.append(sb.toString());
        finalString.append("@");
        sb = new StringBuilder(stAt.nextToken());

        for (int i = 2; i < sb.length() && sb.charAt(i) != '.'; i++) {
            sb.setCharAt(i, '.');
        }
        finalString.append(sb.toString());*/
        String maskedEmail = finalString.toString();
        return maskedEmail;
    }




}
