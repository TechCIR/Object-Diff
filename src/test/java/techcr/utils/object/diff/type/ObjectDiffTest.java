package techcr.utils.object.diff.type;

import java.lang.reflect.Field;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import techcr.utils.object.diff.generaltest.User;
import techcr.utils.object.diff.process.DiffProperties;
import techcr.utils.object.diff.type.exception.ObjectDiffException;

import static org.testng.Assert.*;

public class ObjectDiffTest {

    @Test
    public void testIsValidForEmpty() {
        ObjectDiff objectDiff = new ObjectDiff("", new DiffProperties());
        boolean isValid = objectDiff.isValid();
        Assert.assertFalse(isValid);
    }

    @Test
    public void testIsValidForField() throws NoSuchFieldException, ObjectDiffException {
        User user = new User();
        Field field = user.getClass().getDeclaredField("firstName");

        ObjectDiff objectDiff = new ObjectDiff("", new DiffProperties());
        objectDiff.addFieldDiff(field, "Google", "Yahoo");
        boolean isValid = objectDiff.isValid();
        Assert.assertTrue(isValid);

    }

    @Test
    public void testIsValidForObjectDiff() throws ObjectDiffException {
        ObjectDiff diffMock = Mockito.mock(ObjectDiff.class);
        ObjectDiff objectDiff = new ObjectDiff("", new DiffProperties());
        objectDiff.addObjectDiff(diffMock);
        boolean isValid = objectDiff.isValid();
        Assert.assertTrue(isValid);

    }

    @Test
    public void testIsValidForFieldAndObjectDiff() throws NoSuchFieldException, ObjectDiffException {
        ObjectDiff diffMock = Mockito.mock(ObjectDiff.class);

        User user = new User();
        Field field = user.getClass().getDeclaredField("firstName");

        ObjectDiff objectDiff = new ObjectDiff("", new DiffProperties());
        objectDiff.addObjectDiff(diffMock);
        objectDiff.addFieldDiff(field, "Google", "Yahoo");

        boolean isValid = objectDiff.isValid();
        Assert.assertTrue(isValid);

    }

    @Test
    public void testGetDiff() throws NoSuchFieldException, ObjectDiffException {
        User user = new User();
        Field userField = user.getClass().getDeclaredField("firstName");
        Field ageField = user.getClass().getDeclaredField("age");

        ObjectDiff objectDiff = new ObjectDiff("", new DiffProperties());
        objectDiff.addFieldDiff(userField, "Google", "Yahoo");
        objectDiff.addFieldDiff(ageField, 20, 30);
        assertEquals(objectDiff.getDiff(), " { First Name : Google -> Yahoo } , { Age : 20 -> 30 } ");
    }

    @Test
    public void testDefaultObjectDiffPrinter() throws NoSuchFieldException, ObjectDiffException {
        DiffProperties properties = new DiffProperties();
        ObjectDiff innerDiff = new ObjectDiff("Inner", properties);

        User user = new User();
        Field userField = user.getClass().getDeclaredField("firstName");
        innerDiff.addFieldDiff(userField, "Google", "Yahoo");

        ObjectDiff objectDiff = new ObjectDiff("", properties);
        objectDiff.addObjectDiff(innerDiff);

        assertEquals(objectDiff.getDiff(), "Inner [  { First Name : Google -> Yahoo }  ] ");
    }

    @Test
    public void testCustomerObjectDiffPrinter() throws NoSuchFieldException, ObjectDiffException {
        DiffProperties properties = new DiffProperties();
        properties.setObjectDiffPrinter((entityName, entityDiff) -> entityName + " - " + entityDiff);
        ObjectDiff innerDiff = new ObjectDiff("Inner", properties);

        User user = new User();
        Field userField = user.getClass().getDeclaredField("firstName");
        innerDiff.addFieldDiff(userField, "Google", "Yahoo");

        ObjectDiff objectDiff = new ObjectDiff("", properties);
        objectDiff.addObjectDiff(innerDiff);

        assertEquals(objectDiff.getDiff(), "Inner -  { First Name : Google -> Yahoo } ");
    }

}
