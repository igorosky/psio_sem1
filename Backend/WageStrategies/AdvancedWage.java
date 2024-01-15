package Backend.WageStrategies;

import Backend.Wage;
import College.People.Employee;
import College.People.Scientist;
import College.People.Worker;

public class AdvancedWage implements Wage {

    private float base;
    private float hIndexMultiplayer;
    private float shiftsBonus;
    private float officeBonus;
    
    public AdvancedWage() {
        base = 4000f;
        hIndexMultiplayer = 100f;
        shiftsBonus = 300f;
        officeBonus = 200f;
    }

    public AdvancedWage(final float base) {
        this();
        this.base = base;
    }

    public AdvancedWage(final float base, final float hIndexMultiplayer) {
        this();
        this.base = base;
        this.hIndexMultiplayer = hIndexMultiplayer;
    }
    
    public AdvancedWage(final float base, final float hIndexMultiplayer, final float shiftsBonus) {
        this();
        this.base = base;
        this.hIndexMultiplayer = hIndexMultiplayer;
        this.shiftsBonus = shiftsBonus;
    }
    
    public AdvancedWage(final float base, final float hIndexMultiplayer, final float shiftsBonus, final float officeBonus) {
        this.base = base;
        this.hIndexMultiplayer = hIndexMultiplayer;
        this.shiftsBonus = shiftsBonus;
        this.officeBonus = officeBonus;
    }
    
    @Override
    public float wage(final Employee employee) {
        if(employee instanceof Worker) {
            final Worker worker = (Worker)employee;
            return base + (worker.isWorksShifts() ? shiftsBonus : 0) + (worker.isWorksInOffice() ? officeBonus : 0); 
        }
        else if(employee instanceof Scientist) {
            return base + (float)((Scientist)employee).gethIndex() * hIndexMultiplayer;
        }
        return -1;
    }

    public float getBase() {
        return base;
    }

    public float getOfficeBonus() {
        return officeBonus;
    }

    public float getShiftsBonus() {
        return shiftsBonus;
    }

    public float gethIndexMultiplayer() {
        return hIndexMultiplayer;
    }

    public void setBase(final float base) {
        this.base = base;
    }

    public void setOfficeBonus(final float officeBonus) {
        this.officeBonus = officeBonus;
    }

    public void setShiftsBonus(final float shiftsBonus) {
        this.shiftsBonus = shiftsBonus;
    }

    public void sethIndexMultiplayer(final float hIndexMultiplayer) {
        this.hIndexMultiplayer = hIndexMultiplayer;
    }
    
}
