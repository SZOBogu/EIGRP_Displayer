package eigrp_displayer.SwingGUI;

import eigrp_displayer.EventLog;

import javax.swing.*;
import java.awt.*;

public class LogPanel extends JPanel implements Refreshable{
    private JTextArea textArea;

    public LogPanel(){
        //TODO: wymiary
        this.textArea = new JTextArea(EventLog.getEventLog(),200, 50);
        textArea.setLineWrap(true);
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(400, 100));
        add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

    public void refresh(){
        this.textArea.setText(EventLog.getEventLog());
    }
}
