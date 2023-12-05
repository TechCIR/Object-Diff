package techcr.utils.object.diff.enumtest;

import techcr.utils.object.diff.type.config.WeekendDayOutput;
import techcr.utils.object.diff.type.notation.ValueOutput;

public class Day {
    private WeekDay weekDay;
    @ValueOutput(outputHandler = WeekendDayOutput.class)
    private WeekendDay weekendDay;
    private FreeDay freeDay;

    public Day() {
    }

    public Day(WeekDay weekDay, WeekendDay weekendDay) {
        this.weekDay = weekDay;
        this.weekendDay = weekendDay;
    }

    public WeekDay getWeekDay() {
        return weekDay;
    }

    public WeekendDay getWeekendDay() {
        return weekendDay;
    }

    public void setFreeDay(FreeDay freeDay) {
        this.freeDay = freeDay;
    }

    public FreeDay getFreeDay() {
        return freeDay;
    }
}
