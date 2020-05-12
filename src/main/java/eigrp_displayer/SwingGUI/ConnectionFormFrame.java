package eigrp_displayer.SwingGUI;

import eigrp_displayer.Connection;
import eigrp_displayer.MessageScheduler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectionFormFrame extends JFrame implements ActionListener {
    private Connection connection;

    private JLabel connectionLabel;

    private JLabel bandwidthLabel;
    private JLabel delayLabel;
    private JLabel loadLabel;
    private JLabel reliabilityLabel;
    private JSpinner bandwidthSpinner;
    private JSpinner delaySpinner;
    private JSpinner loadSpinner;
    private JSpinner reliabilitySpinner;

    private JLabel device1Label;
    private JLabel device2Label;
    private JComboBox device1ComboBox;
    private JComboBox device2ComboBox;

    private JButton editConnectionButton;
    private JButton goBackButton;

    public ConnectionFormFrame(Connection connection){
        super();
        this.connection = connection;

        this.connectionLabel = new JLabel(connection.toString());

        this.bandwidthLabel = new JLabel("Bandwidth: ");
        this.delayLabel = new JLabel("Delay: ");
        this.loadLabel = new JLabel("Load: ");
        this.reliabilityLabel = new JLabel("Reliability: ");

        SpinnerNumberModel bandwidthSpinnerModel = new SpinnerNumberModel(connection.getBandwidth(), 0, Integer.MAX_VALUE, 1);
        SpinnerNumberModel delaySpinnerModel = new SpinnerNumberModel(connection.getDelay(), 0, Integer.MAX_VALUE, 1);
        SpinnerNumberModel loadSpinnerModel = new SpinnerNumberModel(connection.getLoad(), 0, 255, 1);
        SpinnerNumberModel reliabilitySpinnerModel = new SpinnerNumberModel(connection.getReliability(), 0, 255, 1);

        this.bandwidthSpinner = new JSpinner(bandwidthSpinnerModel);
        this.delaySpinner = new JSpinner(delaySpinnerModel);
        this.loadSpinner = new JSpinner(loadSpinnerModel);
        this.reliabilitySpinner = new JSpinner(reliabilitySpinnerModel);

        this.device1Label = new JLabel(connection.getDevice1().toString());
        this.device2Label = new JLabel(connection.getDevice2().toString());

        this.device1ComboBox = new JComboBox(MessageScheduler.getInstance().getControllers().toArray());
        this.device2ComboBox = new JComboBox(MessageScheduler.getInstance().getControllers().toArray());

        this.goBackButton = new JButton("Go Back");
        this.editConnectionButton = new JButton("Edit Connection");
        this.goBackButton.addActionListener(this);
        this.editConnectionButton.addActionListener(this);

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

        add(connectionLabel, gbc);
        connectionLabel.setHorizontalAlignment(JLabel.CENTER);

        gbc.gridy++;
        add(bandwidthLabel, gbc);
        gbc.gridx++;
        add(bandwidthSpinner, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        gbc.gridy++;
        add(delayLabel, gbc);
        gbc.gridx++;
        add(delaySpinner, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        gbc.gridy++;
        add(loadLabel, gbc);
        gbc.gridx++;
        add(loadSpinner, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        gbc.gridy++;
        add(reliabilityLabel, gbc);
        gbc.gridx++;
        add(reliabilitySpinner, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        gbc.gridy++;
        add(device1Label, gbc);
        gbc.gridx++;
        add(device1ComboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        gbc.gridy++;
        add(device2Label, gbc);
        gbc.gridx++;
        add(device2ComboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        gbc.gridy++;
        add(goBackButton, gbc);
        gbc.gridx++;
        add(editConnectionButton, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JButton clickedButton = (JButton) actionEvent.getSource();

        if (clickedButton == this.editConnectionButton) {
            this.connection.setBandwidth((int)this.bandwidthSpinner.getValue());
            this.connection.setDelay((int)this.delaySpinner.getValue());
            this.connection.setLoad((int)this.loadSpinner.getValue());
            this.connection.setReliability((int)this.reliabilitySpinner.getValue());
//            this.connection.setDevice1(this.device1ComboBox.getSelectedItem());
//            this.connection.setDevice2(this.device2ComboBox.getSelectedItem());
        }
        else if (clickedButton == this.goBackButton) {
            new NetworkForm();
            this.dispose();
        }
    }
}
