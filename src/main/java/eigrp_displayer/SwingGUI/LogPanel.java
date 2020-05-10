package eigrp_displayer.SwingGUI;

import eigrp_displayer.EventLog;

import javax.swing.*;
import java.awt.*;

public class LogPanel extends JPanel {
    private JLabel textLabel;

    public LogPanel(){
        textLabel = new JLabel();
        setLayout(new BorderLayout());
        add(new JScrollPane(textLabel), BorderLayout.CENTER);
    }

    public void refresh(){
        textLabel.setText(EventLog.getEventLog());
    }
}
