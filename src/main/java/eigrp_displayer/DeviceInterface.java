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
}
