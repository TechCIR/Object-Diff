package techcr.utils.object.diff.type;

import java.lang.reflect.Field;

import org.testng.Assert;
import org.testng.annotations.Test;

import techcr.utils.object.diff.enumtest.Day;
import techcr.utils.object.diff.enumtest.FreeDay;
import techcr.utils.object.diff.generaltest.User;
import techcr.utils.object.diff.process.DiffProperties;
import techcr.utils.object.diff.type.exception.ObjectDiffException;

public class FieldDiffTest {

    private DiffProperties diffProperties = new DiffProperties();
    /**
     * Method {@link FieldDiff#getFieldName()}
     * Method {@link FieldDiff#getFieldDisplayName()}
     * Method {@link FieldDiff#getOldValue()}
     * Method {@link FieldDiff#getPrintedOldValue()}
     * Method {@link FieldDiff#getNewValue()}
     * Method {@link FieldDiff#getPrintedNewValue()}
     * @throws NoSuchFieldException
     * @throws ObjectDiffException
     */
    @Test
    public void testGetFieldName() throws NoSuchFieldException, ObjectDiffException {
        User user = new User();
        Field field = user.getClass().getDeclaredField("firstName");
        FieldDiff<String> diff = new FieldDiff<>(field, diffProperties, "Google", "Yahoo");
        Assert.assertEquals(diff.getField(), field);
        Assert.assertEquals(diff.getFieldName(), "firstName");
        Assert.assertEquals(diff.getFieldDisplayName(), "First Name");
        Assert.assertEquals(diff.getOldValue(), "Google");
        Assert.assertEquals(diff.getPrintedOldValue(), "Google");
        Assert.assertEquals(diff.getNewValue(), "Yahoo");
        Assert.assertEquals(diff.getPrintedNewValue(), "Yahoo");
    }

    /**
     * Method {@link FieldDiff#getFieldName()}
     * Method {@link FieldDiff#getFieldDisplayName()}
     * Method {@link FieldDiff#getOldValue()}
     * Method {@link FieldDiff#getPrintedOldValue()}
     * Method {@link FieldDiff#getNewValue()}
     * Method {@link FieldDiff#getPrintedNewValue()}
     * @throws NoSuchFieldException
     * @throws ObjectDiffException
     */
    @Test
    public void testFieldNameWithAlias() throws NoSuchFieldException, ObjectDiffException {
        User user = new User();
        Field field = user.getClass().getDeclaredField("lastName");
        FieldDiff<String> diff = new FieldDiff<>(field, diffProperties, "Google", "Yahoo");
        Assert.assertEquals(diff.getFieldName(), "lastName");
        Assert.assertEquals(diff.getFieldDisplayName(), "LST NAME");
        Assert.assertEquals(diff.getOldValue(), "Google");
        Assert.assertEquals(diff.getPrintedOldValue(), "Google");
        Assert.assertEquals(diff.getNewValue(), "Yahoo");
        Assert.assertEquals(diff.getPrintedNewValue(), "Yahoo");
    }

    /**
     * Method {@link FieldDiff#getOldValue()}
     * Method {@link FieldDiff#getPrintedOldValue()}
     * Method {@link FieldDiff#getNewValue()}
     * Method {@link FieldDiff#getPrintedNewValue()}
     * @throws NoSuchFieldException
     * @throws ObjectDiffException
     */
    @Test
    public void testValueOutputHandler() throws NoSuchFieldException, ObjectDiffException {
        User user = new User();
        Field field = user.getClass().getDeclaredField("job");
        FieldDiff<String> diff = new FieldDiff<>(field, diffProperties, "Google", "Yahoo");
        Assert.assertEquals(diff.getOldValue(), "Google");
        Assert.assertEquals(diff.getPrintedOldValue(), "Job : Google");
        Assert.assertEquals(diff.getNewValue(), "Yahoo");
        Assert.assertEquals(diff.getPrintedNewValue(), "Job : Yahoo");
    }

    @Test
    public void valueOutputInstanceHandler() throws NoSuchFieldException, ObjectDiffException {
        Day day1 = new Day();
        Field field = day1.getClass().getDeclaredField("freeDay");
        FieldDiff<FreeDay> diff = new FieldDiff<>(field, diffProperties, FreeDay.SUNDAY, FreeDay.MONDAY);
        Assert.assertEquals(diff.getOldValue(), FreeDay.SUNDAY);
        Assert.assertEquals(diff.getPrintedOldValue(), FreeDay.SUNDAY.getDescription());
        Assert.assertEquals(diff.getNewValue(), FreeDay.MONDAY);
        Assert.assertEquals(diff.getPrintedNewValue(), FreeDay.MONDAY.getDescription());
    }

    @Test
    public void testNullFieldValue() throws NoSuchFieldException, ObjectDiffException {
        User user = new User();
        Field field = user.getClass().getDeclaredField("lastName");
        FieldDiff<String> diff = new FieldDiff<>(field, diffProperties, null, "Yahoo");
        Assert.assertNull(diff.getOldValue());
        Assert.assertEquals(diff.getPrintedOldValue(), "");
        Assert.assertEquals(diff.getNewValue(), "Yahoo");
        Assert.assertEquals(diff.getPrintedNewValue(), "Yahoo");
    }

    @Test
    public void testDoubleValue() throws NoSuchFieldException, ObjectDiffException {
        User user = new User();
        Field field = user.getClass().getDeclaredField("salary");
        FieldDiff<Double> diff = new FieldDiff<>(field, diffProperties, 20.0, 30.0);
        Assert.assertEquals(diff.getOldValue(), 20.0);
        Assert.assertEquals(diff.getPrintedOldValue(), "20.0");
        Assert.assertEquals(diff.getNewValue(), 30.0);
        Assert.assertEquals(diff.getPrintedNewValue(), "30.0");
    }

    @Test(expectedExceptions = ObjectDiffException.class)
    public void testBeanCreationException() throws NoSuchFieldException, ObjectDiffException {
        User user = new User();
        Field field = user.getClass().getDeclaredField("strSalary");
        FieldDiff<Double> diff = new FieldDiff<>(field, diffProperties, 20.0, 30.0);
    }

}
