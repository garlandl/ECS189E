import api.IAdmin;
import api.IInstructor;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Instructor;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Vincent on 23/2/2017.
 */
public class TestInstructor {

    private IAdmin admin;
    private IInstructor instructor;
    private IStudent student;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.instructor = new Instructor();
        this.student = new Student();
    }

    /* The homework must be assigned */
    @Test
    public void testHomeworkAssigned() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.instructor.addHomework("Instructor", "Test", 2017,
                "Homework1", "First homework assignment");
        assertTrue(this.instructor.homeworkExists("Test", 2017, "Homework1"));
    }

    /* Homework that does not exist should return false */
    @Test
    public void testHomeworkAssigned2() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.instructor.addHomework("Instructor", "Test", 2017,
                "Homework1", "First homework assignment");
        assertFalse(this.instructor.homeworkExists("Test2", 2020, "Homework2"));
    }

    /* Test all three constraints */
    @Test
    public void testHomeworkConstraints() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.instructor.addHomework("Instructor", "Test", 2017,
                "Homework1", "First homework assignment");
        this.student.registerForClass("Student1", "Test", 2017);
        this.student.submitHomework("Student1","Homework1", "Answer to homework1",
                "Test", 2017);

        assertEquals(this.admin.getClassInstructor("Test", 2017), "Instructor");
        assertTrue(this.instructor.homeworkExists("Test", 2017, "Homework1"));
        assertTrue(this.student.hasSubmitted("Student1", "Homework1", "Test", 2017));
    }

    /* Test all three constraints */
    @Test
    public void testHomeworkConstraints2() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.instructor.addHomework("Instructor", "Test", 2017,
                "Homework1", "First homework assignment");
        this.student.registerForClass("Student1", "Test", 2017);
        this.student.submitHomework("Student1","Homework1", "Answer to homework1",
                "Test", 2017);

        assertEquals(this.admin.getClassInstructor("Test", 2017), "Instructor");
        assertTrue(this.instructor.homeworkExists("Test", 2017, "Homework1"));
        assertFalse(this.student.hasSubmitted("Student2", "Homework1", "Test", 2017));
    }

    /* Grade should not be below 0% */
    @Test
    public void testAssignGrade() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.instructor.addHomework("Instructor", "Test", 2017,
                "Homework1", "First homework assignment");
        this.student.registerForClass("Student1", "Test", 2017);
        this.student.submitHomework("Student1","Homework1", "Answer to homework1",
                "Test", 2017);

        this.instructor.assignGrade("Instructor", "Test", 2017, "Homework1",
                "Student1", -1);
        assertFalse(this.instructor.getGrade("Test", 2017, "Homework1", "Student1") < 0);
    }

    /* Grade should be more than (or equal to) 0% */
    @Test
    public void testAssignGrade2() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.instructor.addHomework("Instructor", "Test", 2017,
                "Homework1", "First homework assignment");
        this.student.registerForClass("Student1", "Test", 2017);
        this.student.submitHomework("Student1","Homework1", "Answer to homework1",
                "Test", 2017);

        this.instructor.assignGrade("Instructor", "Test", 2017, "Homework1",
                "Student1", 0);
        assertTrue(this.instructor.getGrade("Test", 2017, "Homework1", "Student1") == 0);
    }

    /* Grade should be more than 0% */
    @Test
    public void testAssignGrade3() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.instructor.addHomework("Instructor", "Test", 2017,
                "Homework1", "First homework assignment");
        this.student.registerForClass("Student1", "Test", 2017);
        this.student.submitHomework("Student1","Homework1", "Answer to homework1",
                "Test", 2017);

        this.instructor.assignGrade("Instructor", "Test", 2017, "Homework1",
                "Student1", 90);
        assertTrue(this.instructor.getGrade("Test", 2017, "Homework1", "Student1") == 90);
    }

    /* Grade can be more than 100% */
    @Test
    public void testAssignGrade4() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.instructor.addHomework("Instructor", "Test", 2017,
                "Homework1", "First homework assignment");
        this.student.registerForClass("Student1", "Test", 2017);
        this.student.submitHomework("Student1","Homework1", "Answer to homework1",
                "Test", 2017);

        this.instructor.assignGrade("Instructor", "Test", 2017, "Homework1",
                "Student1", 110);
        assertTrue(this.instructor.getGrade("Test", 2017, "Homework1", "Student1") == 110);
    }
}
