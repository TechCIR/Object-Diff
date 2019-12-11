package object.diff.parentlevel;

public class ChildLevelOne extends Parent {
    private String childLevelOneName;

    public ChildLevelOne(String parentName, String childLevelOneName) {
        super(parentName);
        this.childLevelOneName = childLevelOneName;
    }

    public String getChildLevelOneName() {
        return childLevelOneName;
    }
}
