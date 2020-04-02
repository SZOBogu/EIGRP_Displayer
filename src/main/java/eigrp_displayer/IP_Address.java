package eigrp_displayer;

public class IP_Address {
    private String address;
    private SubnetMask mask;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public SubnetMask getMask() {
        return mask;
    }

    public void setMask(SubnetMask mask) {
        this.mask = mask;
    }

    public IP_Address(String address, SubnetMask mask){
        this.address = address;
        this.mask = mask;
    }
}
