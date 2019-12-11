package object.diff.type.config;

@FunctionalInterface
public interface ObjectDiffPrinter {
    String printDiff(String entityName, String entityDiff);
}
