package object.diff.type;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;

import object.diff.process.DiffProperties;
import object.diff.type.config.ObjectDiffPrinter;
import object.diff.type.exception.ObjectDiffException;

public class ObjectDiff {


    private List<FieldDiff> diffs = new ArrayList<>();
    private String entityName;
    private List<ObjectDiff> objectDiffs = new ArrayList<>();
    private DiffProperties properties;
    private ObjectDiffPrinter objectDiffPrinter;

    public ObjectDiff(String entityName, DiffProperties properties) {
        Objects.requireNonNull(entityName, "Entity Name must not be null");
        this.entityName = entityName;
        this.properties = properties;
    }

    public void addFieldDiff(FieldDiff diff) {
        diffs.add(diff);
    }

    public void addFieldDiff(Field field, Object oldValue, Object newValue) throws ObjectDiffException {
        addFieldDiff(new FieldDiff(field, properties, oldValue, newValue));
    }

    public void addObjectDiff(ObjectDiff objectDiff) throws ObjectDiffException {
        Optional.ofNullable(objectDiff).ifPresent(objectDiffs::add);
    }

    public String getDiff() {
        return getDiff(properties.getDiffSplitter());
    }

    public String getDiff(String diffSplitter) {
        StringJoiner joiner = new StringJoiner(diffSplitter);
        diffs.forEach(diff -> joiner.add(properties.getDiffPrinter().printDiff(diff)));
        objectDiffs.forEach(objectDiff ->
            joiner.add(objectDiff.getObjectDiffPrinter().printDiff(objectDiff.entityName, objectDiff.getDiff(diffSplitter))));
        return joiner.toString();
    }

    private ObjectDiffPrinter getObjectDiffPrinter() {
        return Optional.ofNullable(objectDiffPrinter).orElse(properties.getObjectDiffPrinter());
    }

    public void setObjectDiffPrinter(ObjectDiffPrinter objectDiffPrinter) {
        this.objectDiffPrinter = objectDiffPrinter;
    }

    public List<FieldDiff> getDiffs() {
        return diffs;
    }

    public List<ObjectDiff> getObjectDiffs() {
        return objectDiffs;
    }

    public boolean isValid() {
        return diffs.size() > 0 || objectDiffs.size() > 0;
    }

}
