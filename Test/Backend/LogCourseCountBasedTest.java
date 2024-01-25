package Test.Backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import Backend.StipendStrategies.LogCourseCountBased;
import College.Course;
import College.People.Student;

public class LogCourseCountBasedTest {
    private LogCourseCountBased stipend;
    
    @Before
    public void Init() {
        stipend = new LogCourseCountBased();
    }

    @Test
    public void LogCourseCountBasedRandomTest() {
        Random rand = new Random(System.currentTimeMillis());
        for(int c = 0; c < 100; ++c) {
            final int base = rand.nextInt(98) + 2;
            stipend.setLogBase(base);
            final int multiplayer = rand.nextInt(1000);
            stipend.setMultiplayer(multiplayer);
            for(int j = 0; j < 100; ++j) {
                final Student student = new Student(null, null, null, null);
                final int count = rand.nextInt(1000) + 1;
                String tmp = "";
                for(int i = 0; i < count; ++i) {
                    student.addCourse(new Course(tmp));
                    tmp += ".";
                }
                stipend.stipend(student);
                assertEquals(stipend.stipend(student), Math.log((double)count)/Math.log(base) * multiplayer, 0.01);
            }
        }
    }
}
