package eigrp_displayer;

import eigrp_displayer.messages.Message;

import java.util.*;

public class MessageScheduler implements ClockDependent{
    private List<List<Message>> messageSchedules = new ArrayList<>();
    private List<DeviceController> controllers = new ArrayList<>();
    private Network network = new Network();

    private static class MessageSchedulerSingleton{
        private static final MessageScheduler scheduler = new MessageScheduler();
    }

    public static MessageScheduler getInstance(){
        return MessageSchedulerSingleton.scheduler;
    }

    public List<List<Message>> getMessageSchedules() {
        return messageSchedules;
    }

    public List<DeviceController> getControllers() {
        return controllers;
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network){
        this.network = network;
    }

    public void clear(){
        for (List<Message> messages : this.messageSchedules) {
            messages.clear();
            for (int j = 0; j < 10000; j++) {
                messages.add(null);
            }
        }
    }

    public int getTicksToAnotherMessage(){
        List<Integer> indexesOfClosestOccurrences = new ArrayList<>();

        for(List<Message> messageList : this.messageSchedules){
            for(int i = Clock.getTime(); i < messageList.size(); i++){
                 if(messageList.get(i) != null){
                     indexesOfClosestOccurrences.add(i);
                     break;
                }
            }
        }
        Collections.sort(indexesOfClosestOccurrences);
        if(indexesOfClosestOccurrences.isEmpty())
            return this.messageSchedules.size();
        else
            return indexesOfClosestOccurrences.get(1) - indexesOfClosestOccurrences.get(0);
    }

    @Override
    public void updateTime() {
        for(int i = 0; i < this.messageSchedules.size(); i++){
            Message message = this.messageSchedules.get(Clock.getTime()).get(i);
            EventLog.messageSent(this.controllers.get(i), message);
            this.network.getDeviceController(message.getReceiverAddress()).respond(message);
        }
        Clock.incrementClock(this.getTicksToAnotherMessage());
    }
}