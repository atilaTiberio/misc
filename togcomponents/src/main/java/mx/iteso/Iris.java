package mx.iteso;

import org.springframework.beans.BeanUtils;

import java.util.Objects;
import java.util.Random;

public class Iris {

    double seplen;
    double sepwid;
    double petlen;
    double petwid;
    double classValue;


    public Iris(){

    }

    public Iris(double seplen, double sepwid, double petlen, double petwid, double classValue) {
        this.seplen = seplen;
        this.sepwid = sepwid;
        this.petlen = petlen;
        this.petwid = petwid;
        this.classValue = classValue;
    }

    public double getSeplen() {
        return seplen;
    }

    public void setSeplen(double seplen) {
        this.seplen = seplen;
    }

    public double getSepwid() {
        return sepwid;
    }

    public void setSepwid(double sepwid) {
        this.sepwid = sepwid;
    }

    public double getPetlen() {
        return petlen;
    }

    public void setPetlen(double petlen) {
        this.petlen = petlen;
    }

    public double getPetwid() {
        return petwid;
    }

    public void setPetwid(double petwid) {
        this.petwid = petwid;
    }

    public double getClassValue() {
        return classValue;
    }

    public void setClassValue(double classValue) {
        this.classValue = classValue;
    }

    @Override
    public String toString() {
        return "\n\tIris{" +
                "seplen=" + seplen +
                ", sepwid=" + sepwid +
                ", petlen=" + petlen +
                ", petwid=" + petwid +
                ", classValue=" + classValue +
                "}";
    }

    public void getMax(Iris newMax){


        newMax.setClassValue(Math.max(this.classValue,newMax.getClassValue()));
        newMax.setPetlen(Math.max(this.getPetlen(),newMax.getPetlen()));
        newMax.setPetwid(Math.max(this.getPetwid(),newMax.getPetwid()));
        newMax.setSeplen(Math.max(this.getSeplen(),newMax.getSeplen()));
        newMax.setSepwid(Math.max(this.getSepwid(),newMax.getSepwid()));

    }

    public void getMin(Iris newMin){

        newMin.setClassValue(Math.min(this.classValue,newMin.getClassValue()));
        newMin.setPetlen(Math.min(this.getPetlen(),newMin.getPetlen()));
        newMin.setPetwid(Math.min(this.getPetwid(),newMin.getPetwid()));
        newMin.setSeplen(Math.min(this.getSeplen(),newMin.getSeplen()));
        newMin.setSepwid(Math.min(this.getSepwid(),newMin.getSepwid()));
    }


    public void updateValues(Iris newCentroide){
        BeanUtils.copyProperties(newCentroide,this);
    }


    public static Iris getRandom(Iris max, Iris min){


        Iris irisRandom= new Iris();
        Random random = new Random();
        double nextDouble=0.0;

        nextDouble=random.nextDouble()*(max.getClassValue()-min.getClassValue())+min.getClassValue();
        irisRandom.setClassValue(nextDouble);

        nextDouble=random.nextDouble()*(max.getPetlen()-min.getPetlen())+min.getPetlen();
        irisRandom.setPetlen(nextDouble);

        nextDouble=random.nextDouble()*(max.getPetwid()-min.getPetwid())+min.getPetwid();
        irisRandom.setPetwid(nextDouble);

        nextDouble=random.nextDouble()*(max.getSeplen()-min.getSeplen())+min.getSeplen();
        irisRandom.setSeplen(nextDouble);

        nextDouble=random.nextDouble()*(max.getSepwid()-min.getSepwid())+min.getSepwid();
        irisRandom.setSepwid(nextDouble);

        return irisRandom;

    }



    public double distanciaEuclideana(Iris cluster) {

        Double d = 0d;

        d += Math.pow(this.getClassValue() -cluster.getClassValue(), 2);
        d += Math.pow(this.getPetlen() -cluster.getPetlen(), 2);
        d += Math.pow(this.getPetwid() -cluster.getPetwid(), 2);
        d += Math.pow(this.getSeplen() -cluster.getSeplen(), 2);
        d += Math.pow(this.getSepwid() -cluster.getSepwid(), 2);

        return Math.sqrt(d);
    }

    public  void deltaCentroide(Iris punto,Integer totalPuntosClusterActual){

        this.setClassValue(this.getClassValue()+(punto.getClassValue()/totalPuntosClusterActual));
        this.setSepwid(this.getSepwid()+(punto.getSepwid()/totalPuntosClusterActual));
        this.setSeplen(this.getSeplen()+(punto.getSeplen()/totalPuntosClusterActual));
        this.setPetwid(this.getPetwid()+(punto.getPetwid()/totalPuntosClusterActual));
        this.setPetlen(this.getPetlen()+(punto.getPetlen()/totalPuntosClusterActual));

        System.out.println(this);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Iris iris = (Iris) o;
        return Double.compare(iris.seplen, seplen) == 0 &&
                Double.compare(iris.sepwid, sepwid) == 0 &&
                Double.compare(iris.petlen, petlen) == 0 &&
                Double.compare(iris.petwid, petwid) == 0 &&
                Double.compare(iris.classValue, classValue) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(seplen, sepwid, petlen, petwid, classValue);
    }
}