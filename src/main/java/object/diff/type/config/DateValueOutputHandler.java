package object.diff.type.config;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateValueOutputHandler implements ValueOutputHandler<Date> {

    private SimpleDateFormat dateFormat;

    public DateValueOutputHandler(SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public String print(Date date) {
        return getDateFormat().format(date);
    }

    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }
}