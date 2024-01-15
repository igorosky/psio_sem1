package Backend.StipendStrategies;

import Backend.Stipend;
import College.People.Student;

public class LinearCourseCountBased implements Stipend {

    private float multiplayer;

    public LinearCourseCountBased() {
        multiplayer = 100f;
    }

    public LinearCourseCountBased(final float multiplayer) {
        this.multiplayer = multiplayer;
    }

    @Override
    public float stipend(Student student) {
        return (float)student.getCourses().size() * multiplayer;
    }
    
    public float getMultiplayer() {
        return multiplayer;
    }

    public void setMultiplayer(float multiplayer) {
        this.multiplayer = multiplayer;
    }
    
}
