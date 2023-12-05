package techcr.utils.object.diff.type.config;

import techcr.utils.object.diff.type.FieldDiff;

@FunctionalInterface
public interface FieldDiffPrinter {

    String printDiff(FieldDiff fieldDiff);
}
