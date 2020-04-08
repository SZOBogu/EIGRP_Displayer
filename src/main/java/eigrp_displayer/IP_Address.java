package eigrp_displayer;

//IPv4 for simplicity's sake
public class IP_Address {
    private Integer firstOctet;
    private Integer secondOctet;
    private Integer thirdOctet;
    private Integer fourthOctet;
    private SubnetMask mask;

    public IP_Address(int firstOctet, int secondOctet,
                      int thirdOctet, int fourthOctet, SubnetMask mask){
        this.firstOctet = firstOctet;
        this.secondOctet = secondOctet;
        this.thirdOctet = thirdOctet;
        this.fourthOctet = fourthOctet;
        this.mask = mask;
    }

    public IP_Address(IP_Address ip_address){
        this.firstOctet = ip_address.getFirstOctet();
        this.secondOctet = ip_address.getSecondOctet();
        this.thirdOctet = ip_address.getThirdOctet();
        this.fourthOctet = ip_address.getFourthOctet();
        this.mask = ip_address.getMask();
    }

    public void setAddress(int firstOctet, int secondOctet,
                            int thirdOctet, int fourthOctet) {
        this.firstOctet = firstOctet;
        this.secondOctet = secondOctet;
        this.thirdOctet = thirdOctet;
        this.fourthOctet = fourthOctet;
    }

    public int getFirstOctet() {
        return firstOctet;
    }

    public void setFirstOctet(int firstOctet) {
        this.firstOctet = firstOctet;
    }

    public int getSecondOctet() {
        return secondOctet;
    }

    public void setSecondOctet(int secondOctet) {
        this.secondOctet = secondOctet;
    }

    public int getThirdOctet() {
        return thirdOctet;
    }

    public void setThirdOctet(int thirdOctet) {
        this.thirdOctet = thirdOctet;
    }

    public int getFourthOctet() {
        return fourthOctet;
    }

    public void setFourthOctet(int fourthOctet) {
        this.fourthOctet = fourthOctet;
    }

    public SubnetMask getMask() {
        return mask;
    }

    public void setMask(SubnetMask mask) {
        this.mask = mask;
    }
}
