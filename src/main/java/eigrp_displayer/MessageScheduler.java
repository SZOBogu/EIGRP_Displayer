package eigrp_displayer;

import eigrp_displayer.messages.RTPMessage;

import java.util.*;

public class MessageScheduler implements ClockDependent{
    private HashMap<DeviceController, List<RTPMessage>> schedule = new HashMap<>();
    private Network network = new Network();

    private static class MessageSchedulerSingleton{
        private static final MessageScheduler scheduler = new MessageScheduler();
    }

    public static MessageScheduler getInstance(){
        return MessageSchedulerSingleton.scheduler;
    }

    public HashMap<DeviceController, List<RTPMessage>> getSchedule() {
        return schedule;
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network){
        this.network = network;
    }

    public void clear(){
        for (List<RTPMessage> rtpMessages : this.schedule.values()) {
            rtpMessages.clear();
            for (int j = 0; j < 10000; j++) {
                rtpMessages.add(null);
            }
        }
    }

    public int getTicksToAnotherMessage(){
        List<Integer> indexesOfClosestOccurrences = new ArrayList<>();

        for(List<RTPMessage> messageList : this.schedule.values()){
            for(int i = Clock.getTime(); i < messageList.size(); i++){
                 if(messageList.get(i) != null){
                     indexesOfClosestOccurrences.add(i);
                     break;
                }
            }
        }
        Collections.sort(indexesOfClosestOccurrences);
        if(indexesOfClosestOccurrences.isEmpty())
            return this.schedule.size();
        else
            return indexesOfClosestOccurrences.get(1) - indexesOfClosestOccurrences.get(0);
    }

    @Override
    public void updateTime() {
        for(int i = 0; i < this.schedule.size(); i++){
            RTPMessage message = this.schedule.get(Clock.getTime()).get(i);
            this.network.getDeviceController(message.getReceiverAddress()).respond(message);
        }
        Clock.incrementClock(this.getTicksToAnotherMessage());
    }
}