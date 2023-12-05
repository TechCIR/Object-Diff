package techcr.utils.object.diff.type.config;


import techcr.utils.object.diff.enumtest.WeekendDay;

public class WeekendDayOutput implements ValueOutputHandler<WeekendDay> {
    @Override
    public String print(WeekendDay weekendDay) {
        return weekendDay.getDate();
    }
}
