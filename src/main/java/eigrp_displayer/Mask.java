package eigrp_displayer;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mask mask1 = (Mask) o;
        return getMask() == mask1.getMask();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMask());
    }
}
