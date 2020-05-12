package eigrp_displayer.SwingGUI;

import eigrp_displayer.Mask;
import eigrp_displayer.Network;
import eigrp_displayer.PremadeNetwork;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NetworkFormPanel extends JPanel implements ActionListener {
    private Network network;

    private JLabel titleLabel;
    private JLabel maskLabel;

    private IPAddressPanel networkIPPanel;
    private IPAddressPanel broadcastIPPanel;

    private JSpinner maskSpinner;

    private DeviceChoicePanel deviceChoicePanel;

    private JButton editButton;
    private JButton backButton;

    public NetworkFormPanel(){
        this.network = PremadeNetwork.getNetwork();

        this.titleLabel = new JLabel("Network Form");
        this.networkIPPanel = new IPAddressPanel("Network");
        this.broadcastIPPanel = new IPAddressPanel("Broadcast");
        this.maskLabel = new JLabel("Mask");

        SpinnerNumberModel maskSpinnerModel = new SpinnerNumberModel(24, 0, 31, 1);
        this.maskSpinner = new JSpinner(maskSpinnerModel);

        this.deviceChoicePanel = new DeviceChoicePanel();

        this.backButton = new JButton("Go Back");
        this.editButton = new JButton("Edit");
        this.backButton.addActionListener(this);
        this.editButton.addActionListener(this);

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

        add(deviceChoicePanel, gbc);
        gbc.gridy++;

        gbc.gridy++;
        add(this.backButton, gbc);
        gbc.gridy++;
        add(this.editButton, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JButton clickedButton = (JButton)actionEvent.getSource();

        if(clickedButton == backButton){
            new DisplayFrame();
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.dispose();
        }
        else if(clickedButton == editButton){
            if(this.network.getNetworkAddress() != this.networkIPPanel.getIPAddress() ||
                    this.network.getBroadcastAddress() != this.broadcastIPPanel.getIPAddress() ||
                    this.network.getMask().getMask() != (int)this.maskSpinner.getValue()) {
                this.network.setNetworkAddress(this.networkIPPanel.getIPAddress());
                this.network.setBroadcastAddress(this.broadcastIPPanel.getIPAddress());
                this.network.setMask(new Mask((int) this.maskSpinner.getValue()));

                JOptionPane.showMessageDialog(new JFrame(), "Network edited", "Dialog",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            JOptionPane.showMessageDialog(new JFrame(), "Nothing changed", "Dialog",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
