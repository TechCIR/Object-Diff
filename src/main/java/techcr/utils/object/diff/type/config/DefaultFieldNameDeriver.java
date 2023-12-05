package techcr.utils.object.diff.type.config;

import org.apache.commons.lang3.StringUtils;

import techcr.utils.object.diff.type.FieldDiff;

public class DefaultFieldNameDeriver extends FieldNameDeriver {

    public DefaultFieldNameDeriver() {
    }

    @Override
    String getCustomName(FieldDiff fieldDiff) {
        return StringUtils.capitalize(StringUtils.join(
            StringUtils.splitByCharacterTypeCamelCase(fieldDiff.getFieldName()), " "));
    }

}
