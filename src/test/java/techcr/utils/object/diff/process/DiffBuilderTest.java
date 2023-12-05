package techcr.utils.object.diff.process;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import techcr.utils.object.diff.datetest.DiffDate;
import techcr.utils.object.diff.enumtest.Day;
import techcr.utils.object.diff.enumtest.WeekDay;
import techcr.utils.object.diff.enumtest.WeekendDay;
import techcr.utils.object.diff.generaltest.School;
import techcr.utils.object.diff.generaltest.User;
import techcr.utils.object.diff.generaltest.UserSchool;
import techcr.utils.object.diff.parentlevel.ChildLevelOne;
import techcr.utils.object.diff.parentlevel.ChildLevelTwo;
import techcr.utils.object.diff.parentlevel.IgnoreChildLevelTwo;
import techcr.utils.object.diff.type.FieldDiff;
import techcr.utils.object.diff.type.ObjectDiff;
import techcr.utils.object.diff.type.exception.ObjectDiffException;

import static org.hamcrest.MatcherAssert.assertThat;

public class DiffBuilderTest {

    @Test
    public void testIgnoreDiff() throws ObjectDiffException {

        User user1 = new User("Google", 20, "xx");
        User user2 = new User("Yahoo", 30, "yy");

        DiffBuilder diffBuilder = new DiffBuilder();
        ObjectDiff objectDiff = diffBuilder.diff(user1, user2);
        List<FieldDiff> fieldDiffs = objectDiff.getDiffs();
        Assert.assertEquals(fieldDiffs.size(), 2);
        Assert.assertFalse(fieldDiffs.stream().anyMatch(diff -> diff.getFieldName().equals("pw")));


        assertThat(fieldDiffs, Matchers.hasItem(
                Matchers.allOf(
                        Matchers.hasProperty("oldValue", Matchers.equalTo("Google")),
                        Matchers.hasProperty("newValue", Matchers.equalTo("Yahoo")))));
        assertThat(fieldDiffs, Matchers.hasItem(
                Matchers.allOf(
                        Matchers.hasProperty("oldValue", Matchers.equalTo(20)),
                        Matchers.hasProperty("newValue", Matchers.equalTo(30)))));

    }

    @Test
    public void testValueEvaluator() throws ObjectDiffException {
        User user1 = new User();
        user1.setAddress("Col");
        User user2 = new User();
        user2.setAddress("Colombo");
        DiffBuilder diffBuilder = new DiffBuilder();
        ObjectDiff objectDiff = diffBuilder.diff(user1, user2);
        List<FieldDiff> fieldDiffs = objectDiff.getDiffs();
        Assert.assertEquals(fieldDiffs.size(), 0);

        user2.setAddress("Gampaha");
        objectDiff = diffBuilder.diff(user1, user2);
        fieldDiffs = objectDiff.getDiffs();
        Assert.assertEquals(fieldDiffs.size(), 1);
        assertThat(fieldDiffs, Matchers.hasItem(
                Matchers.allOf(
                        Matchers.hasProperty("oldValue", Matchers.equalTo("Col")),
                        Matchers.hasProperty("newValue", Matchers.equalTo("Gampaha")))));
    }

    @Test
    public void testCustomPrinterNullAliasSplitter() throws ObjectDiffException {
        User user1 = new User();
        user1.setName("Google");
        User user2 = new User();
        user2.setName("Yahoo");
        Function<String, String> printFunction = s -> s + " Changed";

        DiffBuilder builder = new DiffBuilder().
                addDiffPrinter(fieldDiff -> printFunction.apply(fieldDiff.getFieldDisplayName()));

        String diff = builder.diff(user1, user2).getDiff();
        //System.out.println(diff);
        Assert.assertTrue(diff.contains(printFunction.apply("Name")));

    }

    @Test
    public void testCustomNullAlias() throws ObjectDiffException {
        User user1 = new User();
        //user1.setName("Google");
        User user2 = new User();
        user2.setName("Yahoo");

        DiffBuilder builder = new DiffBuilder().addNullAlias("NULL");

        String diff = builder.diff(user1, user2).getDiff();
        //System.out.println(diff);
        Assert.assertEquals(diff, " { Name : NULL -> Yahoo } ");

    }

    @Test
    public void testCustomSplitter() throws ObjectDiffException {
        User user1 = new User();
        //user1.setName("Google");
        User user2 = new User();
        user2.setName("Yahoo");
        user2.setAge(30);

        DiffBuilder builder = new DiffBuilder().addDiffSplitter("__");

        String diff = builder.diff(user1, user2).getDiff();
        Assert.assertEquals(diff.split("__").length, 2);

    }


