package object.diff.enumtest;

import object.diff.type.config.ValueOutputHandler;

public enum FreeDay implements ValueOutputHandler<FreeDay> {
    SUNDAY("Sunday Funday"),
    MONDAY("Monday Workday");

    String description;

    FreeDay(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String print(FreeDay freeDay) {
        return null != freeDay ? freeDay.description : "";
    }
}
