package eigrp_displayer;

public class CalculatorIP {
    public static IPAddress incrementAddress(IPAddress ip_address){
        IPAddress address = new IPAddress(ip_address);
        if(address.getFourthOctet() == 255){
            address.setFourthOctet(0);
            if(address.getThirdOctet() == 255){
                address.setThirdOctet(0);
                if(address.getSecondOctet() == 255){
                    address.setSecondOctet(0);
                    if(address.getFirstOctet() == 255){
                        address.setFirstOctet(0);
                    }
                    else {
                        address.setFirstOctet(address.getFirstOctet() + 1);
                    }
                    address.setSecondOctet(0);
                }
                else {
                    address.setSecondOctet(address.getSecondOctet() + 1);
                }
                address.setThirdOctet(0);
            }
            else {
                address.setThirdOctet(address.getThirdOctet() + 1);
            }
            address.setFourthOctet(0);
        }
        else
            address.setFourthOctet(address.getFourthOctet() + 1);

        return address;
    }

    public static IPAddress incrementAddress(IPAddress ipAddress, int incrementBy){
        IPAddress address = new IPAddress(ipAddress);
        for(int i = 0; i < incrementBy; i++){
            address = CalculatorIP.incrementAddress(address);
        }
        return address;
    }

    public static int difference(IPAddress address1, IPAddress address2){
        int firstOctetDifference = address1.getFirstOctet() - address2.getFirstOctet();
        int secondOctetDifference = address1.getSecondOctet() - address2.getSecondOctet();
        int thirdOctetDifference = address1.getThirdOctet() - address2.getThirdOctet();
        int fourthOctetDifference = address1.getFourthOctet() - address2.getFourthOctet();

        thirdOctetDifference *= 256;
        secondOctetDifference *= 256*256;
        firstOctetDifference *= 256*256*256;

        return firstOctetDifference + secondOctetDifference +
                thirdOctetDifference + fourthOctetDifference;
    }
}
