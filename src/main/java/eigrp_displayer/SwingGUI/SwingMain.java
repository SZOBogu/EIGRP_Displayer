package eigrp_displayer.SwingGUI;

import eigrp_displayer.MessageScheduler;
import eigrp_displayer.PremadeNetwork;

import javax.swing.*;

public class SwingMain {
    public static void main(String[] args){
        MessageScheduler.getInstance().setNetwork(PremadeNetwork.getNetwork());
        SwingUtilities.invokeLater(DisplayFrame::new);
    }
}
