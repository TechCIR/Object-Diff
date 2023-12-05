package techcr.utils.object.diff.type;

import org.testng.Assert;
import org.testng.annotations.Test;

import techcr.utils.object.diff.type.config.ValueEqualEvaluator;


public class ValueEqualEvaluatorTest {

    @Test
    public void testIsEqual() {

        String source = null;
        String target = null;
        ValueEqualEvaluator<String> evaluator = new ValueEqualEvaluator<String>() {};

        boolean isEqual = evaluator.isEqual(source, target);
        Assert.assertTrue(isEqual);
        target = "One";
        isEqual = evaluator.isEqual(source, target);
        Assert.assertFalse(isEqual);
        target = null;
        source = "One";
        isEqual = evaluator.isEqual(source, target);
        Assert.assertFalse(isEqual);
        target = "One";
        isEqual = evaluator.isEqual(source, target);
        Assert.assertTrue(isEqual);
        target = "Two";
        isEqual = evaluator.isEqual(source, target);
        Assert.assertFalse(isEqual);
    }
}
