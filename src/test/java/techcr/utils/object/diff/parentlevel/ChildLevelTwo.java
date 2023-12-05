package techcr.utils.object.diff.parentlevel;

import techcr.utils.object.diff.type.notation.ParentDiff;

@ParentDiff(ignore = false, parentLevel = 1)
public class ChildLevelTwo extends ChildLevelOne {

    private String childLevelTwoName;

    public ChildLevelTwo(String parentName, String childLevelOneName, String childLevelTwoName) {
        super(parentName, childLevelOneName);
        this.childLevelTwoName = childLevelTwoName;
    }

    public String getChildLevelTwoName() {
        return childLevelTwoName;
    }
}
