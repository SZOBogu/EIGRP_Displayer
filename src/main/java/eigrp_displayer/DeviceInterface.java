package eigrp_displayer;

public class DeviceInterface {
    private String name;
    private Connection connection;

    public DeviceInterface(){
        this("Any");
    }

    public DeviceInterface(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean checkIfOtherDeviceControllerConnected(DeviceController controller){
//        try {
            return this.connection != null && this.connection.getOtherDevice(controller) != null;
//        }
//        catch(NullPointerException e) {
//            return false;
//        }
    }

    public DeviceController getOtherDeviceController(DeviceController controller){
        if(this.checkIfOtherDeviceControllerConnected(controller))
            return this.connection.getOtherDevice(controller);
        else
            return null;
    }
}