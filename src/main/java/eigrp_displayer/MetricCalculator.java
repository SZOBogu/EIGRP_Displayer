package eigrp_displayer;

public class MetricCalculator {

    public int calculateMetric(Router router, RoutingTableEntry entry){
        int k1 = router.isK1() ? 1 : 0;
        int k2 = router.isK2() ? 1 : 0;
        int k3 = router.isK3() ? 1 : 0;
        int k4 = router.isK4() ? 1 : 0;
        int k5 = router.isK5() ? 1 : 0;

        int bandwidth = entry.getLowestBandwidth();
        int delay = entry.getSumOfDelays();
        int load = entry.getWorstLoad();
        int reliability = entry.getWorstReliability();

        return (k1 * bandwidth +
                ((k2 * bandwidth)/(256-load))
                + k3 * delay) *
                k5/(k4 + reliability) * 256;
    }

    public int calculateMetric(int bandwidth, int delay){
        return (bandwidth + delay) * 256;
    }


    public int calculateMetric(int bandwidth, int delay,
                               int load, int reliability){
        return (bandwidth +
                (bandwidth / (256 - load))
                + delay) /(1 + reliability) * 256;
    }


    public int calculateMetric(Router router, Connection connection){
        int k1 = router.isK1() ? 1 : 0;
        int k2 = router.isK2() ? 1 : 0;
        int k3 = router.isK3() ? 1 : 0;
        int k4 = router.isK4() ? 1 : 0;
        int k5 = router.isK5() ? 1 : 0;

        if(k5 == 0){
            return (k1 * connection.getBandwidth() +
                    ((k2 * connection.getBandwidth())/(256- connection.getLoad()))
                    + k3 * connection.getDelay()) * 256;
        }
        else
            return (k1 * connection.getBandwidth() +
                    ((k2 * connection.getBandwidth())/(256- connection.getLoad()))
                    + k3 * connection.getDelay()) *
                    k5/(k4 + connection.getReliability()) * 256;
    }
}
