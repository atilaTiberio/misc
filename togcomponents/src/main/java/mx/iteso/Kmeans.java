package mx.iteso;


import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Kmeans {


    public static void main(String[] args) throws IOException {

        Iris newMax= new Iris(Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE);
        Iris newMax_V2= new Iris(Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE);
        Iris newMin= new Iris(Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE);

        List<Iris> pts= iris(newMax,newMin);


        /*
        Obtener maximos y minimos, con esto obtendre los k clusters
         */

        int totalAttributes=5;
        /*
            Ya teniendo los maximos y minimos, se generan los k clusters aleatorios
         */
        int kClusters=4;
        List<Cluster> clusters= new ArrayList<>();
        Cluster c;

        /*
        Asigna clusters random
         */
        double distancia=0.0;

        for(int i=0;i<kClusters;i++){

            c= new Cluster(Iris.getRandom(newMax,newMin));
            clusters.add(c);

        }

    /*
    Asignar puntos
     */
        for(Iris iris: pts){

            double distanciaMinima=Double.MAX_VALUE;
            Cluster seleccion=null;

            for(Cluster cluster: clusters){
                    distancia=iris.distanciaEuclideana(cluster.getCentro());

                    if(distancia<distanciaMinima){
                        distanciaMinima=distancia;
                        seleccion=cluster;
                    }
            }

            seleccion.addPunto(iris);


        }

        /*
        Reajustar centroides
         */

        for(Cluster cluster: clusters){

            if(cluster.getPuntos().isEmpty()){
                continue;
            }

            Iris nuevoCentro= new Iris();


            for(Iris ite: cluster.getPuntos()){
                nuevoCentro.deltaCentroide(ite,cluster.getPuntos().size());
            }


            if(cluster.getCentro().equals(nuevoCentro)){
                cluster.setFinalizo(true);

            }
            else{
                cluster.setCentro(nuevoCentro);
            }


        }









    }

    public static List<Iris> iris(Iris max, Iris min) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("iris.dat"));

        List<Iris> iris = new ArrayList<>();

        Iris t;


        String temp;
        while ((temp = br.readLine()) != null) {

            String vals[] = temp.split(" ");

            t = new Iris(
                    Double.parseDouble(vals[0].trim()),
                    Double.parseDouble(vals[1].trim()),
                    Double.parseDouble(vals[2].trim()),
                    Double.parseDouble(vals[3].trim()),
                    Double.parseDouble(vals[4].trim())
            );
            t.getMax(max);
            t.getMin(min);
            iris.add(t);
        }

        return iris;

    }



    public static List<Location> puntos() throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("data.csv"));
        List<LocationWrapper> locations = new ArrayList<>();
        List<Location> puntos = new ArrayList<>();

        Location t;
        LocationWrapper lw;

        String temp;
        while ((temp = br.readLine()) != null) {

            String vals[] = temp.split(",");
            t = new Location(Double.parseDouble(vals[0].trim()), Double.parseDouble(vals[1].trim()));
            puntos.add(t);
            //locations.add(new LocationWrapper(t));

        }

        return puntos;
    }


}
