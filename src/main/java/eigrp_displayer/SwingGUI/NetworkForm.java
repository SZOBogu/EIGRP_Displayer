package eigrp_displayer.SwingGUI;

import javax.swing.*;
import java.awt.*;

public class NetworkForm extends JFrame {
    private NetworkFormPanel networkFormPanel;

    public NetworkForm(){
        super("Network edit window");
        this.networkFormPanel = new NetworkFormPanel();

        setLayout(new BorderLayout());
        add(new JScrollPane(this.networkFormPanel,
                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER);

        setMinimumSize(new Dimension(600, 400));
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}