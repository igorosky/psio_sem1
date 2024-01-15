package Backend.WageStrategies;

import Backend.Wage;
import College.Department;
import College.People.Employee;

public class WageDepartmentBased implements Wage {
    private float base;
    private float multiplayer;

    public WageDepartmentBased() {
        base = 4000f;
        multiplayer = 100f;
    }

    public WageDepartmentBased(final float base) {
        this();
        this.base = base;
    }

    public WageDepartmentBased(final float base, final float multiplayer) {
        this.base = base;
        this.multiplayer = multiplayer;
    }
    
    @Override
    public float wage(final Employee employee) {
        return base + (float)Department.getIndex(employee.getDepartment()) * multiplayer;
    }

    public float getBase() {
        return base;
    }

    public float getMultiplayer() {
        return multiplayer;
    }

    public void setBase(final float base) {
        this.base = base;
    }

    public void setMultiplayer(final float multiplayer) {
        this.multiplayer = multiplayer;
    }
    
}
