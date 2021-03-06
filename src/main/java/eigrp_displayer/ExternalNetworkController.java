package eigrp_displayer;

import eigrp_displayer.messages.CyclicMessage;
import eigrp_displayer.messages.Message;
import eigrp_displayer.messages.Packet;
import eigrp_displayer.messages.PacketACK;

import java.util.ArrayList;
import java.util.List;

public class ExternalNetworkController extends DeviceController{
    private List<PacketTargetModel> packetTargetModelList;
    private int sentPacketsCount;

    public ExternalNetworkController(ExternalNetwork externalNetwork){
        this();
        this.setDevice(externalNetwork);
    }

    public ExternalNetworkController(){
        super();
        this.packetTargetModelList = new ArrayList<>();
        this.sentPacketsCount = 0;
    }

    @Override
    public void scheduleCyclicMessages() {
        for(PacketTargetModel targetModel : this.packetTargetModelList) {
            Packet packet = new Packet(this.getDevice().getIp_address(),
                    this.getDevice().getIp_address(),
                    targetModel.getNextHopAddress(),
                    targetModel.getTargetAddress(), Clock.getTime());
            CyclicMessage cyclicMessage = new CyclicMessage(packet, 10);
            this.sendCyclicMessage(cyclicMessage, 500);
        }
    }

    @Override
    public void respond(Message message){
        EventLog.messageReceived(this, message);

        if(message instanceof PacketACK){
            for(PacketTargetModel targetModel : this.packetTargetModelList){
                if(targetModel.getTargetAddress().equals(message.getSenderAddress())){
                    targetModel.getAckedMessageNumberSet().add(((PacketACK) message).getPacketNumber());
                }
            }
        }
    }

    public PacketTargetModel getPacketTargetModel(int i){
        return this.packetTargetModelList.get(i);
    }

    public void addPacketTargetModel(PacketTargetModel packetTargetModel){
        this.packetTargetModelList.add(packetTargetModel);
    }

    public int packetTargetModelSize(){
        return this.packetTargetModelList.size();
    }

    public void removePacketTargetModel(PacketTargetModel packetTargetModel){
        this.packetTargetModelList.remove(packetTargetModel);
    }
}