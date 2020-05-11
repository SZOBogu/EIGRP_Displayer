package eigrp_displayer.SwingGUI;

import eigrp_displayer.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class NetworkFormPanel extends JPanel implements ActionListener {
    private Network network;

    private JLabel titleLabel;
    private JLabel maskLabel;

    private IPAddressPanel networkIPPanel;
    private IPAddressPanel broadcastIPPanel;

    private JSpinner maskSpinner;

    private JButton editButton;
    private JButton backButton;

    private List<JButton> editDeviceButtons;
    private List<JButton> editConnectionButtons;

    public NetworkFormPanel(){
        this.network = PremadeNetwork.getNetwork();

        this.titleLabel = new JLabel("Network Form");
        this.networkIPPanel = new IPAddressPanel("Network");
        this.broadcastIPPanel = new IPAddressPanel("Broadcast");
        this.maskLabel = new JLabel("Mask");

        SpinnerNumberModel maskSpinnerModel = new SpinnerNumberModel(24, 0, 31, 1);
        this.maskSpinner = new JSpinner(maskSpinnerModel);

        this.backButton = new JButton("Go Back");
        this.editButton = new JButton("Edit");
        this.backButton.addActionListener(this);
        this.editButton.addActionListener(this);

        this.editDeviceButtons = new ArrayList<>();
        for(DeviceController controller : network.getDeviceControllers()){
            JButton button = new JButton("Edit: " + controller.getDevice().toString());
            this.editDeviceButtons.add(button);
            button.addActionListener(this);
        }
        this.editConnectionButtons = new ArrayList<>();
        for(Connection connection : network.getConnections()){
            JButton button = new JButton("Edit: " + connection.toString());
            this.editConnectionButtons.add(button);
            button.addActionListener(this);
        }

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
        add(titleLabel, gbc);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        gbc.gridy++;
        add(this.networkIPPanel, gbc);
        gbc.gridy++;
        add(this.broadcastIPPanel, gbc);
        gbc.gridy++;
        add(this.maskLabel, gbc);
        gbc.gridx++;
        add(this.maskSpinner, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        for(JButton button : this.editDeviceButtons){
            add(button, gbc);
            gbc.gridy++;
        }
        for(JButton button : this.editConnectionButtons){
            add(button, gbc);
            gbc.gridy++;
        }
        gbc.gridy++;
        add(this.backButton, gbc);
        gbc.gridy++;
        add(this.editButton, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JButton clickedButton = (JButton)actionEvent.getSource();
        List<DeviceController> controllers = MessageScheduler.getInstance().getControllers();
        List<Connection> connections = MessageScheduler.getInstance().getNetwork().getConnections();
        for(int i = 0; i < controllers.size(); i++){
            if(clickedButton == this.editDeviceButtons.get(i)){
                new DeviceFormFrame(controllers.get(i));
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                topFrame.dispose();
            }
        }
        for(int i = 0; i < connections.size(); i++){
            if(clickedButton == this.editConnectionButtons.get(i)){
                new ConnectionForm(connections.get(i));
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                topFrame.dispose();
            }
        }
        if(clickedButton == backButton){
            new DisplayFrame();
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.dispose();
        }
        else if(clickedButton == editButton){
            this.network.setNetworkAddress(this.networkIPPanel.getIPAddress());
            this.network.setBroadcastAddress(this.broadcastIPPanel.getIPAddress());
            this.network.setMask(new Mask((int)this.maskSpinner.getValue()));

            JOptionPane.showMessageDialog(new JFrame(), "Network edited", "Dialog",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
