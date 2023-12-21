package College.People;

import College.Department;

public class Worker extends Employee {
    private boolean worksInOffice;
    private boolean worksShifts;
    private String position;
    
    public Worker(final String fname, final String lname, final String mail, final Department department, final boolean worksInOffice, final boolean worksShifts, final String position) {
        super(fname, lname, mail, department);
        this.worksInOffice = worksInOffice;
        this.worksShifts = worksShifts;
        this.position = position;
    }

    public boolean isWorksInOffice() {
        return worksInOffice;
    }

    public boolean isWorksShifts() {
        return worksShifts;
    }

    public void setWorksInOffice(final boolean worksInOffice) {
        this.worksInOffice = worksInOffice;
    }

    public void setWorksShifts(final boolean worksShifts) {
        this.worksShifts = worksShifts;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(final String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return String.format("Worker %s Position: %s Works shifts: %s Works in office: %s", super.toString(), position, worksShifts ? "yes" : "no", worksInOffice ? "yes" : "no");
    }
}
