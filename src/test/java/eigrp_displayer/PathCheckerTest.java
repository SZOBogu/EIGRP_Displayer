package eigrp_displayer;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PathCheckerTest {
    ExternalNetwork network = new ExternalNetwork();
    Router router0 = new Router("R0");
    Router router1 = new Router("R1");
    Device device = new EndDevice();

    DeviceController netController = new DeviceController(network);
    RouterController routerController0 = new RouterController(router0);
    RouterController routerController1 = new RouterController(router1);
    DeviceController deviceController = new DeviceController(device);

    Connection connection0 = new Cable();
    Connection connection1 = new Cable();
    Connection connection2 = new Cable();

    @Test
    void checkPath() {
        connection0.linkDevices(netController, routerController0);
        connection1.linkDevices(routerController0, routerController1);
        connection2.linkDevices(routerController1, deviceController);

        List<Connection> path = new ArrayList<>();
        path.add(connection0);
        path.add(connection1);
        path.add(connection2);

        assertTrue(PathChecker.checkPath(path));
        connection1.setDevice1(null);
        assertFalse(PathChecker.checkPath(path));
    }
}