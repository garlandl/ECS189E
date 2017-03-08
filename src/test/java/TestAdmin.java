import api.IAdmin;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test cases for Admin interface
 */
public class TestAdmin {

    private IAdmin admin;
    private IStudent student1;
    private IStudent student2;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.student1 = new Student();
        this.student2 = new Student();
    }

    /* A class this year should be valid */
    @Test
    public void testMakeClassYear() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }

    /* A class cannot be in the past */
    @Test
    public void testMakeClassYear2() {
        this.admin.createClass("Test2", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test2", 2016));
    }

    /* A future class should be valid */
    @Test
    public void testMakeClassYear3() {
        this.admin.createClass("Test3", 2020, "Instructor", 15);
        assertTrue(this.admin.classExists("Test3", 2020));
    }

    /* A class capacity must be greater than zero */
    @Test
    public void testMakeClassCapacity() {
        this.admin.createClass("Test", 2017, "Instructor", 1);
        assertTrue(this.admin.getClassCapacity("Test", 2017) > 0);
    }

    /* A class capacity cannot be less than zero */
    @Test
    public void testMakeClassCapacity2() {
        this.admin.createClass("Test2", 2017, "Instructor", -1);
        assertFalse(this.admin.getClassCapacity("Test2", 2017) <= 0);
    }

    /* A class capacity can be equal to the # students currently enrolled */
    @Test
    public void testChangeCapacity() {
        this.admin.createClass("Test", 2017, "Instructor", 5);
        this.student1.registerForClass("Student1", "Test", 2017);
        this.student2.registerForClass("Student2", "Test", 2017);

        /* Should set capacity to two */
        this.admin.changeCapacity("Test", 2017, 2);
        assertTrue(this.admin.getClassCapacity("Test", 2017) == 2);
    }

    /* A class capacity must be at least equal to # students enrolled */
    @Test
    public void testChangeCapacity2() {
        this.admin.createClass("Test2", 2017, "Instructor", 2);
        this.student1.registerForClass("Student1", "Test2", 2017);
        this.student2.registerForClass("Student2", "Test2", 2017);

        /* Should not let capacity be set less than two */
        this.admin.changeCapacity("Test2", 2017, 1);
        assertFalse(this.admin.getClassCapacity("Test2", 2017) == 1);
    }

    /* A class capacity can be more than the # students currently enrolled */
    @Test
    public void testChangeCapacity3() {
        this.admin.createClass("Test", 2017, "Instructor", 3);
        this.student1.registerForClass("Student1", "Test", 2017);
        this.student2.registerForClass("Student2", "Test", 2017);

        /* Should set capacity to two */
        this.admin.changeCapacity("Test", 2017, 5);
        assertTrue(this.admin.getClassCapacity("Test", 2017) == 5);
    }
}
