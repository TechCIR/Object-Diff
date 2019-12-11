package object.diff.process;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import object.diff.type.DiffConstant;
import object.diff.type.config.DefaultFieldDiffPrinter;
import object.diff.type.config.DefaultObjectDiffPrinter;
import object.diff.type.config.FieldDiffPrinter;
import object.diff.type.config.ObjectDiffPrinter;

public class DiffProperties {
    private FieldDiffPrinter diffPrinter;
    private ObjectDiffPrinter objectDiffPrinter;
    private String nullValueAlias = "EMPTY";
    private String diffSplitter = ",";
    private List<String> ignoreFields = new ArrayList<>();
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DiffConstant.DATE_FORMAT);

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

    public void setObjectDiffPrinter(ObjectDiffPrinter objectDiffPrinter) {
        this.objectDiffPrinter = objectDiffPrinter;
    }

    void setNullValueAlias(String nullValueAlias) {
        this.nullValueAlias = nullValueAlias;
    }

    public String getDiffSplitter() {
        return diffSplitter;
    }

    void setDiffSplitter(String diffSplitter) {
        this.diffSplitter = diffSplitter;
    }

    List<String> getIgnoreFields() {
        return ignoreFields;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = new SimpleDateFormat(dateFormat);
    }

    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    public boolean isIgnore(String fieldName) {
        return ignoreFields.contains(fieldName);
    }


}
