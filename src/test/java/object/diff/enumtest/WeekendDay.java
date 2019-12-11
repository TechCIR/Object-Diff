package object.diff.enumtest;

public enum WeekendDay {
    SATURDAY("Saturday"),
    SUNDAY("Sunday");

    String date;

    WeekendDay(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
