import api.IAdmin;
import api.IInstructor;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Instructor;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Vincent on 23/2/2017.
 */
public class TestStudent {

    private IAdmin admin;
    private IInstructor instructor;
    private IStudent student1;
    private IStudent student2;
    private IStudent student3;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.instructor = new Instructor();
        this.student1 = new Student();
        this.student2 = new Student();
        this.student3 = new Student();
    }

    /* Should not allow student2 to register past capacity */
    @Test
    public void testRegisterCapacity() {
        this.admin.createClass("Test", 2017, "Instructor", 1);
        this.instructor.addHomework("Instructor", "Test", 2017,
                "Homework1", "First homework assignment");
        this.student1.registerForClass("Student1", "Test", 2017);
        this.student2.registerForClass("Student2", "Test", 2017);

        assertFalse(this.student2.isRegisteredFor("Student2", "Test", 2017));
    }

    /* Student1 should be registered */
    @Test
    public void testRegisterCapacity2() {
        this.admin.createClass("Test", 2017, "Instructor", 1);
        this.instructor.addHomework("Instructor", "Test", 2017,
                "Homework1", "First homework assignment");
        this.student1.registerForClass("Student1", "Test", 2017);

        assertTrue(this.student1.isRegisteredFor("Student1", "Test", 2017));
    }

    /* Student2 should be registered (capacity 2) */
    @Test
    public void testRegisterCapacity3() {
        this.admin.createClass("Test", 2017, "Instructor", 2);
        this.instructor.addHomework("Instructor", "Test", 2017,
                "Homework1", "First homework assignment");
        this.student1.registerForClass("Student1", "Test", 2017);
        this.student2.registerForClass("Student2", "Test", 2017);

        assertTrue(this.student2.isRegisteredFor("Student2", "Test", 2017));
    }

    /* Drop class and add another student */
    @Test
    public void testRegisterCapacity4() {
        this.admin.createClass("Test", 2017, "Instructor", 2);
        this.instructor.addHomework("Instructor", "Test", 2017,
                "Homework1", "First homework assignment");
        this.student1.registerForClass("Student1", "Test", 2017);
        this.student2.registerForClass("Student2", "Test", 2017);
        this.student2.dropClass("Student2", "Test", 2017);

        this.student3.registerForClass("Student3", "Test", 2017);
        assertFalse(this.student2.isRegisteredFor("Student2", "Test", 2017));
        assertTrue(this.student3.isRegisteredFor("Student3", "Test", 2017));
    }
}
