package eigrp_displayer.SwingGUI;

import eigrp_displayer.MessageScheduler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppPanel extends JPanel implements ActionListener {
    private JLabel titleLabel;
    private TimeLabel timeLabel;
  //  private PacketInfoPanel packetInfoPanel;
    private DisplayPanel displayPanel;
    private TablePanelsPanel tablePanelsPanel;

    private JButton goButton;
    private JButton editNetworkButton;

    public AppPanel(){
        super();
        this.titleLabel = new JLabel("Main Displayer Window");
        this.timeLabel = new TimeLabel();
    //    this.packetInfoPanel = new PacketInfoPanel((ExternalNetworkController) MessageScheduler.getInstance().getControllers().get(0));
        this.displayPanel = new DisplayPanel();
        this.tablePanelsPanel = new TablePanelsPanel();
        this.editNetworkButton = new JButton("Edit Network");
        this.editNetworkButton.addActionListener(this);
        this.goButton = new JButton("Go with the simulation");
        this.goButton.addActionListener(this);

        setMinimumSize(new Dimension(1080, 640));
        setSize(1080, 800);
        setVisible(true);

        this.layoutComponents();
    }

    private void layoutComponents() {
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy = 0;
        gbc.gridx = 0;
        add(titleLabel, gbc);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        gbc.gridy++;
        add(timeLabel, gbc);
        timeLabel.setHorizontalAlignment(JLabel.CENTER);

//        gbc.gridy++;
//        add(packetInfoPanel, gbc);

        gbc.gridy++;
        add(displayPanel, gbc);

        gbc.gridx++;
        add(tablePanelsPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        add(goButton, gbc);
        gbc.gridy++;
        add(editNetworkButton, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JButton clickedButton = (JButton)actionEvent.getSource();

        if(clickedButton == this.goButton){
            MessageScheduler.getInstance().updateTime();
            this.displayPanel.refresh();
            this.tablePanelsPanel.refresh();
 //           this.packetInfoPanel.refresh();
        }
        else if(clickedButton == this.editNetworkButton){
            new NetworkForm();
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.dispose();
        }
    }
}