package eigrp_displayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RouterTest {
    Router router = new Router("Some router");
    RoutingTable routingTable = Mockito.mock(RoutingTable.class);
    RoutingTable routingTable0 = Mockito.mock(RoutingTable.class);

    @BeforeEach
    void init(){
        router.setRoutingTable(routingTable);
    }

    @Test
    void getName() {
        assertEquals("Some router", router.getName());
    }

    @Test
    void setName() {
        router.setName("Test");
        assertEquals("Test", router.getName());
    }

    @Test
    void getRoutingTable() {
        assertEquals(routingTable, router.getRoutingTable());
    }

    @Test
    void setRoutingTable() {
        router.setRoutingTable(routingTable0);
        assertEquals(routingTable0, router.getRoutingTable());
    }
}