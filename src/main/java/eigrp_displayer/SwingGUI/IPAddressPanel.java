package eigrp_displayer.SwingGUI;

import eigrp_displayer.IPAddress;

import javax.swing.*;
import java.awt.*;

public class IPAddressPanel extends JPanel {
    private JLabel addressLabel;

    private JSpinner firstOctetAddressSpinner;
    private JSpinner secondOctetAddressSpinner;
    private JSpinner thirdOctetAddressSpinner;
    private JSpinner fourthOctetAddressSpinner;

    public IPAddressPanel(){
        this(new IPAddress(192, 169, 0 ,1));
    }

    public IPAddressPanel(IPAddress ipAddress){
        this.addressLabel = new JLabel("IP Address: ");

        SpinnerNumberModel firstOctetAddressSpinnerModel =
                new SpinnerNumberModel(ipAddress.getFirstOctet(), 0, 255, 1);
        SpinnerNumberModel secondOctetAddressSpinnerModel =
                new SpinnerNumberModel(ipAddress.getSecondOctet(), 0, 255, 1);
        SpinnerNumberModel thirdOctetAddressSpinnerModel =
                new SpinnerNumberModel(ipAddress.getThirdOctet(), 0, 255, 1);
        SpinnerNumberModel fourthOctetAddressSpinnerModel =
                new SpinnerNumberModel(ipAddress.getFourthOctet(), 0, 255, 1);

        this.firstOctetAddressSpinner = new JSpinner(firstOctetAddressSpinnerModel);
        this.secondOctetAddressSpinner = new JSpinner(secondOctetAddressSpinnerModel);
        this.thirdOctetAddressSpinner = new JSpinner(thirdOctetAddressSpinnerModel);
        this.fourthOctetAddressSpinner = new JSpinner(fourthOctetAddressSpinnerModel);

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
        add(this.addressLabel, gbc);
        this.addressLabel.setHorizontalAlignment(JLabel.CENTER);

        gbc.gridy++;
        add(this.firstOctetAddressSpinner, gbc);
        gbc.gridx++;
        add(this.secondOctetAddressSpinner, gbc);
        gbc.gridx++;
        add(this.thirdOctetAddressSpinner, gbc);
        gbc.gridx++;
        add(this.fourthOctetAddressSpinner, gbc);
        gbc.gridx++;
    }

    public IPAddress getIPAddress(){
        return new IPAddress((int)this.firstOctetAddressSpinner.getValue(),
                (int)this.secondOctetAddressSpinner.getValue(),
                (int)this.thirdOctetAddressSpinner.getValue(),
                (int)this.fourthOctetAddressSpinner.getValue());
    }
}
