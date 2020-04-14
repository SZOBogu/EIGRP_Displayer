package eigrp_displayer;

import java.util.Date;

public class NeighbourTableEntry {
    /**Here is a brief description of each column

     H – the sequential numbering of established neighbor adjacencies. The first neighbor will have a value of 0, the second neighbor a value of 1 and so on.
     Address – the IP address of the EIGRP neighbor.
     Interface – the interface on the local router on which the Hello packets were received.
     Hold (sec) – the holddown timer. It specifies how long will EIGRP wait to hear from the neighbor before declaring it down.
     Uptime – the time in hours:minutes: seconds since the local router first heard from the neighbor.
     SRTT (ms) – Smooth round-trip time. The time it takes to send an EIGRP packet and receive an acknowledgment from the neighbor.
     RTO – Retransmission timeout in milliseconds. This is the time that EIGRP will wait before retransmitting a packet from the retransmission queue to a neighbor.
     Q Cnt – the number of EIGRP packets (Update, Query or Reply) in the queue that are awaiting transmission. Should be 0.
     Seq Num – the sequence number of the last update, query, or reply packet that was received from the neighbor.
     */

    private IPAddress neighbourAddress;
    private String netInterface;
    private int hold;       //timeout
    private Date uptime;
    private int srtt;
    private int rto;
    private int qCnt;
    private int seqNum;
}
