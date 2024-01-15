package Backend.StipendStrategies;

import Backend.Stipend;
import College.People.Student;

public class LogCourseCountBased implements Stipend {

    private float multiplayer;
    private int logBase;
    private double logBaseFactor;

    public LogCourseCountBased() {
        multiplayer = 100f;
        setLogBase(2);
    }

    public LogCourseCountBased(final float multiplayer) {
        this();
        this.multiplayer = multiplayer;
    }

    public LogCourseCountBased(final float multiplayer, final int logBase) {
        this();
        this.multiplayer = multiplayer;
        setLogBase(logBase);
    }

    @Override
    public float stipend(Student student) {
        return Math.max((float)(Math.log((double)student.getCourses().size())/logBaseFactor) * multiplayer, 0f);
    }
    
    public float getMultiplayer() {
        return multiplayer;
    }

    public void setMultiplayer(float multiplayer) {
        this.multiplayer = multiplayer;
    }
    
    public int getLogBase() {
        return logBase;
    }

    public void setLogBase(int logBase) {
        if(logBase > 1) {
            this.logBase = logBase;
            logBaseFactor = Math.log(logBase);
        }
    }
    
}
