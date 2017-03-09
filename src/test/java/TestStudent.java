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

    /* Cannot register for a class that does not exist */
    @Test
    public void testRegisterClassExists() {
        this.student1.registerForClass("Student1", "Test", 2017);
        assertFalse(this.student1.isRegisteredFor("Student1", "Test", 2017));
    }

    /* Successfully drop a class */
    @Test
    public void testDropClass() {
        this.admin.createClass("Test", 2017, "Instructor", 1);
        this.instructor.addHomework("Instructor", "Test", 2017,
                "Homework1", "First homework assignment");
        this.student1.registerForClass("Student1", "Test", 2017);
        this.student1.dropClass("Student1", "Test", 2017);
        assertFalse(this.student1.isRegisteredFor("Student1", "Test", 2017));
    }

    /* Successfully submit a homework */
    @Test
    public void testSubmitHomework() {
        this.admin.createClass("Test", 2017, "Instructor", 1);
        this.instructor.addHomework("Instructor", "Test", 2017,
                "Homework1", "First homework assignment");
        this.student1.registerForClass("Student1", "Test", 2017);
        this.student1.submitHomework("Student1", "Homework1", "Answer to HW1",
                "Test", 2017);
        assertTrue(this.student1.hasSubmitted("Student1", "Homework1", "Test", 2017));
    }

    /* Fail if homework does no exist */
    @Test
    public void testSubmitHomework2() {
        this.admin.createClass("Test", 2017, "Instructor", 1);
        this.student1.registerForClass("Student1", "Test", 2017);
        this.student1.submitHomework("Student1", "Homework1", "Answer to HW1",
                "Test", 2017);
        assertFalse(this.student1.hasSubmitted("Student1", "Homework1", "Test", 2017));
    }

    /* Fail if student is not registered */
    @Test
    public void testSubmitHomework3() {
        this.admin.createClass("Test", 2017, "Instructor", 1);
        this.instructor.addHomework("Instructor", "Test", 2017,
                "Homework1", "First homework assignment");
        this.student1.submitHomework("Student1", "Homework1", "Answer to HW1",
                "Test", 2017);
        assertFalse(this.student1.hasSubmitted("Student1", "Homework1", "Test", 2017));
    }

    /* Fail if class is not taught (this year or otherwise) */
    @Test
    public void testSubmitHomework4() {
        this.instructor.addHomework("Instructor", "Test", 2017,
                "Homework1", "First homework assignment");
        this.student1.registerForClass("Student1", "Test", 2017);
        this.student1.submitHomework("Student1", "Homework1", "Answer to HW1",
                "Test", 2017);
        assertFalse(this.student1.hasSubmitted("Student1", "Homework1", "Test", 2017));
    }

    /* Fail if class is taught in the future */
    @Test
    public void testSubmitHomework5() {
        this.admin.createClass("Test", 2018, "Instructor", 5);
        this.instructor.addHomework("Instructor", "Test", 2018,
                "Homework1", "First homework assignment");
        this.student1.registerForClass("Student1", "Test", 2018);
        this.student1.submitHomework("Student1", "Homework1", "Answer to HW1",
                "Test", 2018);
        assertFalse(this.student1.hasSubmitted("Student1", "Homework1", "Test", 2018));
    }
}
