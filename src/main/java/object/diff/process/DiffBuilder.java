package object.diff.process;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import object.diff.type.ObjectDiff;
import object.diff.type.config.FieldDiffPrinter;
import object.diff.type.config.ValueEqualEvaluator;
import object.diff.type.exception.ObjectDiffException;
import object.diff.type.notation.EntityType;
import object.diff.type.notation.IgnoreDiff;
import object.diff.type.notation.ObjectDiffType;
import object.diff.type.notation.ParentDiff;
import object.diff.type.notation.ValueEvaluator;

public class DiffBuilder {

    private DiffProperties properties = new DiffProperties();
    private ObjectDiff objectDiff;

    public DiffBuilder() {

    }

    /**
     * if additional property added to diff builder makes sure to add to here.
     *
     * @return
     */
    private DiffBuilder cloneDiffBuilder() {
        return new DiffBuilder().setProperties(properties);
    }

    public DiffBuilder diff(Object source, Object target) throws ObjectDiffException {
        Objects.requireNonNull(source, "Source must not be null");
        Objects.requireNonNull(target, "Target must not be null");
        diff(source.getClass(), "", source, target);
        return this;
    }


    private  <T> void diff(Class<T> clazz, String rootName, Object source, Object target) throws ObjectDiffException {

        objectDiff = new ObjectDiff(rootName, properties);

        if (!clazz.isAnnotationPresent(IgnoreDiff.class)) {
            int parentLevel = 0;
            ParentDiff parentDiff = clazz.getAnnotation(ParentDiff.class);
            if (null != parentDiff && !parentDiff.ignore()) {
                parentLevel = parentDiff.parentLevel();
            }
            List<Field> fields = getFields(clazz, parentLevel);

            for (Field field : fields) {
                if (!field.isAnnotationPresent(IgnoreDiff.class) && !properties.isIgnore(field.getName())) {
                    field.setAccessible(true);
                    Object sourceValue = null;
                    try {
                        sourceValue = field.get(source);
                    } catch (Exception e) {
                    }
                    Object targetValue = null;
                    try {
                        targetValue = field.get(target);
                    } catch (Exception e) {
                    }
                    createFieldDiff(field, sourceValue, targetValue);
                }
            }
        }
    }

    private void createFieldDiff(Field field, Object sourceValue, Object targetValue) throws ObjectDiffException {
        try {

            ValueEvaluator valueEvaluator = field.getAnnotation(ValueEvaluator.class);
            ValueEqualEvaluator evaluator = valueEvaluator == null ?
                    new ValueEqualEvaluator() {
                    } : valueEvaluator.valueEvaluator().newInstance();
            if (!evaluator.isEqual(sourceValue, targetValue)) {
                if (field.isAnnotationPresent(EntityType.class)) {
                    EntityType entityType = field.getAnnotation(EntityType.class);
                    String entityName = entityType.name();
                    DiffBuilder innerDiffBuilder = cloneDiffBuilder();
                    innerDiffBuilder.diff(field.getType(), entityName, sourceValue, targetValue);
                    ObjectDiff innerObjectDiff = innerDiffBuilder.getObjectDiff();
                    if (innerObjectDiff.isValid()) {
                        if (field.isAnnotationPresent(ObjectDiffType.class)) {
                            innerObjectDiff.setObjectDiffPrinter(
                                    field.getAnnotation(ObjectDiffType.class).objectDiffPrinter().newInstance());
                        }
                        objectDiff.addObjectDiff(innerObjectDiff);
                    }
                } else {
                    objectDiff.addFieldDiff(field, sourceValue, targetValue);
                }
            }
        } catch (IllegalAccessException | InstantiationException e) {
            throw new ObjectDiffException(field.getName() + " Access Failed", e);
        }
    }

    private <T> List<Field> getFields(Class<T> clazz, int parentLevel) {
        List<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));

        if (parentLevel > 0) {
            Class superClass = clazz.getSuperclass();
            fields.addAll(getFields(superClass, parentLevel - 1));
        }

        return fields;
    }

    public DiffBuilder addDiffPrinter(FieldDiffPrinter printer) {
        Objects.requireNonNull(printer, "Printer must not be null");
        properties.setDiffPrinter(printer);
        return this;
    }

    public DiffBuilder addNullAlias(String nullValueAlias) {
        Objects.requireNonNull(nullValueAlias, "Alias must not be null");
        properties.setNullValueAlias(nullValueAlias);
        return this;
    }

    public DiffBuilder addDiffSplitter(String diffSplitter) {
        Objects.requireNonNull(diffSplitter, "Alias must not be null");
        properties.setDiffSplitter(diffSplitter);
        return this;
    }

    private DiffBuilder setProperties(DiffProperties properties) {
        this.properties = properties;
        return this;
    }

    public DiffBuilder setDateFormat(String dateFormat) {
        this.properties.setDateFormat(dateFormat);
        return this;
    }

    public DiffBuilder addIgnoreField(List<String> ignoreFields) {
        Optional.ofNullable(ignoreFields).ifPresent(fields -> properties.getIgnoreFields().addAll(fields));
        return this;
    }

    public DiffBuilder addIgnoreField(String... ignoreFields) {
        Optional.ofNullable(ignoreFields).ifPresent(fields -> properties.getIgnoreFields().addAll(Arrays.asList(fields)));
        return this;
    }

    public ObjectDiff getObjectDiff() {
        return objectDiff;
    }

    public String getDiff() {
        return null == objectDiff ? "" : objectDiff.getDiff(properties.getDiffSplitter());
    }
}
