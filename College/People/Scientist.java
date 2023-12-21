package College.People;

// import java.util.HashSet;

// import College.Course;
import College.Department;

public class Scientist extends Employee {
    private int hIndex;
    // private HashSet<Course> canLecture;

    // public static final class ScientistFriendship { private ScientistFriendship() {} }
    
    public Scientist(final String fname, final String lname, final String mail, final Department department, final int hIndex) {
        super(fname, lname, mail, department);
        this.hIndex = hIndex;
        // canLecture = new HashSet<>();
    }

    public int gethIndex() {
        return hIndex;
    }

    public void sethIndex(final int hIndex) {
        this.hIndex = hIndex;
    }

    @Override
    public String toString() {
        return String.format("Scientist %s HIndex: %d", super.toString(), hIndex);
    }

    // public boolean addCourse(Course course, final Course.CourseFriendship _x) {
    //     return canLecture.add(course);
    // }

    // public boolean addCourse(Course course) {
    //     course.addLecturer(this, new ScientistFriendship());
    //     return canLecture.add(course);
    // }

    // public boolean removeCourse(Course course, final Course.CourseFriendship _x) {
    //     return canLecture.remove(course);
    // }

    // public boolean removeCourse(Course course) {
    //     course.removeLecturer(this, new ScientistFriendship());
    //     return canLecture.remove(course);
    // }
    // public final HashSet<Course> getCanLecture() {
    //     return canLecture;
    // }
}
