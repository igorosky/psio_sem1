package College;

import java.io.Serializable;
// import java.util.HashSet;

// import College.People.Scientist;
// import College.People.Student;

public class Course implements Serializable {
    private String name;
    // private HashSet<Scientist> lecturers;
    // private HashSet<Student> students;

    // public static final class CourseFriendship { private CourseFriendship() { } }

    public Course(final String name) {
        this.name = name;
        // lecturers = new HashSet<>();
        // students = new HashSet<>();
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof Course && ((Course)obj).getName().equals(getName());
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    // public final HashSet<Scientist> getLecturers() {
    //     return lecturers;
    // }

    // public boolean addLecturer(Scientist lecturer, final Scientist.ScientistFriendship x_) {
    //     return lecturers.add(lecturer);
    // }

    // public boolean addLecturer(Scientist lecturer) {
    //     lecturer.addCourse(this, new CourseFriendship());
    //     return lecturers.add(lecturer);
    // }

    // public boolean removeLecturer(Scientist lecturer, final Scientist.ScientistFriendship x_) {
    //     return lecturers.remove(lecturer);
    // }

    // public boolean removeLecturer(Scientist lecturer) {
    //     lecturer.removeCourse(this, new CourseFriendship());
    //     return lecturers.remove(lecturer);
    // }

    // public boolean addStudent(Student student, final Student.StudentFriendship _x) {
    //     return students.add(student);
    // }
    
    // public boolean addStudent(Student student) {
    //     student.addCourse(this, new CourseFriendship());
    //     return students.add(student);
    // }

    // public boolean removeStudent(Student student, final Student.StudentFriendship _x) {
    //     return students.remove(student);
    // }

    // public boolean removeStudent(Student student) {
    //     student.removeCourse(this, new CourseFriendship());
    //     return students.remove(student);
    // }

    // public final HashSet<Student> getStudents() {
    //     return students;
    // }
}
