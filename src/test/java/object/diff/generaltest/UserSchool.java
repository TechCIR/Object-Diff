package object.diff.generaltest;

public class UserSchool {
    private String name;

    public UserSchool(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSchool school = (UserSchool) o;

        return name != null ? name.equals(school.name) : school.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
