package eigrp_displayer.SwingGUI;

import eigrp_displayer.Clock;
import eigrp_displayer.DeviceController;
import eigrp_displayer.MessageScheduler;
import eigrp_displayer.RouterController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class DisplayFrame extends JFrame implements ActionListener {
    private JLabel titleLabel;
    private JLabel timeLabel;

    private DisplayPanel displayPanel;
    private LogPanel logPanel;
    private List<TablesPanel> tablePanels;

    private JButton goButton;
    private JButton editNetworkButton;

    public DisplayFrame(){
        super("Displayer Main Window");
        this.titleLabel = new JLabel("Main Displayer Window");
        this.timeLabel = new JLabel(String.valueOf(Clock.getTime()));
        this.displayPanel = new DisplayPanel();
        this.logPanel = new LogPanel();
        this.tablePanels = new ArrayList<>();
        List<DeviceController> controllers = MessageScheduler.getInstance().getNetwork().getDeviceControllers();
        for(int i = 0; i < controllers.size(); i++){
            if(controllers.get(i) instanceof RouterController){
                this.tablePanels.add(new TablesPanel((RouterController)controllers.get(i)));
            }
        }
        this.logPanel = new LogPanel();
        this.editNetworkButton = new JButton("Edit Network");
        this.editNetworkButton.addActionListener(this);
        this.goButton = new JButton("Go with the simulation");
        this.goButton.addActionListener(this);

        setMinimumSize(new Dimension(600, 400));
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        gbc.gridy++;
        add(displayPanel, gbc);

        gbc.gridy++;
        add(logPanel, gbc);

        gbc.gridy++;
        for(TablesPanel panel : this.tablePanels){
            add(panel, gbc);
            gbc.gridy++;
        }

        add(goButton, gbc);
        gbc.gridy++;
        add(editNetworkButton, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JButton clickedButton = (JButton)actionEvent.getSource();

        if(clickedButton == this.goButton){
            //TODO: make some sort of go on method
        }
        else if(clickedButton == this.editNetworkButton){
            new NetworkForm();
            this.dispose();
        }
    }
}