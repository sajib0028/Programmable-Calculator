package Calculator;

public class Entries {
    public String name, measuredValue, errorValue, unit, type;

    public Entries(String name, String measuredValue, String errorValue, String unit, String type) {
        this.name = name;
        this.measuredValue = measuredValue;
        this.errorValue = errorValue;
        this.unit = unit;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getMeasuredValue() {
        return measuredValue;
    }

    public String getErrorValue() {
        return errorValue;
    }

    public String getUnit() {
        return unit;
    }

    public String getType() {
        return type;
    }
}
