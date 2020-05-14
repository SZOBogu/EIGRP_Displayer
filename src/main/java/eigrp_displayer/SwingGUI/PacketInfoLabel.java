package eigrp_displayer.SwingGUI;

import eigrp_displayer.ExternalNetworkController;

import javax.swing.*;

public class PacketInfoLabel extends JLabel implements Refreshable{
    private ExternalNetworkController controller;

    public PacketInfoLabel(ExternalNetworkController externalNetworkController){
        super();
        this.controller = externalNetworkController;
    }

    @Override
    public void refresh() {
        String string = "";

        for(int i = 0 ; i < this.controller.packetTargetModelSize(); i++){
            string += "Target: " + this.controller.getDevice().getIp_address() +
                    " Packets sent: " + this.controller.getPacketTargetModel(i).getSentPacketCount() +
                    "/" + this.controller.getPacketTargetModel(i).getAckedMessageNumberSet().size() + "\n";
        }
        super.setText(string);
    }
}
