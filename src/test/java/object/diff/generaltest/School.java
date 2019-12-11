package object.diff.generaltest;

import object.diff.type.notation.IgnoreDiff;

@IgnoreDiff
public class School {
    private String name;

    public School(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        School school = (School) o;

        return name != null ? name.equals(school.name) : school.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
