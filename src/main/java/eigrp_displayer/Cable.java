package eigrp_displayer;

public class Cable extends Link{
    private int brandwidth;
    private int delay;

    public Cable(){
        this.setName("Ethernet Cable");
        this.brandwidth = 100000;
        this.delay = 100;
    }

    public int getBrandwidth() {
        return brandwidth;
    }

    public void setBrandwidth(int brandwidth) {
        this.brandwidth = brandwidth;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
