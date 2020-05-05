package eigrp_displayer;

import eigrp_displayer.messages.Message;

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

    public static void messageSent(DeviceController controller, Message message){
        appendLog(Clock.getTime() + ": " + message.getClass().getSimpleName()
                + " sent from " + controller.getDevice().toString() + " to " + message.getReceiverAddress());
    }

    public static void messageReceived(DeviceController controller, Message message){
        appendLog(Clock.getTime() + ": " + controller.getDevice().toString()
                + " received " + message.getClass().getSimpleName()
                + " from " + message.getSenderAddress());
    }

    public static void connectionChanged(Connection connection, String whatChanged){
        appendLog(Clock.getTime() + ": " + connection + " " + whatChanged + " has been changed.");
    }

    public static void deviceConnected(DeviceController controller, Connection connection){
        appendLog(Clock.getTime() + ": " + controller.getDevice().toString()
                + " connected by " + connection.toString());
    }

    public static void deviceChanged(DeviceController device, String whatChanged){
        appendLog(Clock.getTime() + ": " + device.getDevice() + " " + whatChanged + " has been changed.");
    }

    public static void deviceUnreachable(DeviceController device){
        appendLog(Clock.getTime() + ": " + device.getDevice() + " became unreachable.");
    }

    public static void neighbourshipFormed(DeviceController device1, DeviceController device2){
        appendLog(Clock.getTime() + ": " + device1.getDevice() + " and "
                + device2.getDevice() + " became neighbours.");
    }

    public static void neighbourshipBroken(DeviceController device1, DeviceController device2){
        appendLog(Clock.getTime() + ": " + "Neighbourship between "
                + device1.getDevice() + " and " + device2.getDevice() + " broken.");
    }

    public static String getEventLog(){
        return eventLog;
    }

    public static void clear(){
        eventLog = "";
    }
}
