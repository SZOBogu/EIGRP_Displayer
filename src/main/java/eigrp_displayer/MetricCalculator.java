package eigrp_displayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MetricCalculator {

    public static long calculateMetric(Router router, RoutingTableEntry entry){
        //list has 0 element null, other int values are according to k values in router
        List<Integer> k = MetricCalculator.getKValueInts(router);

        int bandwidth = entry.getLowestBandwidth();
        int delay = entry.getSumOfDelays();
        int load = entry.getWorstLoad();
        int reliability = entry.getWorstReliability();

        return (k.get(1) * bandwidth +
                ((k.get(2) * bandwidth)/(256-load))
                + k.get(3) * delay) *
                k.get(5)/(k.get(4) + reliability) * 256;
    }

    public static long calculateMetric(int bandwidth, int delay){
        return (bandwidth + delay) * 256;
    }


    public static long calculateMetric(int bandwidth, int delay,
                               int load, int reliability){
        return (bandwidth +
                (bandwidth / (256 - load))
                + delay) /(1 + reliability) * 256;
    }


    public static long calculateMetric(Router router, Connection connection){
        //list has 0 element null, other int values are according to k values in router
        List<Integer> k = MetricCalculator.getKValueInts(router);

        if(k.get(5) == 0){
            return (k.get(1) * connection.getBandwidth() +
                    ((k.get(2) * connection.getBandwidth())/(256- connection.getLoad()))
                    + k.get(3) * connection.getDelay()) * 256;
        }
        else
            return (k.get(1) * connection.getBandwidth() +
                    ((k.get(2) * connection.getBandwidth())/(256- connection.getLoad()))
                    + k.get(3) * connection.getDelay()) *
                    k.get(5)/(k.get(4) + connection.getReliability()) * 256;
    }

    private static List<Integer> getKValueInts(Router router){
        int k1 = router.isK1() ? 1 : 0;
        int k2 = router.isK2() ? 1 : 0;
        int k3 = router.isK3() ? 1 : 0;
        int k4 = router.isK4() ? 1 : 0;
        int k5 = router.isK5() ? 1 : 0;

        //added null first so indexes match actual number, preventing confusion
        return new ArrayList<>(Arrays.asList(null, k1, k2, k3, k4, k5));
    }
}
