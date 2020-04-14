package eigrp_displayer;

import eigrp_displayer.messages.CyclicMessage;
import eigrp_displayer.messages.Message;
import eigrp_displayer.messages.Placeholder;

import java.util.ArrayList;
import java.util.List;

public class MessageScheduler {
    private List<Message> schedule;
    private int currentTime;            //maybe a clock object someday

    private MessageScheduler(){
        this.schedule = new ArrayList<>();
        for(int i = 0; i < 10000; i++){
            this.schedule.add(new Placeholder());
        }
    }

    private static class MessageSchedulerSingleton{
        private static final MessageScheduler scheduler = new MessageScheduler();
    }

    public static MessageScheduler getInstance(){
        return MessageSchedulerSingleton.scheduler;
    }

    public void scheduleMessage(Message message){
        for(int i = 0; i < this.schedule.size() - currentTime; i++){
            if(this.schedule.get(i + currentTime) instanceof Placeholder) {
                this.schedule.remove(i + currentTime);
                this.schedule.add(i + currentTime, message);
                break;
            }
        }
    }

    public void scheduleMessage(CyclicMessage message){
        int messagesToSchedule = 0;
        for(int i = 0; i < this.schedule.size() - currentTime; i++){
            if(this.currentTime % message.getInterval() == 0)
                messagesToSchedule++;
            if(messagesToSchedule > 0) {
                if (this.schedule.get(i + currentTime) instanceof Placeholder) {
                    this.schedule.remove(i + currentTime);
                    this.schedule.add(i + currentTime, message.getMessage());
                    messagesToSchedule--;
                }
            }
        }
    }

}
