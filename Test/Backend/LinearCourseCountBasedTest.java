package Test.Backend;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import Backend.StipendStrategies.LinearCourseCountBased;
import College.Course;
import College.People.Student;

public class LinearCourseCountBasedTest {
    private LinearCourseCountBased stipend;
    
    @Before
    public void Init() {
        stipend = new LinearCourseCountBased();
    }
    
    @Test
    public void LinearCourseCountBasedRandomTest() {
        Random rand = new Random(System.currentTimeMillis());
        for(int c = 0; c < 100; ++c) {
            final int multiplayer = rand.nextInt(10000);
            stipend.setMultiplayer(multiplayer);
            for(int j = 0; j < 100; ++j) {
                final Student student = new Student(null, null, null, null);
                final int count = rand.nextInt(1000);
                String tmp = "";
                for(int i = 0; i < count; ++i) {
                    student.addCourse(new Course(tmp));
                    tmp += ".";
                }
                stipend.stipend(student);
                assertEquals(stipend.stipend(student), count * multiplayer, 0.01);
            }
        }
    }
}
