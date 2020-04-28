package eigrp_displayer;

import eigrp_displayer.messages.NullMessage;
import eigrp_displayer.messages.RTPMessage;

import java.util.ArrayList;
import java.util.List;

public class MessageScheduler implements ClockDependent{
    private List<List<RTPMessage>> schedule = new ArrayList<>();
    private Network network = new Network();

    private static class MessageSchedulerSingleton{
        private static final MessageScheduler scheduler = new MessageScheduler();
    }

    public static MessageScheduler getInstance(){
        return MessageSchedulerSingleton.scheduler;
    }

    public List<List<RTPMessage>> getSchedule() {
        return schedule;
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network){
        this.network = network;
    }

    public void clear(){
        for (List<RTPMessage> rtpMessages : this.schedule) {
            rtpMessages.replaceAll(value -> new NullMessage());
        }
    }

    //TODO: tests
    public int getTicksToAnotherMessage(){
        int tickCounter = 0;
        for(int i = Clock.getTime(); i < 10000; i++){
            for(int j = 0 ; j < this.schedule.size(); j++){
                if(!(this.schedule.get(i).get(j) instanceof NullMessage)){
                    break;
                }
                tickCounter ++;
            }
        }
        return tickCounter;
    }

    @Override
    public void updateTime() {
        for(int i = 0; i < this.schedule.size(); i++){
            RTPMessage message = this.schedule.get(Clock.getTime()).get(i);
            this.network.getDeviceController(message.getReceiverAddress()).respond(message);
        }
    }
}