package eigrp_displayer;

public class SubnetMask {
    int mask;

    public SubnetMask(int mask){
        this.mask = mask;
    }

    public int getMask() {
        return mask;
    }

    public void setMask(int mask) {
        this.mask = mask;
    }

    public int calculateAvailableAddresses(){
        return (int)Math.pow(2, 32 - this.mask);
    }
}
