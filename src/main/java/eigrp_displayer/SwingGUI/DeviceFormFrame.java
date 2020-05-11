package eigrp_displayer.SwingGUI;

import eigrp_displayer.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeviceFormFrame extends JFrame implements ActionListener {
    private DeviceController controller;
    private DeviceForm devicePanel;
    private RouterForm routerPanel;
    private JLabel maskLabel;
    private JSpinner maskSpinner;       //network only gets mask, which is covered by spinner
    private GridBagConstraints gbc;
    private JButton backButton;
    private JButton editButton;


    public DeviceFormFrame(DeviceController controller){
        this.controller = controller;
        this.devicePanel = new DeviceForm(controller);
        if(controller instanceof RouterController){
            this.routerPanel = new RouterForm((RouterController) controller);
        }
        else if(controller instanceof ExternalNetworkController){
            SpinnerNumberModel interfacesModel =
                    new SpinnerNumberModel(24, 0, 31, 1);
            this.maskSpinner = new JSpinner(interfacesModel);
            this.maskLabel = new JLabel("Mask: ");
        }

        this.editButton = new JButton("Edit Device");
        this.backButton = new JButton("Back Button");

        setLayout(new GridBagLayout());
        this.gbc = new GridBagConstraints();
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy = 0;
        gbc.gridx = 0;

        if(controller instanceof RouterController) {
            this.layoutRouter();
        }
        else if(controller instanceof ExternalNetworkController) {
            this.layoutNetwork();
        }
        else{
            this.layoutDevice();
        }

        setMinimumSize(new Dimension(600, 400));
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void layoutDevice(){
        add(devicePanel, gbc);
        gbc.gridy++;
        add(editButton, gbc);
        gbc.gridy++;
        add(backButton, gbc);
    }

    private void layoutRouter(){
        this.layoutDevice();
        add(routerPanel, gbc);
        gbc.gridy++;
    }

    private void layoutNetwork() {
        this.layoutDevice();
        add(maskLabel, gbc);
        gbc.gridx++;
        add(maskSpinner, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JButton clickedButton = (JButton) actionEvent.getSource();

        if (clickedButton == this.editButton) {
            this.controller.getDevice().setName(this.devicePanel.getName());
            this.controller.getDevice().setIp_address(this.devicePanel.getIP());
            this.controller.getDevice().setMessageSendingTimeOffset(this.devicePanel.getOffset());
            if(this.routerPanel != null){
                Router router = (Router)this.controller.getDevice();
                router.setK1(this.routerPanel.getK1());
                router.setK2(this.routerPanel.getK2());
                router.setK3(this.routerPanel.getK3());
                router.setK4(this.routerPanel.getK4());
                router.setK5(this.routerPanel.getK5());
            }
            if(this.maskSpinner != null){
                ExternalNetwork externalNetwork = (ExternalNetwork) this.controller.getDevice();
                externalNetwork.setMask(new Mask((int)this.maskSpinner.getValue()));
            }
        }
        else if (clickedButton == this.backButton) {
            new DisplayFrame();
            this.dispose();
        }
    }
}
