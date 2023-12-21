import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import College.Course;
import College.Department;
import College.People.Person;
import College.People.Scientist;
import College.People.Student;
import College.People.Worker;
import ConsoleUI.ConsoleUI;
import ConsoleUI.Validators.NumberInRange;
import ConsoleUI.Validators.StringNotEmpty;
import PersonFilters.PersonFilter;
import PersonFilters.PersonFilterByHIndexGreater;
import PersonFilters.PersonFilterByIndex;
import PersonFilters.PersonFilterByLName;
import PersonFilters.PersonFilterByPosition;
import PersonFilters.PersonFilterByStudentsIndex;

public final class Menu {
    public static void mainMenu() {
        while (true) {
            switch (ConsoleUI.choice(List.of("Open", "New", "Exit"))) {
                case 1: // Open
                    final String path = ConsoleUI.inputString("Path to file", List.of());
                    Optional<ArrayList<Person>> result = ObjectSaver.deserialize(path);
                    if(!result.isPresent()) {
                        ConsoleUI.print("Couldn't open file");
                        continue;
                    }
                    dbMenu(result.get());
                    break;
                case 2: // New
                    dbMenu(new ArrayList<>());
                    break;
                case 3: // Exit
                    return;
                default:
                    ConsoleUI.print("Unexpected input");
                    break;
            }
        }
    }

    public static ArrayList<Person> listPeople(final ArrayList<Person> list, List<PersonFilter> filters) {
        ArrayList<Person> ans = new ArrayList<>();
        for (final Person person : list) {
            boolean valid = true;
            for (PersonFilter personFilter : filters) {
                if (!personFilter.isGood(person)) {
                    valid = false;
                    break;
                }
            }
            if(valid) {
                ans.add(person);
            }
        }
        return ans;
    }

    public static void dbMenu(ArrayList<Person> list) {
        while (true) {
            switch (ConsoleUI.choice(List.of(
                "Add a person",
                "List everyone",
                "Find by last name",
                "Find by student's index",
                "Find with bigger HIndex",
                "Find worker on position",
                "Save",
                "Exit",
                "Add course to student",
                "List Student's courses",
                "Remove Student's course"))) {
                case 1: // Add a person
                    addPerson(list);
                    break;
                case 2: // List everyone
                    ConsoleUI.print("Every employee and student:");
                    ConsoleUI.printList(list, false);
                    break;
                case 3: // Find by last name
                    ConsoleUI.printList(listPeople(list, List.of(new PersonFilterByLName(ConsoleUI.inputString("Last name", null)))), true);
                    break;
                case 4: // Find by student's index
                    ConsoleUI.printList(listPeople(list, List.of(new PersonFilterByStudentsIndex(ConsoleUI.inputString("Student's index", null)))), true);
                    break;
                case 5: // Find with bigger HIndex
                    ConsoleUI.printList(listPeople(list, List.of(new PersonFilterByHIndexGreater(ConsoleUI.inputNumber("HIndex", null)))), true);
                    break;
                case 6: // Position
                    ConsoleUI.printList(listPeople(list, List.of(new PersonFilterByPosition(ConsoleUI.inputString("Position", null)))), true);
                    break;
                case 7: // Save
                ObjectSaver.serialize(list, ConsoleUI.inputString("File path", List.of(new StringNotEmpty())));
                break;
                case 8: //Exit
                    return;
                case 9: // Add course to student
                {
                    ConsoleUI.print("Add to:");
                    final ArrayList<Person> chosenStudent = listPeople(list, List.of(new PersonFilterByIndex(ConsoleUI.inputString("Student's index", null))));
                    if(chosenStudent.isEmpty()) {
                        ConsoleUI.print("No student with such index");
                        break;
                    }
                    // TODO Exception when two students has same index
                    Student student = (Student)chosenStudent.get(0);
                    final String courseName = ConsoleUI.inputString("Course name", List.of(new StringNotEmpty()));
                    student.addCourse(new Course(courseName));
                    break;
                }
                case 10: // List Student's courses
                {
                    ConsoleUI.print("Student:");
                    final ArrayList<Person> chosenStudent = listPeople(list, List.of(new PersonFilterByIndex(ConsoleUI.inputString("Student's index", null))));
                    if(chosenStudent.isEmpty()) {
                        ConsoleUI.print("No student with such index");
                        break;
                    }
                    // TODO Exception when two students has same index
                    Student student = (Student)chosenStudent.get(0);
                    ConsoleUI.print(String.format("Curses (%d):", student.getCourses().size()));
                    for (Course course : student.getCourses()) {
                        ConsoleUI.print(course);
                    }
                    break;
                }
                case 11: // Remove Student's course
                {
                    ConsoleUI.print("Remove from:");
                    final ArrayList<Person> chosenStudent = listPeople(list, List.of(new PersonFilterByIndex(ConsoleUI.inputString("Student's index", null))));
                    if(chosenStudent.isEmpty()) {
                        ConsoleUI.print("No student with such index");
                        break;
                    }
                    // TODO Exception when two students has same index
                    Student student = (Student)chosenStudent.get(0);
                    final String courseName = ConsoleUI.inputString("Course name", List.of(new StringNotEmpty()));
                    ConsoleUI.print(student.removeCourse(new Course(courseName)) ? "Course deleted" : "Student was't at this course");
                    break;
                }
                default:
                    ConsoleUI.print("Unexpected input");
                    break;
            }
        }
    }

    private static Department getDepartment() {
        switch (ConsoleUI.choice(List.of("W1", "W2", "W3", "W4N", "W5", "W6", "W7", "W8", "W9", "W10", "W11", "W12", "W13", "W14"))) {
            case 1:
                return Department.W1;
            case 2:
                return Department.W2;
            case 3:
                return Department.W3;
            case 4:
                return Department.W4N;
            case 5:
                return Department.W5;
            case 6:
                return Department.W6;
            case 7:
                return Department.W7;
            case 8:
                return Department.W8;
            case 9:
                return Department.W9;
            case 10:
                return Department.W10;
            case 11:
                return Department.W11;
            case 12:
                return Department.W12;
            case 13:
                return Department.W13;
            case 14:
                return Department.W14;
            default:
                // Impossible state
                return Department.W1;
        }
    }

    public static void addPerson(ArrayList<Person> list) {
        final String fname = ConsoleUI.inputString("First name", List.of(new StringNotEmpty()));
        final String lname = ConsoleUI.inputString("Last name", List.of(new StringNotEmpty()));
        final String email = ConsoleUI.inputString("Email", List.of(new StringNotEmpty()));
        switch (ConsoleUI.choice(List.of("Scientist", "Student", "Worker", "Exit"))) {
            case 1: // Scientist
            {
                final Department department = getDepartment();
                final int hIndex = ConsoleUI.inputNumber("HIndex", List.of(new NumberInRange(0, Integer.MAX_VALUE)));
                list.add(new Scientist(fname, lname, email, department, hIndex));
                break;
            }
            case 2: // Student
                final String indexNumber = ConsoleUI.inputString("Index number", List.of(new StringNotEmpty()));
                list.add(new Student(fname, lname, email, indexNumber));
                break;
            case 3: // Worker
                final boolean worksInOffice = ConsoleUI.inputBoolean("Does work in office");
                final boolean worksShift = ConsoleUI.inputBoolean("Does work shifts");
                final Department department = getDepartment();
                final String position = ConsoleUI.inputString("Position", List.of(new StringNotEmpty()));
                list.add(new Worker(fname, lname, email, department, worksInOffice, worksShift, position));
            case 4:
                break;
            default:
                ConsoleUI.print("Unexpected input");
                break;
        }
    }
}
