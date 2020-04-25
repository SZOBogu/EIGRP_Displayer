package eigrp_displayer;

public class Network extends Device implements Addable {
    private Mask mask;

    public Network(){
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
