package techcr.utils.object.diff.parentlevel;

import techcr.utils.object.diff.type.notation.ParentDiff;

@ParentDiff(ignore = true)
public class IgnoreChildLevelTwo extends ChildLevelOne {

    private String childLevelTwoName;

    public IgnoreChildLevelTwo(String parentName, String childLevelOneName, String childLevelTwoName) {
        super(parentName, childLevelOneName);
        this.childLevelTwoName = childLevelTwoName;
    }

    public String getChildLevelTwoName() {
        return childLevelTwoName;
    }
}
