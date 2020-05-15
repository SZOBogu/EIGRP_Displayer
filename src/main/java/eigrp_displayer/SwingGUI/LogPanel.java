package eigrp_displayer.SwingGUI;

import eigrp_displayer.EventLog;

import javax.swing.*;
import java.awt.*;

public class LogPanel extends JPanel implements Refreshable{
    private JTextArea textArea;

    public LogPanel(){
        this.textArea = new JTextArea(EventLog.getEventLog(),30, 50);
        textArea.setLineWrap(true);
        setLayout(new BorderLayout());
        add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

    public void refresh(){
        this.textArea.setText(EventLog.getEventLog());
    }
}
