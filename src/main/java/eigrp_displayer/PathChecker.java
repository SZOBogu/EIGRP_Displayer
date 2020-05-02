package eigrp_displayer;

import java.util.List;

public class PathChecker {
    public static boolean checkPath(List<Connection> path){
        for(Connection connection : path){
            if(connection.getDevice1() == null || connection.getDevice2() == null)
                return false;
        }
        return true;
    }
}
