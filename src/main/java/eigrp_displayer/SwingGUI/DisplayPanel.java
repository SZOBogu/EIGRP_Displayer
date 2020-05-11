package eigrp_displayer.SwingGUI;

import javax.swing.*;
import java.awt.*;

public class DisplayPanel extends JPanel {
    private JLabel emptyLabel;
    private JLabel endDevice0Label;

    private JLabel connection3Label;

    private JLabel connection1Label;
    private JLabel router1Label;
    private JLabel connection4Label;

    private JLabel externalNetworkLabel;
    private JLabel connection0Label;
    private JLabel router0Label;
    private JLabel connection2Label;
    private JLabel router2Label;
    private JLabel connection5Label;
    private JLabel endDevice1Label;

    public DisplayPanel(){
        ImageIcon emptyIcon = new ImageIcon(
                "/home/bogu/NetBeansProjects/EIGRP Displayer/src/main/resources/static/large placeholder.png");
        Image emptyImage = emptyIcon.getImage();
        Image scaledEmptyImage = emptyImage.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);

        ImageIcon extNetIcon = new ImageIcon(
                "/home/bogu/NetBeansProjects/EIGRP Displayer/src/main/resources/static/cloud network.png");
        Image extNetImage = extNetIcon.getImage();
        Image scaledExtNetImage = extNetImage.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);

        ImageIcon deviceIcon = new ImageIcon(
                "/home/bogu/NetBeansProjects/EIGRP Displayer/src/main/resources/static/enddev.png");
        Image deviceImage = deviceIcon.getImage();
        Image scaledDeviceImage = deviceImage.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);

        ImageIcon routerIcon = new ImageIcon(
                "/home/bogu/NetBeansProjects/EIGRP Displayer/src/main/resources/static/router.png");
        Image routerImage = routerIcon.getImage();
        Image scaledRouterImage = routerImage.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);

        ImageIcon vertConnIcon = new ImageIcon(
                "/home/bogu/NetBeansProjects/EIGRP Displayer/src/main/resources/static/vertical connection.png");
        Image verConnImage = vertConnIcon.getImage();
        Image scaledVertConnImage = verConnImage.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);

        ImageIcon horConnIcon = new ImageIcon(
                "/home/bogu/NetBeansProjects/EIGRP Displayer/src/main/resources/static/horizontal connection.png");
        Image horConnImage = horConnIcon.getImage();
        Image scaledHorConnImage = horConnImage.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);

        ImageIcon rightConnIcon = new ImageIcon(
                "/home/bogu/NetBeansProjects/EIGRP Displayer/src/main/resources/static/right turn connection.png");
        Image rightConnImage = rightConnIcon.getImage();
        Image scaledRightConnImage = rightConnImage.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);

        ImageIcon leftConnIcon = new ImageIcon(
                "/home/bogu/NetBeansProjects/EIGRP Displayer/src/main/resources/static/left turn connection.png");
        Image leftConnImage = leftConnIcon.getImage();
        Image scaledLeftConnImage = leftConnImage.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);

        this.emptyLabel = new JLabel(new ImageIcon(scaledEmptyImage));

        this.connection0Label = new JLabel(new ImageIcon(scaledHorConnImage));
        this.connection1Label = new JLabel(new ImageIcon(scaledRightConnImage));
        this.connection2Label = new JLabel(new ImageIcon(scaledHorConnImage));
        this.connection3Label = new JLabel(new ImageIcon(scaledVertConnImage));
        this.connection4Label = new JLabel(new ImageIcon(scaledLeftConnImage));
        this.connection5Label = new JLabel(new ImageIcon(scaledHorConnImage));

        this.externalNetworkLabel = new JLabel(new ImageIcon(scaledExtNetImage));

        this.endDevice0Label = new JLabel(new ImageIcon(scaledDeviceImage));
        this.endDevice1Label = new JLabel(new ImageIcon(scaledDeviceImage));

        this.router0Label = new JLabel(new ImageIcon(scaledRouterImage));
        this.router1Label = new JLabel(new ImageIcon(scaledRouterImage));
        this.router2Label = new JLabel(new ImageIcon(scaledRouterImage));

        this.layoutComponents();
    }

    private void layoutComponents(){
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy = 0;
        gbc.gridx = 0;

        //1 row
        add(emptyLabel, gbc);
        gbc.gridx++;
        add(emptyLabel, gbc);
        gbc.gridx++;
        add(emptyLabel, gbc);
        gbc.gridx++;
        add(endDevice0Label, gbc);
        gbc.gridx++;
        add(emptyLabel, gbc);
        gbc.gridx++;
        add(emptyLabel, gbc);
        gbc.gridx++;
        add(emptyLabel, gbc);
        gbc.gridx++;

        //2 row
        gbc.gridy++;
        gbc.gridx = 0;
        add(emptyLabel, gbc);
        gbc.gridx++;
        add(emptyLabel, gbc);
        gbc.gridx++;
        add(emptyLabel, gbc);
        gbc.gridx++;
        add(connection3Label, gbc);
        gbc.gridx++;
        add(emptyLabel, gbc);
        gbc.gridx++;
        add(emptyLabel, gbc);
        gbc.gridx++;
        add(emptyLabel, gbc);
        gbc.gridx++;

        //3rd row
        gbc.gridy++;
        gbc.gridx = 0;
        add(emptyLabel, gbc);
        gbc.gridx++;
        add(emptyLabel, gbc);
        gbc.gridx++;
        add(connection1Label, gbc);
        gbc.gridx++;
        add(router1Label, gbc);
        gbc.gridx++;
        add(connection4Label, gbc);
        gbc.gridx++;
        add(emptyLabel, gbc);
        gbc.gridx++;
        add(emptyLabel, gbc);
        gbc.gridx++;

        //4th row
        gbc.gridy++;
        gbc.gridx = 0;
        add(externalNetworkLabel, gbc);
        gbc.gridx++;
        add(connection0Label, gbc);
        gbc.gridx++;
        add(router0Label, gbc);
        gbc.gridx++;
        add(connection2Label, gbc);
        gbc.gridx++;
        add(router2Label, gbc);
        gbc.gridx++;
        add(connection5Label, gbc);
        gbc.gridx++;
        add(endDevice1Label, gbc);
        gbc.gridx++;
    }
}