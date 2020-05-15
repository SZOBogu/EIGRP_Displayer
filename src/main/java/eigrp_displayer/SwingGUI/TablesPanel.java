package eigrp_displayer.SwingGUI;

import eigrp_displayer.RouterController;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class TablesPanel extends JPanel implements Refreshable{
    RouterController controller;
    private JLabel routerLabel;
    private JTextArea routingTableTextArea;
    private JTextArea topologyTableTextArea;
    private JTextArea neighbourTableTextArea;

    public TablesPanel(RouterController controller){
        this.controller = controller;
        this.routerLabel = new JLabel(controller.getDevice().toString());

        this.routingTableTextArea = new JTextArea(controller.printRoutingTable(),5, 60);
        this.topologyTableTextArea = new JTextArea(controller.printTopologyTable(),5, 60);
        this.neighbourTableTextArea = new JTextArea(
                controller.getDevice().getNeighbourTable().toString(),5, 60);

        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        this.routerLabel.setBorder(border);
        this.routingTableTextArea.setBorder(border);
        this.topologyTableTextArea.setBorder(border);
        this.neighbourTableTextArea.setBorder(border);
        this.routingTableTextArea.setFont(new Font("Serif", Font.PLAIN, 8));
        this.topologyTableTextArea.setFont(new Font("Serif", Font.PLAIN, 8));
        this.neighbourTableTextArea.setFont(new Font("Serif", Font.PLAIN, 8));

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
        this.routerLabel.setHorizontalAlignment(JLabel.CENTER);

        gbc.gridy++;
        add(new JScrollPane(
                this.routingTableTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), gbc);
        gbc.gridy++;
        add(new JScrollPane(
                this.topologyTableTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), gbc);
        gbc.gridy++;
        add(new JScrollPane(
                this.neighbourTableTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), gbc);
    }

    public void refresh(){
        this.routingTableTextArea.setText(this.controller.printRoutingTable());
        this.topologyTableTextArea.setText(this.controller.printTopologyTable());
        this.neighbourTableTextArea.setText(this.controller.getDevice().getNeighbourTable().toString());

        this.layoutComponents();
        this.revalidate();
        this.repaint();
    }
}