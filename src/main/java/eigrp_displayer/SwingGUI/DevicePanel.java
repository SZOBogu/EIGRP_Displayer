package eigrp_displayer.SwingGUI;

import eigrp_displayer.DeviceController;
import eigrp_displayer.IPAddress;

import javax.swing.*;
import java.awt.*;

public class DevicePanel extends JPanel {
    protected DeviceController controller;

    protected JLabel titleLabel;
    protected JLabel nameLabel;
    protected JLabel interfacesLabel;
    protected JLabel messageOffsetLabel;

    protected JTextField nameField;
    protected IPAddressPanel ipPanel;
    protected JSpinner interfacesSpinner;
    protected JSpinner offsetSpinner;

    public DevicePanel(DeviceController controller){
        this.controller = controller;

        this.titleLabel = new JLabel(controller.getDevice().toString());
        this.nameLabel = new JLabel("Name: ");
        this.interfacesLabel = new JLabel("Number of available interfaces: ");
        this.messageOffsetLabel = new JLabel("Message sending offset: ");

        this.nameField = new JTextField(controller.getDevice().getName(), 20);
        this.ipPanel = new IPAddressPanel(controller.getDevice().getIp_address());

        SpinnerNumberModel interfacesModel =
                new SpinnerNumberModel(controller.getDevice().getDeviceInterfaces().length,
                        1, 100, 1);
        SpinnerNumberModel offsetModel =
                new SpinnerNumberModel(controller.getDevice().getMessageSendingTimeOffset(),
                        1, 1000, 1);

        this.interfacesSpinner = new JSpinner(interfacesModel);
        this.offsetSpinner = new JSpinner(offsetModel);

        this.layoutComponents();
    }

    protected void layoutComponents(){
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

        add(nameLabel, gbc);
        gbc.gridx++;
        add(nameField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        add(ipPanel, gbc);
        gbc.gridy++;

        add(interfacesLabel, gbc);
        gbc.gridx++;
        add(interfacesSpinner, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        add(messageOffsetLabel, gbc);
        gbc.gridx++;
        add(offsetSpinner, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
    }

    public String getName(){
        return this.nameField.getText();
    }

    public IPAddress getIP(){
        return this.ipPanel.getIPAddress();
    }

    public int getInterfacesCount(){
        return (int)this.interfacesSpinner.getValue();
    }

    public int getOffset(){
        return (int)this.offsetSpinner.getValue();
    }
}
