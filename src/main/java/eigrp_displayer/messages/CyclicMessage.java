package eigrp_displayer.messages;

public class CyclicMessage {
    private RTPMessage message;
    private int interval;

    public CyclicMessage(RTPMessage message, int interval){
        this.message = message;
        this.interval = interval;
    }

    public RTPMessage getMessage() {
        return message;
    }

    public int getInterval() {
        return interval;
    }
}
