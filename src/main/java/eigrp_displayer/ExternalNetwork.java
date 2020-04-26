package eigrp_displayer;

public class ExternalNetwork extends Device implements Addable {
    private Mask mask;

    public ExternalNetwork(){
        super(100);
        super.setName("External Network");
    }

    public Mask getMask() {
        return mask;
    }

    public void setMask(Mask mask) {
        this.mask = mask;
    }
}
