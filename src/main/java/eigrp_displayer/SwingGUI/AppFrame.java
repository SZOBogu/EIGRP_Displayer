package eigrp_displayer.SwingGUI;

import javax.swing.*;
import java.awt.*;

public class AppFrame extends JFrame {
    private AppPanel appPanel;

    public AppFrame(){
        super("Displayer Main Window");
        this.appPanel = new AppPanel();

        setLayout(new BorderLayout());
        add(new JScrollPane(
                        this.appPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER);
        setMinimumSize(new Dimension(1080, 640));
        setSize(1080, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
