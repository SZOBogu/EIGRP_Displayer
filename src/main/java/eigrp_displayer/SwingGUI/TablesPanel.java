package eigrp_displayer.SwingGUI;

import eigrp_displayer.RouterController;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class TablesPanel extends JPanel implements Refreshable{
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

        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        this.routerLabel.setBorder(border);
        this.routingTableLabel.setBorder(border);
        this.topologyTableLabel.setBorder(border);
        this.neighbourTableLabel.setBorder(border);
        this.routingTableLabel.setFont(new Font("Serif", Font.PLAIN, 8));
        this.topologyTableLabel.setFont(new Font("Serif", Font.PLAIN, 8));
        this.neighbourTableLabel.setFont(new Font("Serif", Font.PLAIN, 8));

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

    public void refresh(){
        this.routingTableLabel.setText(this.controller.printRoutingTable());
        this.topologyTableLabel.setText(this.controller.printTopologyTable());
        this.neighbourTableLabel.setText(this.controller.getDevice().getNeighbourTable().toString());

        this.layoutComponents();
        this.revalidate();
        this.repaint();
    }
}