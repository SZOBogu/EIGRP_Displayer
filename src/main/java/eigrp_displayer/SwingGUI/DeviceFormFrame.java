package eigrp_displayer.SwingGUI;

import eigrp_displayer.DeviceController;
import eigrp_displayer.ExternalNetworkController;
import eigrp_displayer.RouterController;

import javax.swing.*;
import java.awt.*;

public class DeviceFormFrame extends JFrame{
    private DeviceController controller;
    private DeviceForm devicePanel;
    private RouterForm routerPanel;
    private JLabel maskLabel;
    private JSpinner maskSpinner;       //network only gets mask, which is covered by spinner

    public DeviceFormFrame(DeviceController controller){
        this.controller = controller;
        this.devicePanel = new DeviceForm(controller);
        if(controller instanceof RouterController){
            this.routerPanel = new RouterForm((RouterController) controller);
        }
        else if(controller instanceof ExternalNetworkController){
            SpinnerNumberModel interfacesModel =
                    new SpinnerNumberModel(24, 0, 31, 1);
            this.maskSpinner = new JSpinner();
            this.maskLabel = new JLabel("Mask: ");
        }

        setMinimumSize(new Dimension(600, 400));
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void layoutDevice(){
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy = 0;
        gbc.gridx = 0;

        add(devicePanel, gbc);
        gbc.gridx++;
    }

    private void layoutRouter(){
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy = 0;
        gbc.gridx = 0;

        add(devicePanel, gbc);
        gbc.gridy++;
        add(routerPanel, gbc);
        gbc.gridy++;
    }

    private void layoutNetwork() {
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy = 0;
        gbc.gridx = 0;

        add(devicePanel, gbc);
        gbc.gridy++;
        add(maskLabel, gbc);
        gbc.gridx++;
        add(maskSpinner, gbc);
    }
}
