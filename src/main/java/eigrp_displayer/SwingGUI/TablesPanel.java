package eigrp_displayer.SwingGUI;

import eigrp_displayer.RouterController;

import javax.swing.*;
import java.awt.*;

public class TablesPanel extends JPanel {
    RouterController controller;
    private JLabel routerLabel;
    private JLabel routingTableLabel;
    private JLabel topologyTableLabel;
    private JLabel neighbourTableLabel;

    public TablesPanel(RouterController controller){
        this.controller = controller;
        this.routerLabel = new JLabel(controller.getDevice().toString());
        this.routingTableLabel = new JLabel(controller.printRoutingTable());
        this.topologyTableLabel = new JLabel(controller.printTopologyTable());
        this.neighbourTableLabel = new JLabel(controller.getDevice().getNeighbourTable().toString());

        this.layoutComponents();
    }

    private void layoutComponents(){
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy = 0;
        gbc.gridx = 0;
        add(this.routerLabel, gbc);
        this.routingTableLabel.setHorizontalAlignment(JLabel.CENTER);

        gbc.gridy++;
        add(this.routingTableLabel, gbc);
        gbc.gridy++;
        add(this.topologyTableLabel, gbc);
        gbc.gridy++;
        add(this.neighbourTableLabel, gbc);
    }
}
