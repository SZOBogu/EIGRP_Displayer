package eigrp_displayer.SwingGUI;

import eigrp_displayer.DeviceController;
import eigrp_displayer.Network;
import eigrp_displayer.PremadeNetwork;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class NetworkEditMenu extends JFrame implements ActionListener {
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

    public NetworkEditMenu(){
        Network network = PremadeNetwork.getNetwork();
        this.endDevice0Button = new JButton(network.getDeviceControllers().get(1).toString());

        this.connection3Button = new JButton(network.getConnections().get(3).toString());


        this.connection1Button = new JButton(network.getConnections().get(1).toString());
        this.router1Button = new JButton(network.getDeviceControllers().get(4).toString());
        this.connection4Button = new JButton(network.getConnections().get(4).toString());

        this.externalNetworkButton = new JButton(network.getDeviceControllers().get(0).toString());
        this.connection0Button = new JButton(network.getConnections().get(0).toString());
        this.router0Button = new JButton(network.getDeviceControllers().get(3).toString());
        this.connection2Button = new JButton(network.getConnections().get(2).toString());
        this.router2Button = new JButton(network.getDeviceControllers().get(5).toString());
        this.connection5Button = new JButton(network.getConnections().get(5).toString());
        this.endDevice1Button = new JButton(network.getDeviceControllers().get(2).toString());


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
        List<DeviceController> controllers = PremadeNetwork.getNetwork().getDeviceControllers();

        if(clickedButton == this.endDevice0Button){

        }
        else if(clickedButton == this.connection3Button){

        }
        else if(clickedButton == this.connection1Button){

        }
        else if(clickedButton == this.router1Button){

        }
        else if(clickedButton == this.externalNetworkButton){

        }
        else if(clickedButton == this.connection0Button){

        }
        else if(clickedButton == this.router0Button){

        }
        else if(clickedButton == this.connection2Button){

        }
        else if(clickedButton == this.router2Button){

        }
        else if(clickedButton == this.connection5Button){

        }
        else if(clickedButton == this.endDevice1Button){

        }
    }
}