    @Test
    public void testIgnoreDiffClass() throws ObjectDiffException {
        School school1 = new School("Ananda");
        School school2 = new School("Nalanada");

        DiffBuilder builder = new DiffBuilder();
        ObjectDiff objectDiff = builder.diff(school1, school2);
        List<FieldDiff> fieldDiffs = objectDiff.getDiffs();
        Assert.assertEquals(fieldDiffs.size(), 0);

    }

    @Test
    public void testEntityType() throws ObjectDiffException {
        User anandaUser = new User("Google", 20, "xx");
        UserSchool ananda = new UserSchool("Ananda");
        UserSchool parentAnanda = new UserSchool("Parent");
        anandaUser.setParentSchool(parentAnanda);
        anandaUser.setUserSchool(ananda);
        User nalandaUser = new User("Yahoo", 20, "yy");
        UserSchool nalanda = new UserSchool("Nalanada");
        UserSchool parentNalanda = new UserSchool("Parent");
        nalandaUser.setParentSchool(parentNalanda);
        nalandaUser.setUserSchool(nalanda);

        DiffBuilder diffBuilder = new DiffBuilder();
        ObjectDiff diff = diffBuilder.diff(anandaUser, nalandaUser);
        Assert.assertEquals(diff.getDiffs().size(), 1);
        Assert.assertEquals(diff.getObjectDiffs().size(), 1);
    }

    @Test
    public void testEntityTypeIgnoreDiff() throws ObjectDiffException {
        User anandaUser = new User();
        School ananda = new School("Ananda");
        anandaUser.setSchool(ananda);
        User nalandaUser = new User();
        School nalanda = new School("Nalanada");
        nalandaUser.setSchool(nalanda);
        DiffBuilder diffBuilder = new DiffBuilder();
        ObjectDiff diff = diffBuilder.diff(anandaUser, nalandaUser);
        Assert.assertEquals(diff.getObjectDiffs().size(), 0);

    }

    @Test
    public void testForIgnoreFields() throws ObjectDiffException {
        User user1 = new User("Google", 20, "xx");
        User user2 = new User("Yahoo", 30, "yy");
        DiffBuilder diffBuilder = new DiffBuilder().addIgnoreField("name");
        ObjectDiff diff = diffBuilder.diff(user1, user2);

        Assert.assertEquals(diff.getDiffs().size(), 1);
        Assert.assertEquals(diff.getDiff(), " { Age : 20 -> 30 } ");

        List<String> ignoreList = Arrays.asList("age");
        diffBuilder.addIgnoreField(ignoreList);
        diff = diffBuilder.diff(user1, user2);
        Assert.assertEquals(diff.getDiffs().size(), 0);
    }

    @Test(expectedExceptions = ObjectDiffException.class)
    public void testBeanCreationException() throws NoSuchFieldException, ObjectDiffException {
        User user1 = new User();
        user1.setStrSalary("20.5");
        User user2 = new User();
        user2.setStrSalary("21.5");
        DiffBuilder diffBuilder = new DiffBuilder();
        diffBuilder.diff(user1, user2);

    }

    @Test
    public void testEnumName() throws ObjectDiffException {
        Day day1 = new Day(WeekDay.FRIDAY, WeekendDay.SATURDAY);
        Day day2 = new Day(WeekDay.MONDAY, WeekendDay.SATURDAY);

        ObjectDiff objectDiff = new DiffBuilder().diff(day1, day2);
        Assert.assertEquals(objectDiff.getDiff(), " { Week Day : FRIDAY -> MONDAY } ");
    }

    @Test
    public void testEnumDesc() throws ObjectDiffException {
        Day day1 = new Day(WeekDay.FRIDAY, WeekendDay.SATURDAY);
        Day day2 = new Day(WeekDay.FRIDAY, WeekendDay.SUNDAY);

        ObjectDiff objectDiff = new DiffBuilder().diff(day1, day2);
        Assert.assertEquals(objectDiff.getDiff(), " { Weekend Day : Saturday -> Sunday } ");
    }

    @Test
    public void testForParentWithNoAnnotation() throws ObjectDiffException {
        ChildLevelOne levelOne1 = new ChildLevelOne("Parent One", "Child1");
        ChildLevelOne levelOne2 = new ChildLevelOne("Parent Two", "Child2");
        ObjectDiff objectDiff = new DiffBuilder().diff(levelOne1, levelOne2);
        Assert.assertEquals(objectDiff.getDiffs().size(), 1);
    }

