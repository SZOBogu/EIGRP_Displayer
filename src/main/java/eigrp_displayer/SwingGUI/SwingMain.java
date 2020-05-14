package eigrp_displayer.SwingGUI;

import eigrp_displayer.DeviceController;
import eigrp_displayer.MessageScheduler;
import eigrp_displayer.PremadeNetwork;

import javax.swing.*;

public class SwingMain {
    public static void main(String[] args){
        MessageScheduler scheduler = MessageScheduler.getInstance();
        scheduler.setNetwork(PremadeNetwork.getNetwork());
        scheduler.init();

        for(DeviceController controller : scheduler.getControllers()){
            System.out.println(controller.getDevice() + " " + controller.getDevice().getMessageSendingTimeOffset());
        }
        SwingUtilities.invokeLater(DisplayFrame::new);
    }
}
