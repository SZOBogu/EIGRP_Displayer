package eigrp_displayer;

public class Route {
    private IP_Address ip_address;
    private int feasibleDistance;
    private int reportedDistance;
    String connectionType;

    public IP_Address getIp_address() {
        return ip_address;
    }

    public void setIp_address(IP_Address ip_address) {
        this.ip_address = ip_address;
    }

    public int getFeasibleDistance() {
        return feasibleDistance;
    }

    public void setFeasibleDistance(int feasibleDistance) {
        this.feasibleDistance = feasibleDistance;
    }

    public int getReportedDistance() {
        return reportedDistance;
    }

    public void setReportedDistance(int reportedDistance) {
        this.reportedDistance = reportedDistance;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }
}
