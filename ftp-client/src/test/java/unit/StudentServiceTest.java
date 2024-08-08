package unit;

import com.bashkir777.entities.Student;
import com.bashkir777.services.StudentService;
import org.testng.Assert;
import org.testng.annotations.Test;

public class StudentServiceTest {

    private final String STUDENT_NAME = "test_name";
    private final Integer STUDENT_ID = 1;

    private final StudentService studentService = new StudentService();


    @Test(groups="unit", description = "StudentService correctly adds student")
    public void studentServiceCorrectlyAddsStudent() {
        studentService.addStudent(new Student(STUDENT_ID, STUDENT_NAME));
        Student student = studentService.getPriorityQueue().peek();
        Assert.assertNotNull(student);
        Assert.assertEquals(student.getId(), STUDENT_ID);
        Assert.assertEquals(student.getName(), STUDENT_NAME);
    }

    @Test(groups = {"withStudentInQueue", "unit"}, dependsOnMethods = "studentServiceCorrectlyAddsStudent"
            , description = "StudentService correctly returns student by id")
    public void studentServiceCorrectlyReturnsStudentById() {
        Student student = studentService.getStudentById(STUDENT_ID);;
        Assert.assertNotNull(student);
        Assert.assertEquals(student.getId(), STUDENT_ID);
        Assert.assertEquals(student.getName(), STUDENT_NAME);
    }



    @Test(groups = {"withStudentInQueue", "unit"}, dependsOnMethods = "studentServiceCorrectlyAddsStudent"
            , description = "StudentService correctly serialize and uploads queue")
    public void studentServiceCorrectlySerializeAndUploadsQueue() {
        String serializedQueue = studentService.queueToString();
        studentService.clean();
        studentService.uploadQueue(serializedQueue);
        Student student = studentService.getPriorityQueue().peek();
        Assert.assertNotNull(student);
        Assert.assertEquals(student.getId(), STUDENT_ID);
        Assert.assertEquals(student.getName(), STUDENT_NAME);
    }

    @Test(groups="unit", dependsOnGroups = "withStudentInQueue",
            description = "StudentService correctly removes student by ID")
    public void studentServiceCorrectlyRemovesStudentByID() {
        studentService.removeStudentById(STUDENT_ID);
        Assert.assertTrue(
                studentService
                        .getPriorityQueue()
                        .isEmpty()
        );
    }


}
