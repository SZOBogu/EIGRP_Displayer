package eigrp_displayer;

import eigrp_displayer.messages.NullMessage;
import eigrp_displayer.messages.RTPMessage;

import java.util.ArrayList;
import java.util.List;

public class MessageScheduler implements ClockDependent{
    private List<List<RTPMessage>> schedule = new ArrayList<>();
    private ShowcaseNetwork network;

    private static class MessageSchedulerSingleton{
        private static final MessageScheduler scheduler = new MessageScheduler();
    }

    public static MessageScheduler getInstance(){
        return MessageSchedulerSingleton.scheduler;
    }

//    public void scheduleMessage(RTPMessage message){
//        this.schedule.get(Clock.getTime()).add(message);
//    }
//
//    public void scheduleMessage(RTPMessage message, int offset){
//        this.schedule.get(Clock.getTime() + offset).add(message);
//    }
//
//    public void scheduleCyclicMessage(CyclicMessage message){
//        for(int i = Clock.getTime(); i < this.schedule.size(); i++){
//            if(Clock.getTime() % message.getInterval() == 0){
//                this.scheduleMessage(message.getMessage());
//            }
//        }
//    }
//
//    public void scheduleCyclicMessage(CyclicMessage message, int offset){
//        for(int i = Clock.getTime(); i < this.schedule.size(); i++){
//            if(Clock.getTime() + (offset % message.getInterval()) == 0){
//                this.scheduleMessage(message.getMessage());
//            }
//        }
//    }

    public List<List<RTPMessage>> getSchedule() {
        return schedule;
    }

    public ShowcaseNetwork getNetwork() {
        return network;
    }

    public void setNetwork(ShowcaseNetwork network){
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