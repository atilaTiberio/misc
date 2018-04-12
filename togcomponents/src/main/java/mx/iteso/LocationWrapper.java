package mx.iteso;

import org.apache.commons.math3.ml.clustering.Clusterable;

import java.util.Arrays;

public class LocationWrapper implements Clusterable {

    private double[] points;
    //private Location location;

    public LocationWrapper(Location location){
        //this.location=location;
        points= new double[]{location.getX(),location.getY()};
    }

    /*public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    */

    @Override
    public double[] getPoint() {
        return points;
    }

    @Override
    public String toString() {
        return "LocationWrapper{" +
                "points=" + Arrays.toString(points) +
                '}';
    }
}
