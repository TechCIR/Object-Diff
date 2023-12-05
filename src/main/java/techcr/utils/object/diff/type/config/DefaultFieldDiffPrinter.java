package techcr.utils.object.diff.type.config;

import java.util.Objects;

import techcr.utils.object.diff.type.FieldDiff;

public class DefaultFieldDiffPrinter implements FieldDiffPrinter {

    private String nullValueAlias;

    public DefaultFieldDiffPrinter(String nullValueAlias) {
        Objects.requireNonNull(nullValueAlias, "Null Value alias can't be null");
        this.nullValueAlias = nullValueAlias;
    }

    @Override
    public String printDiff(FieldDiff fieldDiff) {
        String field = fieldDiff.getFieldDisplayName();
        String source = fieldDiff.getOldValue() == null ? nullValueAlias : fieldDiff.getPrintedOldValue();
        String target = fieldDiff.getNewValue() == null ? nullValueAlias : fieldDiff.getPrintedNewValue();

        return " { " + field + " : " + source + " -> " + target + " } ";
    }
}
