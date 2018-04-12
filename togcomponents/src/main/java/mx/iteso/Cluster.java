package mx.iteso;

import java.util.ArrayList;
import java.util.List;

public class Cluster {


    Iris centro;
    List<Iris> puntos;
    boolean finalizo;



    public Cluster(Iris centro) {
        this.centro = centro;
        this.puntos = new ArrayList<>();
    }

    public void addPunto(Iris punto){
        puntos.add(punto);
    }
    public Iris getCentro() {
        return centro;
    }

    public void setCentro(Iris centro) {
        this.centro = centro;
    }

    public List<Iris> getPuntos() {
        return puntos;
    }

    public void setPuntos(List<Iris> puntos) {
        this.puntos = puntos;
    }

    public boolean isFinalizo() {
        return finalizo;
    }

    public void setFinalizo(boolean finalizo) {
        this.finalizo = finalizo;
    }

    @Override
    public String toString() {
        return "Cluster{" +
                "centro=" + centro +
                ",\npuntos=" + puntos.size() +
                "}\n";
    }
}
