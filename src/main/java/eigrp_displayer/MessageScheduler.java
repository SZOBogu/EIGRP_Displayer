package eigrp_displayer;

import eigrp_displayer.messages.CyclicMessage;
import eigrp_displayer.messages.Message;
import eigrp_displayer.messages.Placeholder;

import java.util.ArrayList;
import java.util.List;

public class MessageScheduler {
    private List<List<Message>> schedule;
    private Integer currentTime;            //maybe a clock object someday
    private ShowcaseNetwork network;

    private MessageScheduler(){
        this.schedule = new ArrayList<>();
        this.currentTime = 0;
        for(int i = 0; i < 10000; i++){
            this.schedule.add(new ArrayList<>());
            this.schedule.get(i).add(new Placeholder());
        }
    }

    private static class MessageSchedulerSingleton{
        private static final MessageScheduler scheduler = new MessageScheduler();
    }

    public static MessageScheduler getInstance(){
        return MessageSchedulerSingleton.scheduler;
    }

    public void scheduleMessage(Message message){
        for(int i = this.currentTime; i < this.schedule.size(); i++){
            this.schedule.get(this.currentTime).add(message);
        }
    }

    public void scheduleMessage(CyclicMessage message){
        for(int i = this.currentTime; i < this.schedule.size(); i++){
            if(this.currentTime % message.getInterval() == 0){
                this.scheduleMessage(message.getMessage());
            }
        }
    }

    public void setNetwork(ShowcaseNetwork network){
        this.network = network;
    }
}
