import api.IAdmin;
import api.IInstructor;
import api.core.impl.Admin;
import api.core.impl.Instructor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Vincent on 23/2/2017.
 */
public class TestInstructor {

    private IAdmin admin;
    private IInstructor instructor;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.instructor = new Instructor();
    }

    /* Check that the instructor/class pair exists (className & year) */
    @Test
    public void testInstructorClassPair() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        /* getClassInstructor should return the instruct name as a String (Therefore not empty) */
        assertTrue(!this.admin.getClassInstructor("Test", 2017).isEmpty());

        this.instructor.addHomework("Instructor", "Test", 2017,
                "Homework1", "First homework assignment");
    }

    /* The homework must be assigned */
    @Test
    public void testHomeworkAssigned() {
        this.instructor.addHomework("Instructor", "Test", 2017,
                "Homework1", "First homework assignment");
        assertTrue(this.instructor.homeworkExists("Test", 2017, "Homework1"));
    }

    /* Homework that does not exist should return false */
    @Test
    public void testHomeworkAssigned2() {
        this.instructor.addHomework("Instructor", "Test", 2017,
                "Homework1", "First homework assignment");
        assertFalse(this.instructor.homeworkExists("Test2", 2020, "Homework2"));
    }


}
