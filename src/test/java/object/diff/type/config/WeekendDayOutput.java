package object.diff.type.config;


import object.diff.enumtest.WeekendDay;

public class WeekendDayOutput implements ValueOutputHandler<WeekendDay> {
    @Override
    public String print(WeekendDay weekendDay) {
        return weekendDay.getDate();
    }
}
