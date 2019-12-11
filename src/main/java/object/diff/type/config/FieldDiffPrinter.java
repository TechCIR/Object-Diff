package object.diff.type.config;

import object.diff.type.FieldDiff;

@FunctionalInterface
public interface FieldDiffPrinter {
    String printDiff(FieldDiff fieldDiff);
}
