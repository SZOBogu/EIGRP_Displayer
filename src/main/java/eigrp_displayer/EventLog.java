package eigrp_displayer;

public class EventLog {
    private static String eventLog = "";

    private static class SingletonHolder{
        private static final EventLog eventLog = new EventLog();
    }

    public static EventLog getInstance(){
        return SingletonHolder.eventLog;
    }

    public static void appendLog(String string){
        eventLog += string + "\n";
    }

    public static String getEventLog(){
        return eventLog;
    }

    public static void clear(){
        eventLog = "";
    }
}
