package eigrp_displayer;

import java.util.ArrayList;
import java.util.List;

public class Clock {
    private int currentTime;
    private List<ClockDependent> clockDependents;

    private Clock(){
        this.currentTime = 0;
        this.clockDependents = new ArrayList<>();
    }

    private static class ClockSingleton{
        private static Clock clock = new Clock();
    }

    public static Clock getInstance(){
        return ClockSingleton.clock;
    }

    public static int getTime(){
        return ClockSingleton.clock.currentTime;
    }

    public static List<ClockDependent> getClockDependents(){
        return ClockSingleton.clock.clockDependents;
    }

    public static void incrementClock(){
        ClockSingleton.clock.currentTime++;
        for(ClockDependent cd : ClockSingleton.clock.clockDependents){
            cd.updateTime();
        }
    }

    public static void incrementClock(int by){
        for(int i = 0; i < by; i++){
            incrementClock();
        }
    }

    public static void resetClock(){
        ClockSingleton.clock.currentTime = 0;
    }

    public static void addClockDependant(ClockDependent clockDependent){
        ClockSingleton.clock.clockDependents.add(clockDependent);
    }

    public static void removeDependant(ClockDependent clockDependent){
        ClockSingleton.clock.clockDependents.remove(clockDependent);
    }
}
