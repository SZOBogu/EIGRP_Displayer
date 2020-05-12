package eigrp_displayer.SwingGUI;

import eigrp_displayer.MessageScheduler;
import eigrp_displayer.Network;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeviceChoicePanel extends JPanel implements ActionListener {
    private Network network;

    private JButton endDevice0Button;

    private JButton connection3Button;

    private JButton connection1Button;
    private JButton router1Button;
    private JButton connection4Button;

    private JButton externalNetworkButton;
    private JButton connection0Button;
    private JButton router0Button;
    private JButton connection2Button;
    private JButton router2Button;
    private JButton connection5Button;
    private JButton endDevice1Button;

    public DeviceChoicePanel(){
        super();
        this.network = MessageScheduler.getInstance().getNetwork();
        this.endDevice0Button = new JButton(network.getDeviceControllers().get(1).getDevice().toString());

        this.connection3Button = new JButton(network.getConnections().get(3).toString());

        this.connection1Button = new JButton(network.getConnections().get(1).toString());
        this.router1Button = new JButton(network.getDeviceControllers().get(4).getDevice().toString());
        this.connection4Button = new JButton(network.getConnections().get(4).toString());

        this.externalNetworkButton = new JButton(network.getDeviceControllers().get(0).getDevice().toString());
        this.connection0Button = new JButton(network.getConnections().get(0).toString());
        this.router0Button = new JButton(network.getDeviceControllers().get(3).getDevice().toString());
        this.connection2Button = new JButton(network.getConnections().get(2).toString());
        this.router2Button = new JButton(network.getDeviceControllers().get(5).getDevice().toString());
        this.connection5Button = new JButton(network.getConnections().get(5).toString());
        this.endDevice1Button = new JButton(network.getDeviceControllers().get(2).getDevice().toString());

        this.endDevice0Button.addActionListener(this);
        this.connection3Button.addActionListener(this);
        this.connection1Button.addActionListener(this);
        this.router1Button.addActionListener(this);
        this.connection4Button.addActionListener(this);
        this.externalNetworkButton.addActionListener(this);
        this.connection0Button.addActionListener(this);
        this.router0Button.addActionListener(this);
        this.connection2Button.addActionListener(this);
        this.router2Button.addActionListener(this);
        this.connection5Button.addActionListener(this);
        this.endDevice1Button.addActionListener(this);

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

        add(this.endDevice0Button, gbc);
        gbc.gridy++;
        add(this.connection3Button, gbc);
        gbc.gridy++;
        add(this.connection1Button, gbc);
        gbc.gridy++;
        add(this.router1Button, gbc);
        gbc.gridy++;
        add(this.connection4Button, gbc);
        gbc.gridy++;
        add(this.externalNetworkButton, gbc);
        gbc.gridy++;
        add(this.connection0Button, gbc);
        gbc.gridy++;
        add(this.router0Button, gbc);
        gbc.gridy++;
        add(this.connection2Button, gbc);
        gbc.gridy++;
        add(this.router2Button, gbc);
        gbc.gridy++;
        add(this.connection5Button, gbc);
        gbc.gridy++;
        add(this.endDevice1Button, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JButton clickedButton = (JButton)actionEvent.getSource();

        if(clickedButton == this.endDevice0Button){
            new DeviceFormFrame(network.getDeviceControllers().get(1));
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.dispose();
        }
        else if(clickedButton == this.connection3Button){
            new ConnectionFormFrame(network.getConnections().get(3));
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.dispose();
        }
        else if(clickedButton == this.connection1Button){
            new ConnectionFormFrame(network.getConnections().get(1));
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.dispose();
        }
        else if(clickedButton == this.router1Button){
            new DeviceFormFrame(network.getDeviceControllers().get(4));
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.dispose();
        }
        else if(clickedButton == this.externalNetworkButton){
            new DeviceFormFrame(network.getDeviceControllers().get(0));
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.dispose();
        }
        else if(clickedButton == this.connection0Button){
            new ConnectionFormFrame(network.getConnections().get(0));
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.dispose();
        }
        else if(clickedButton == this.router0Button){
            new DeviceFormFrame(network.getDeviceControllers().get(3));
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.dispose();
        }
        else if(clickedButton == this.connection2Button){
            new ConnectionFormFrame(network.getConnections().get(2));
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.dispose();
        }
        else if(clickedButton == this.router2Button){
            new DeviceFormFrame(network.getDeviceControllers().get(5));
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.dispose();
        }
        else if(clickedButton == this.connection5Button){
            new ConnectionFormFrame(network.getConnections().get(5));
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.dispose();
        }
        else if(clickedButton == this.endDevice1Button){
            new DeviceFormFrame(network.getDeviceControllers().get(2));
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.dispose();
        }
    }
}
