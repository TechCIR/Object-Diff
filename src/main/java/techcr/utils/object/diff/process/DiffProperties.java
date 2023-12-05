package techcr.utils.object.diff.process;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import techcr.utils.object.diff.type.DiffConstant;
import techcr.utils.object.diff.type.config.DefaultFieldDiffPrinter;
import techcr.utils.object.diff.type.config.DefaultFieldNameDeriver;
import techcr.utils.object.diff.type.config.DefaultObjectDiffPrinter;
import techcr.utils.object.diff.type.config.FieldDiffPrinter;
import techcr.utils.object.diff.type.config.FieldNameDeriver;
import techcr.utils.object.diff.type.config.ObjectDiffPrinter;

public class DiffProperties {
    private FieldDiffPrinter diffPrinter;
    private ObjectDiffPrinter objectDiffPrinter;
    private String nullValueAlias = "EMPTY";
    private String diffSplitter = ",";
    private List<String> ignoreFields = new ArrayList<>();
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DiffConstant.DATE_FORMAT);

    private FieldNameDeriver fieldNameDeriver = new DefaultFieldNameDeriver();

    public FieldDiffPrinter getDiffPrinter() {
        diffPrinter = Optional.ofNullable(diffPrinter).orElse(new DefaultFieldDiffPrinter(nullValueAlias));
        return diffPrinter;
    }

    void setDiffPrinter(FieldDiffPrinter diffPrinter) {
        this.diffPrinter = diffPrinter;
    }

    public ObjectDiffPrinter getObjectDiffPrinter() {
        objectDiffPrinter = Optional.ofNullable(objectDiffPrinter).orElse(new DefaultObjectDiffPrinter());
        return objectDiffPrinter;
    }

    public DiffProperties setObjectDiffPrinter(ObjectDiffPrinter objectDiffPrinter) {
        this.objectDiffPrinter = objectDiffPrinter;
        return this;
    }

    DiffProperties setNullValueAlias(String nullValueAlias) {
        this.nullValueAlias = nullValueAlias;
        return this;
    }

    public String getDiffSplitter() {
        return diffSplitter;
    }

    DiffProperties setDiffSplitter(String diffSplitter) {
        this.diffSplitter = diffSplitter;
        return this;
    }

    List<String> getIgnoreFields() {
        return ignoreFields;
    }

    public DiffProperties setDateFormat(String dateFormat) {
        this.dateFormat = new SimpleDateFormat(dateFormat);
        return this;
    }

    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    public boolean isIgnore(String fieldName) {
        return ignoreFields.contains(fieldName);
    }

    public FieldNameDeriver getFieldNameDeriver() {
        return fieldNameDeriver;
    }

    public DiffProperties setFieldNameDeriver(FieldNameDeriver fieldNameDeriver) {
        this.fieldNameDeriver = fieldNameDeriver;
        return this;
    }
}
