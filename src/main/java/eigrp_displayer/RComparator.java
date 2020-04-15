package eigrp_displayer;

public class RComparator {
    public boolean compare(Router router1, Router router2){
        return router1.isK1() == router2.isK1() &&
                router1.isK2() == router2.isK2() &&
                router1.isK3() == router2.isK3() &&
                router1.isK4() == router2.isK4() &&
                router1.isK5() == router2.isK5();
    }
}
