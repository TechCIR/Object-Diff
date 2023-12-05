package techcr.utils.object.diff.type;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

import techcr.utils.object.diff.process.DiffProperties;
import techcr.utils.object.diff.type.config.DateValueOutputHandler;
import techcr.utils.object.diff.type.config.ValueOutputHandler;
import techcr.utils.object.diff.type.exception.ObjectDiffException;
import techcr.utils.object.diff.type.notation.DateType;
import techcr.utils.object.diff.type.notation.FieldAlias;
import techcr.utils.object.diff.type.notation.ValueOutput;

public class FieldDiff<T> {

    private Field field;
    private T oldValue;
    private T newValue;
    private FieldAlias alias;
    private ValueOutputHandler outputHandler;
    private DiffProperties properties;

    public FieldDiff(Field field, DiffProperties properties, T oldValue, T newValue) throws ObjectDiffException {
        this.field = field;
        this.properties = properties;
        try {
            field.setAccessible(true);
            this.alias = field.getAnnotation(FieldAlias.class);

            if (oldValue instanceof ValueOutputHandler) {
                this.outputHandler = (ValueOutputHandler) oldValue;
            } else if (field.isAnnotationPresent(ValueOutput.class)) {
                ValueOutput output = field.getAnnotation(ValueOutput.class);
                this.outputHandler = output.outputHandler().newInstance();
            } else if (field.isAnnotationPresent(DateType.class)){
                DateType dateType = field.getAnnotation(DateType.class);
                this.outputHandler = new DateValueOutputHandler(new SimpleDateFormat(dateType.format()));
            } else if (oldValue instanceof Date){
                this.outputHandler = new DateValueOutputHandler(this.properties.getDateFormat());
            } else {
                this.outputHandler = new ValueOutputHandler() {};
            }
            this.oldValue = oldValue;
            this.newValue = newValue;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ObjectDiffException(field.getName() + " Access Failed", e);
        }
    }

    public String getFieldName() {
        return field.getName();
    }

    public String getFieldDisplayName() {
        return properties.getFieldNameDeriver().getFieldDisplayName(this);

    }
    public Field getField() {
        return field;
    }

    public Object getOldValue() {
        return oldValue;
    }

    public String getPrintedOldValue() {
        return outputHandler.print(oldValue);
    }

    public Object getNewValue() {
        return newValue;
    }

    public String getPrintedNewValue() {
        return outputHandler.print(newValue);
    }

}
