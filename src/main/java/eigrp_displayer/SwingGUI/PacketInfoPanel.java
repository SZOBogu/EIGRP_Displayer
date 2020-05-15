//package eigrp_displayer.SwingGUI;
//
//import eigrp_displayer.ExternalNetworkController;
//
//import javax.swing.*;
//import java.awt.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class PacketInfoPanel extends JPanel implements Refreshable{
//    private ExternalNetworkController controller;
//    private List<JLabel> infoLabels;
//
//    public PacketInfoPanel(ExternalNetworkController externalNetworkController){
//        super();
//        this.controller = externalNetworkController;
//        this.infoLabels = new ArrayList<>();
//        for(int i = 0 ; i < this.controller.packetTargetModelSize(); i++){
//            infoLabels.add(new JLabel());
//        }
//        this.refresh();
//
//        this.layoutComponents();
//    }
//
//    private void layoutComponents(){
//        setLayout(new GridBagLayout());
//
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.weighty = 0.1;
//        gbc.fill = GridBagConstraints.NONE;
//        gbc.anchor = GridBagConstraints.CENTER;
//
//        gbc.gridy = 0;
//        gbc.gridx = 0;
//
//        for(JLabel label : this.infoLabels){
//            add(label,gbc);
//            gbc.gridy++;
//        }
//    }
//
//    @Override
//    public void refresh() {
//        for(int i = 0 ; i < this.controller.packetTargetModelSize(); i++){
//            this.infoLabels.get(i).setText("Target: " + this.controller.getPacketTargetModel(i).getTargetAddress() +
//                    " Packets sent: " + this.controller.getPacketTargetModel(i).getSentPacketCount() +
//                    "/" + this.controller.getPacketTargetModel(i).getAckedMessageNumberSet().size() + "\n");
//        }
//    }
//}
