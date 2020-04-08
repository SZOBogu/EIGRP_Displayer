package eigrp_displayer;

public class IP_Calculator {
    public void incrementAddress(IP_Address ip_address){
        if(ip_address.getFourthOctet() == 255){
            ip_address.setFourthOctet(0);
            if(ip_address.getThirdOctet() == 255){
                ip_address.setThirdOctet(0);
                if(ip_address.getSecondOctet() == 255){
                    ip_address.setSecondOctet(0);
                    if(ip_address.getFirstOctet() == 255){
                        ip_address.setFirstOctet(0);
                        ip_address.setSecondOctet(0);
                        ip_address.setThirdOctet(0);
                        ip_address.setFourthOctet(0);
                    }
                    else
                        ip_address.setFirstOctet(ip_address.getSecondOctet() + 1);
                }
                else
                    ip_address.setThirdOctet(ip_address.getSecondOctet() + 1);
            }
            else
                ip_address.setThirdOctet(ip_address.getThirdOctet() + 1);
        }
        else
            ip_address.setFourthOctet(ip_address.getFourthOctet() + 1);
    }

    public int difference(IP_Address address1, IP_Address address2){
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

    //higher(?) you know 168.192.100.2 is sort of higher than 168.192.100.1
    public boolean isBigger(IP_Address address1, IP_Address address2){
        if(this.difference(address1, address2) > 0)
            return true;
        else
            return false;
    }
}
