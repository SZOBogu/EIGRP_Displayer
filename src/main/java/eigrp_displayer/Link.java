package eigrp_displayer;

public abstract class Link implements Addable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
