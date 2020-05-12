package eigrp_displayer.SwingGUI;

import eigrp_displayer.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DeviceFormFrame extends JFrame implements ActionListener {
    private DeviceController controller;
    private DevicePanel devicePanel;
    private RouterForm routerPanel;
    private JLabel maskLabel;
    private JSpinner maskSpinner;       //network only gets mask, which is covered by spinner
    private GridBagConstraints gbc;
    private JButton backButton;
    private JButton editButton;


    public DeviceFormFrame(DeviceController controller){
        super();
        this.controller = controller;
        this.devicePanel = new DevicePanel(controller);
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
        this.editButton.addActionListener(this);
        this.backButton.addActionListener(this);

        setLayout(new GridBagLayout());
        this.gbc = new GridBagConstraints();
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy = 0;
        gbc.gridx = 0;

        setMinimumSize(new Dimension(600, 400));
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        if(controller instanceof RouterController) {
            this.layoutRouter();
        }
        else if(controller instanceof ExternalNetworkController) {
            this.layoutNetwork();
        }
        else{
            this.layoutDevice();
        }
    }

    private void layoutDevice(){
        add(devicePanel, gbc);
        gbc.gridy++;
        add(editButton, gbc);
        gbc.gridy++;
        add(backButton, gbc);
        gbc.gridy++;
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
        ArrayList<DeviceController> controllerArrayList = new ArrayList<>(MessageScheduler.getInstance().getNetwork().getDeviceControllers());

        if (clickedButton == this.editButton) {
            System.out.println("edit klikniet");
            if(this.routerPanel != null){
                System.out.println("jest ruter");

                Router router = new Router(this.devicePanel.getName());
                router.setName(this.devicePanel.getName());
                router.setIp_address(this.devicePanel.getIP());
                router.setMessageSendingTimeOffset(this.devicePanel.getOffset());
                router.setK1(this.routerPanel.getK1());
                router.setK2(this.routerPanel.getK2());
                router.setK3(this.routerPanel.getK3());
                router.setK4(this.routerPanel.getK4());
                router.setK5(this.routerPanel.getK5());

                if(!router.equals(this.controller.getDevice())) {
                    System.out.println("nie jest taki sam jak w kontorlerze");

                    controllerArrayList.set(controllerArrayList.indexOf(this.controller), this.controller);
                    EventLog.deviceChanged(this.controller);
                    this.controller.setDevice(router);

                    JOptionPane.showMessageDialog(new JFrame(), "Router changed", "Dialog",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
            else if(this.maskSpinner != null){
                ExternalNetwork externalNetwork = new ExternalNetwork();
                externalNetwork.setName(this.devicePanel.getName());
                externalNetwork.setIp_address(this.devicePanel.getIP());
                externalNetwork.setMessageSendingTimeOffset(this.devicePanel.getOffset());
                externalNetwork.setMask(new Mask((int)this.maskSpinner.getValue()));
                if(!externalNetwork.equals(this.controller.getDevice())) {
                    controllerArrayList.set(controllerArrayList.indexOf(this.controller), this.controller);
                    EventLog.deviceChanged(this.controller);
                    this.controller.setDevice(externalNetwork);

                    JOptionPane.showMessageDialog(new JFrame(), "External network edited", "Dialog",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
            else{
                EndDevice device = new EndDevice();
                device.setName(this.devicePanel.getName());
                device.setIp_address(this.devicePanel.getIP());
                device.setMessageSendingTimeOffset(this.devicePanel.getOffset());
                if(!device.equals(this.controller.getDevice())) {
                    controllerArrayList.set(controllerArrayList.indexOf(this.controller), this.controller);
                    EventLog.deviceChanged(this.controller);
                    this.controller.setDevice(device);

                    JOptionPane.showMessageDialog(new JFrame(), "Device edited", "Dialog",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        else if (clickedButton == this.backButton) {
            new NetworkForm();
            this.dispose();
        }
    }
}
