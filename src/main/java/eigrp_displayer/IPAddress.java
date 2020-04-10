package eigrp_displayer;

import java.util.Objects;

//IPv4 for simplicity's sake
public class IPAddress implements Comparable{
    private Integer firstOctet;
    private Integer secondOctet;
    private Integer thirdOctet;
    private Integer fourthOctet;

    public IPAddress(int firstOctet, int secondOctet,
                     int thirdOctet, int fourthOctet){
        this.firstOctet = firstOctet;
        this.secondOctet = secondOctet;
        this.thirdOctet = thirdOctet;
        this.fourthOctet = fourthOctet;
    }

    public IPAddress(IPAddress ip_address){
        this.firstOctet = ip_address.getFirstOctet();
        this.secondOctet = ip_address.getSecondOctet();
        this.thirdOctet = ip_address.getThirdOctet();
        this.fourthOctet = ip_address.getFourthOctet();
    }

    public void setAddress(int firstOctet, int secondOctet,
                            int thirdOctet, int fourthOctet) {
        this.firstOctet = firstOctet;
        this.secondOctet = secondOctet;
        this.thirdOctet = thirdOctet;
        this.fourthOctet = fourthOctet;
    }

    public int getFirstOctet() {
        return firstOctet;
    }

    public void setFirstOctet(int firstOctet) {
        this.firstOctet = firstOctet;
    }

    public int getSecondOctet() {
        return secondOctet;
    }

    public void setSecondOctet(int secondOctet) {
        this.secondOctet = secondOctet;
    }

    public int getThirdOctet() {
        return thirdOctet;
    }

    public void setThirdOctet(int thirdOctet) {
        this.thirdOctet = thirdOctet;
    }

    public int getFourthOctet() {
        return fourthOctet;
    }

    public void setFourthOctet(int fourthOctet) {
        this.fourthOctet = fourthOctet;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IPAddress that = (IPAddress) o;
        return Objects.equals(getFirstOctet(), that.getFirstOctet()) &&
                Objects.equals(getSecondOctet(), that.getSecondOctet()) &&
                Objects.equals(getThirdOctet(), that.getThirdOctet()) &&
                Objects.equals(getFourthOctet(), that.getFourthOctet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstOctet(), getSecondOctet(), getThirdOctet(), getFourthOctet());
    }

    @Override
    public String toString() {
        return  firstOctet +
                "." + secondOctet +
                "." + thirdOctet +
                "." + fourthOctet;
    }

    @Override
    public int compareTo(Object ipAddress) {
        CalculatorIP calc = new CalculatorIP();
        IPAddress ipAddress1 = (IPAddress)ipAddress;

        return Integer.compare(calc.difference(this, ipAddress1), 0);
    }
}
