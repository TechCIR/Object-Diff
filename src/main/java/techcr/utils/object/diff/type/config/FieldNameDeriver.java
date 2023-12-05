package techcr.utils.object.diff.type.config;

import techcr.utils.object.diff.type.FieldDiff;
import techcr.utils.object.diff.type.notation.FieldAlias;

public abstract class FieldNameDeriver {

    public String getFieldDisplayName(FieldDiff fieldDiff) {
        FieldAlias alias = fieldDiff.getField().getAnnotation(FieldAlias.class);
        return alias != null ? alias.alias() : getCustomName(fieldDiff);
    }

    abstract String getCustomName(FieldDiff fieldDiff);
}