    @Test
    public void testForParentDiffLevel1() throws ObjectDiffException {
        ChildLevelTwo levelOne1 = new ChildLevelTwo("Parent One", "Child L 1_1", "Chi L 2_1");
        ChildLevelTwo levelOne2 = new ChildLevelTwo("Parent Two", "Child L 1_2", "Chi L 2_2");
        ObjectDiff objectDiff = new DiffBuilder().diff(levelOne1, levelOne2);
        Assert.assertEquals(objectDiff.getDiffs().size(), 2);
    }

    @Test
    public void testForParentDiffIgnore() throws ObjectDiffException {
        IgnoreChildLevelTwo levelOne1 = new IgnoreChildLevelTwo("Parent One", "Child L 1_1", "Chi L 2_1");
        IgnoreChildLevelTwo levelOne2 = new IgnoreChildLevelTwo("Parent Two", "Child L 1_2", "Chi L 2_2");
        ObjectDiff objectDiff = new DiffBuilder().diff(levelOne1, levelOne2);
        Assert.assertEquals(objectDiff.getDiffs().size(), 1);
    }

    @Test
    public void testDefaultDateFormat() throws ObjectDiffException {
        DiffDate diffDate1 = new DiffDate().setDefaultDate(2019, 12, 31);
        DiffDate diffDate2 = new DiffDate().setDefaultDate(2020, 12, 31);
        String diff = new DiffBuilder().diff(diffDate1, diffDate2).getDiff();
        Assert.assertEquals(diff, " { Default Date : 12-31-2019 -> 12-31-2020 } ");
    }

    @Test
    public void testCustomDateFormat() throws ObjectDiffException {
        DiffDate diffDate1 = new DiffDate().setDefaultDate(2019, 12, 31);
        DiffDate diffDate2 = new DiffDate().setDefaultDate(2020, 12, 31);
        String diff = new DiffBuilder().setDateFormat("yyyy-MM-dd").diff(diffDate1, diffDate2).getDiff();
        Assert.assertEquals(diff, " { Default Date : 2019-12-31 -> 2020-12-31 } ");
    }

    @Test
    public void testDefaultDateTypeDate() throws ObjectDiffException {
        DiffDate diffDate1 = new DiffDate().setDefaultDateTypeDate(2019, 12, 31);
        DiffDate diffDate2 = new DiffDate().setDefaultDateTypeDate(2020, 12, 31);
        String diff = new DiffBuilder().setDateFormat("yyyy-MM-dd").diff(diffDate1, diffDate2).getDiff();
        Assert.assertEquals(diff, " { D D T D : 12-31-2019 -> 12-31-2020 } ");
    }

    @Test
    public void testCustomDateTypeDate() throws ObjectDiffException {
        DiffDate diffDate1 = new DiffDate().setCustomDateTypeDate(2019, 12, 31);
        DiffDate diffDate2 = new DiffDate().setCustomDateTypeDate(2020, 12, 31);
        String diff = new DiffBuilder().setDateFormat("yyyy-MM-dd").diff(diffDate1, diffDate2).getDiff();
        Assert.assertEquals(diff, " { C D T D : 31-2019-12 -> 31-2020-12 } ");
    }

    @Test
    public void testCustomObjectDiffType() throws ObjectDiffException {
        UserSchool schoolOne = new UserSchool("Ananda");
        User userOne = new User();
        userOne.setObjectDiffSchool(schoolOne);
        UserSchool schoolTwo = new UserSchool("Nalanda");
        User userTwo = new User();
        userTwo.setObjectDiffSchool(schoolTwo);

        ObjectDiff objectDiff = new DiffBuilder().diff(userOne, userTwo);
        Assert.assertEquals(objectDiff.getDiff(), "Object Diff School -  { Name : Ananda -> Nalanda } ");
    }

    @Test
    public void testEntityTypeUpdateToNull() throws ObjectDiffException {
        UserSchool schoolOne = new UserSchool("Ananda");
        User userOne = new User();
        userOne.setObjectDiffSchool(schoolOne);
        User userTwo = new User();
        ObjectDiff objectDiff = new DiffBuilder().diff(userOne, userTwo);
        Assert.assertEquals(objectDiff.getDiff(), "Object Diff School -  { Name : Ananda -> EMPTY } ");
        objectDiff = new DiffBuilder().diff(userTwo, userOne);
        Assert.assertEquals(objectDiff.getDiff(), "Object Diff School -  { Name : EMPTY -> Ananda } ");

    }
}
