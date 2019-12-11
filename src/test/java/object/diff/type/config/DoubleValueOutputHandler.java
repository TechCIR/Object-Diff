package object.diff.type.config;

public class DoubleValueOutputHandler implements ValueOutputHandler<Double> {
    private String prefix;

    public DoubleValueOutputHandler(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String print(Double aDouble) {
        return null == aDouble ? "NULL" : "NOT NULL";
    }
}
