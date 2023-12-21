package College.People;

import College.Department;

public class Employee extends Person {
    private Department department;
    
    public Employee(final String fname, final String lname, final String mail, final Department department) {
        super(fname, lname, mail);
        this.department = department;
    }
    
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(final Department faculty) {
        this.department = faculty;
    }
}
