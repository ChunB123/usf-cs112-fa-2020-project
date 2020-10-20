package MLLibrary;

public class DataPoint {
    private Double f1;
    private Double f2;
    private String label;
    private String type;

    public DataPoint() {
    }

    public DataPoint(Double f1, Double f2, String label, String type) {
        this.f1 = f1;
        this.f2 = f2;
        this.label = label;
        this.type = type;
    }

    public Double getF1() {
        return f1;
    }

    public void setF1(Double f1) {
        this.f1 = f1;
    }

    public Double getF2() {
        return f2;
    }

    public void setF2(Double f2) {
        this.f2 = f2;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "DataPoint{" +
                "f1=" + f1 +
                ", f2=" + f2 +
                ", label='" + label + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
