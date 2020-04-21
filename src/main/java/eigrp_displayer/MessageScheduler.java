package eigrp_displayer;

import eigrp_displayer.messages.CyclicMessage;
import eigrp_displayer.messages.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageScheduler {
    private List<List<Message>> schedule;
    private ShowcaseNetwork network;

    private MessageScheduler(){
        this.schedule = new ArrayList<>();
        for(int i = 0; i < 10000; i++){
            this.schedule.add(new ArrayList<>());
            //this.schedule.get(i).add(new Placeholder());
        }
    }

    private static class MessageSchedulerSingleton{
        private static final MessageScheduler scheduler = new MessageScheduler();
    }

    public static MessageScheduler getInstance(){
        return MessageSchedulerSingleton.scheduler;
    }

    public void scheduleMessage(Message message){
        this.schedule.get(Clock.getTime()).add(message);
    }

    public void scheduleMessage(Message message, int offset){
        this.schedule.get(Clock.getTime() + offset).add(message);
    }

    public void scheduleCyclicMessage(CyclicMessage message){
        for(int i = Clock.getTime(); i < this.schedule.size(); i++){
            if(Clock.getTime() % message.getInterval() == 0){
                this.scheduleMessage(message.getMessage());
            }
        }
    }

    public void scheduleCyclicMessage(CyclicMessage message, int offset){
        for(int i = Clock.getTime(); i < this.schedule.size(); i++){
            if(Clock.getTime() + (offset % message.getInterval()) == 0){
                this.scheduleMessage(message.getMessage());
            }
        }
    }

    public List<List<Message>> getSchedule() {
        return schedule;
    }

    public ShowcaseNetwork getNetwork() {
        return network;
    }

    public void setNetwork(ShowcaseNetwork network){
        this.network = network;
    }

    public void clear(){
        for(List<Message> list : this.schedule){
            list.clear();
        }
    }
}