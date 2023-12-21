package College.People;

import java.util.HashSet;

import College.Course;

public class Student extends Person {
    private String indexNumber;
    private HashSet<Course> courses;
    
    public static final class StudentFriendship { private StudentFriendship() { } }

    public Student(final String fname, final String lname, final String mail, final String indexNumber) {
        super(fname, lname, mail);
        this.indexNumber = indexNumber;
        courses = new HashSet<>();
    }

    public String getIndexNumber() {
        return indexNumber;
    }

    public void setIndexNumber(final String indexNumber) {
        this.indexNumber = indexNumber;
    }

    @Override
    public String toString() {
        return String.format("Student %s (index: %s)", super.toString(), indexNumber);
    }

    // public boolean addCourse(Course course, Course.CourseFriendship _x) {
    //     return courses.add(course);
    // }

    public boolean addCourse(Course course) {
        // course.addStudent(this, new StudentFriendship());
        return courses.add(course);
    }

    // public boolean removeCourse(Course course, Course.CourseFriendship _x) {
    //     return courses.remove(course);
    // }

    public boolean removeCourse(Course course) {
    //     course.removeStudent(this, new StudentFriendship());
        return courses.remove(course);
    }

    public final HashSet<Course> getCourses() {
        return courses;
    }
}
