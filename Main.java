import java.util.ArrayList;

import Backend.Backend;
import Backend.StipendStrategies.LogCourseCountBased;
import Backend.WageStrategies.AdvancedWage;
import GUI.MainMenu;

public class Main {
    public static final void main(final String[] args) {
        new MainMenu(new Backend(new ArrayList<>(), new AdvancedWage(), new LogCourseCountBased()));
    }
}
