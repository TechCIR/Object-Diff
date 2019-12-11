package object.diff.type.config;

public class DashObjectDiffPrinter implements ObjectDiffPrinter {
    @Override
    public String printDiff(String entityName, String entityDiff) {
        return (entityName == null || entityName.length() == 0 ? "EMPTY" : entityName) + " - " + entityDiff;
    }
}
