package eigrp_displayer.messages;

public class CyclicMessage {
    private Message message;
    private int interval;

    public CyclicMessage(Message message, int interval){
        this.message = message;
        this.interval = interval;
    }

    public Message getMessage() {
        return message;
    }

    public int getInterval() {
        return interval;
    }
}
