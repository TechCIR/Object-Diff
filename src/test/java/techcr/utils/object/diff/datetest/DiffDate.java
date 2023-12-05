package techcr.utils.object.diff.datetest;

import java.util.Calendar;
import java.util.Date;

import techcr.utils.object.diff.type.notation.DateType;
import techcr.utils.object.diff.type.notation.FieldAlias;

public class DiffDate {

    private Date defaultDate;
    @FieldAlias(alias = "D D T D")
    @DateType
    private Date defaultDateTypeDate;
    @FieldAlias(alias = "C D T D")
    @DateType(format = "dd-yyyy-MM")
    private Date customDateTypeDate;

    public DiffDate setDefaultDate(int year, int month, int day) {
        this.defaultDate = getDate(year, month, day);
        return this;
    }

    public DiffDate setDefaultDateTypeDate(int year, int month, int day) {
        this.defaultDateTypeDate = getDate(year, month, day);
        return this;
    }

    public DiffDate setCustomDateTypeDate(int year, int month, int day) {
        this.customDateTypeDate = getDate(year, month, day);
        return this;
    }

    private Date getDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        return calendar.getTime();
    }

}
