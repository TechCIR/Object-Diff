package object.diff.generaltest;

import object.diff.type.config.DashObjectDiffPrinter;
import object.diff.type.config.DoubleValueOutputHandler;
import object.diff.type.config.JobValueOutputHandler;
import object.diff.type.config.PrefixValueEvaluator;
import object.diff.type.notation.EntityType;
import object.diff.type.notation.FieldAlias;
import object.diff.type.notation.IgnoreDiff;
import object.diff.type.notation.ObjectDiffType;
import object.diff.type.notation.ValueEvaluator;
import object.diff.type.notation.ValueOutput;

public class User {

    private String name;
    private String firstName;
    @FieldAlias(alias = "LST NAME")
    private String lastName;
    private int age;
    @ValueEvaluator(valueEvaluator = PrefixValueEvaluator.class)
    private String address;
    @IgnoreDiff
    private String pw;
    @ValueOutput(outputHandler = JobValueOutputHandler.class)
    private String job;
    private Double salary;
    @ValueOutput(outputHandler = DoubleValueOutputHandler.class)
    private String strSalary;
    @EntityType(name = "School")
    private School school;
    @EntityType(name = "User School")
    private UserSchool userSchool;
    @EntityType(name = "Parent School")
    private UserSchool parentSchool;
    @ObjectDiffType(objectDiffPrinter = DashObjectDiffPrinter.class)
    @EntityType(name = "Object Diff School")
    private UserSchool objectDiffSchool;

    public User() {
    }

    public User(String name, int age, String pw) {
        this.name = name;
        this.age = age;
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public void setStrSalary(String strSalary) {
        this.strSalary = strSalary;
    }

    public void setUserSchool(UserSchool userSchool) {
        this.userSchool = userSchool;
    }

    public void setParentSchool(UserSchool parentSchool) {
        this.parentSchool = parentSchool;
    }

    public UserSchool getObjectDiffSchool() {
        return objectDiffSchool;
    }

    public void setObjectDiffSchool(UserSchool objectDiffSchool) {
        this.objectDiffSchool = objectDiffSchool;
    }
}
