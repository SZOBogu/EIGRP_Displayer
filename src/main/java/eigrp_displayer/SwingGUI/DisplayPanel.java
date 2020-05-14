package eigrp_displayer.SwingGUI;

import javax.swing.*;
import java.awt.*;

public class DisplayPanel extends JPanel implements Refreshable {
    private NetworkGraphicalDisplayPanel networkGraphicalDisplayPanel;
    private LogPanel logPanel;

    public DisplayPanel(){
        this.networkGraphicalDisplayPanel = new NetworkGraphicalDisplayPanel();
        this.logPanel = new LogPanel();
        setLayout(new GridBagLayout());
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy = 0;
        gbc.gridx = 0;
        add(this.networkGraphicalDisplayPanel, gbc);
        gbc.gridy++;
        add(this.logPanel, gbc);
    }

    public void refresh(){
        this.logPanel.refresh();
    }
}
