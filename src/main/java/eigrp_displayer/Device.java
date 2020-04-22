package eigrp_displayer;

public abstract class Device implements Addable {
    private String name;
    private IPAddress ip_address;
    private DeviceInterface[] deviceInterfaces;

    public Device(){
        this(4);
    }

    public Device(int numberOfInterfaces){
        this.deviceInterfaces = new DeviceInterface[numberOfInterfaces];
        for(int i = 0; i < numberOfInterfaces; i++){
            this.deviceInterfaces[i] = new DeviceInterface("Interface " + i);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IPAddress getIp_address() {
        return ip_address;
    }

    public void setIp_address(IPAddress ip_address) {
        this.ip_address = ip_address;
    }

    public DeviceInterface[] getDeviceInterfaces() {
        return deviceInterfaces;
    }

    public void setConnection(Connection connection){
        for(int i = 0; i < this.getDeviceInterfaces().length; i++){
            if(this.getDeviceInterfaces()[i].getConnection() == null) {
                this.getDeviceInterfaces()[i].setConnection(connection);
                break;
            }
        }
    }

    public void checkConnection(Connection connection){
        //przelicz trasy
    }
}
