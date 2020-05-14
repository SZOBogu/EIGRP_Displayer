package eigrp_displayer.SwingGUI;

import eigrp_displayer.Clock;
import eigrp_displayer.ClockDependent;

import javax.swing.*;

public class TimeLabel extends JLabel implements ClockDependent {

    public TimeLabel(){
        super("Current time: " + Clock.getTime());
        Clock.addClockDependant(this);
    }


    @Override
    public void updateTime() {
        this.setText("Current time: " + Clock.getTime());
    }
}
