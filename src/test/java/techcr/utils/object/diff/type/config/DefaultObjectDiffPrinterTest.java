package techcr.utils.object.diff.type.config;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DefaultObjectDiffPrinterTest {

    @Test
    public void testPrintDiff() {

        DefaultObjectDiffPrinter printer = new DefaultObjectDiffPrinter();
        String diff = printer.printDiff(null, "diff");
        Assert.assertEquals(diff, " [ diff ] ");

        diff = printer.printDiff("", "diff");
        Assert.assertEquals(diff, " [ diff ] ");

        diff = printer.printDiff("first", "diff");
        Assert.assertEquals(diff, "first [ diff ] ");

    }
}
