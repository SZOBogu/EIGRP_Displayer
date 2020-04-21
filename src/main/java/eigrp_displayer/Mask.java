package eigrp_displayer;

public class Mask {
    int mask;

    public Mask(int mask){
        this.setMask(mask);
    }

    public int getMask() {
        return mask;
    }

    public void setMask(int mask) {
        if(mask > 32)
            this.mask = 32;
        else this.mask = Math.max(mask, 0);
    }

    public long calculateAvailableAddresses(){
        return (long)Math.pow(2, 32 - this.mask);
    }
}
