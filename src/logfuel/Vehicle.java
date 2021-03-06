package logfuel;

import java.util.SortedMap;
import java.util.TreeMap;
import logfuel.Supply;;

public class Vehicle {

    private String brand = null;

    private String model = null;

    private SortedMap<Integer, Supply> supplies = new TreeMap<>();

    public Vehicle() {
        super();
    }

    public Vehicle(String brand, String model, SortedMap<Integer, Supply> supplies) {
        super();
        this.brand = brand;
        this.model = model;
        this.supplies = supplies;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public SortedMap<Integer, Supply> getSupplies() {
        return supplies;
    }

    public void setSupplies(SortedMap<Integer, Supply> supplies) {
        this.supplies = supplies;
    }

    public void updateSupplies(Supply supply) {
    	this.supplies.put(supply.getCurrentMileage(), supply);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((brand == null) ? 0 : brand.hashCode());
        result = prime * result + ((model == null) ? 0 : model.hashCode());
        result = prime * result + ((supplies == null) ? 0 : supplies.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vehicle other = (Vehicle) obj;
        if (brand == null) {
            if (other.brand != null)
                return false;
        } else if (!brand.equals(other.brand))
            return false;
        if (model == null) {
            if (other.model != null)
                return false;
        } else if (!model.equals(other.model))
            return false;
        if (supplies == null) {
            if (other.supplies != null)
                return false;
        } else if (!supplies.equals(other.supplies))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Vehicle [brand=" + brand + ", model=" + model + ", supplies=" + supplies + "]";
    }

}
