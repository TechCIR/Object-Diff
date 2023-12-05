package techcr.utils.object.diff.process;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import techcr.utils.object.diff.type.ObjectDiff;
import techcr.utils.object.diff.type.config.FieldDiffPrinter;
import techcr.utils.object.diff.type.config.ValueEqualEvaluator;
import techcr.utils.object.diff.type.exception.ObjectDiffException;
import techcr.utils.object.diff.type.notation.EntityType;
import techcr.utils.object.diff.type.notation.IgnoreDiff;
import techcr.utils.object.diff.type.notation.ObjectDiffType;
import techcr.utils.object.diff.type.notation.ParentDiff;
import techcr.utils.object.diff.type.notation.ValueEvaluator;

public class DiffBuilder {

    private DiffProperties properties = new DiffProperties();

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

    /**
     * Generating diff
     *
     * @param source - source object
     * @param target - target object
     * @return
     * @throws ObjectDiffException
     */
    public ObjectDiff diff(Object source, Object target) throws ObjectDiffException {
        Objects.requireNonNull(source, "Source must not be null");
        Objects.requireNonNull(target, "Target must not be null");
        return diff(source.getClass(), "", source, target);
    }

    private <T> ObjectDiff diff(Class<T> clazz, String rootName, Object source, Object target)
        throws ObjectDiffException {

        ObjectDiff objectDiff = new ObjectDiff(rootName, properties);

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
                    createFieldDiff(objectDiff, field, sourceValue, targetValue);
                }
            }
        }
        return objectDiff;
    }

    private void createFieldDiff(ObjectDiff objectDiff, Field field, Object sourceValue, Object targetValue)
        throws ObjectDiffException {
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
                    ObjectDiff innerObjectDiff =innerDiffBuilder
                        .diff(field.getType(), entityName, sourceValue, targetValue);
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

    /**
     * Adding custom {@link  FieldDiffPrinter}
     *
     * @param printer
     * @return
     */
    public DiffBuilder addDiffPrinter(FieldDiffPrinter printer) {
        Objects.requireNonNull(printer, "Printer must not be null");
        properties.setDiffPrinter(printer);
        return this;
    }

    /**
     * Customize how null values should show in diff
     *
     * @param nullValueAlias
     * @return
     */
    public DiffBuilder addNullAlias(String nullValueAlias) {
        Objects.requireNonNull(nullValueAlias, "Alias must not be null");
        properties.setNullValueAlias(nullValueAlias);
        return this;
    }

    /**
     * Customize how diff should be seperated
     *
     * @param diffSplitter
     * @return
     */
    public DiffBuilder addDiffSplitter(String diffSplitter) {
        Objects.requireNonNull(diffSplitter, "Alias must not be null");
        properties.setDiffSplitter(diffSplitter);
        return this;
    }

    /**
     * Configure customize {@link DiffProperties}
     *
     * @param properties
     * @return
     */
    private DiffBuilder setProperties(DiffProperties properties) {
        this.properties = properties;
        return this;
    }

    /**
     * Customize default date format
     *
     * @param dateFormat
     * @return
     */
    public DiffBuilder setDateFormat(String dateFormat) {
        this.properties.setDateFormat(dateFormat);
        return this;
    }

    /**
     * Adding more diff ignore fields
     *
     * @param ignoreFields
     * @return
     */
    public DiffBuilder addIgnoreField(List<String> ignoreFields) {
        Optional.ofNullable(ignoreFields).ifPresent(fields -> properties.getIgnoreFields().addAll(fields));
        return this;
    }

    /**
     * Adding more diff ignore fields
     *
     * @param ignoreFields
     * @return
     */
    public DiffBuilder addIgnoreField(String... ignoreFields) {
        Optional.ofNullable(ignoreFields)
            .ifPresent(fields -> properties.getIgnoreFields().addAll(Arrays.asList(fields)));
        return this;
    }

}
