package mx.iteso;

import java.util.Random;

public class KMain {


    public static void main(String[] args){


        float max[]=new float[]{80.0f,60.0f,95.0f};
        float min[]=new float[]{15.0f,5.0f,18.0f};

        Random r= new Random();

        for(int i=0; i<3;i++){
            System.out.println(r.nextFloat()*(max[i]-min[i])+min[i]);


        }

    }
}
