package techcr.utils.object.diff.type.config;

public class DefaultObjectDiffPrinter implements ObjectDiffPrinter {
    @Override
    public String printDiff(String entityName, String entityDiff) {
        return (null != entityName && entityName.length() > 0 ? entityName : "") + " [ " + entityDiff + " ] ";
    }
}
