package eigrp_displayer.SwingGUI;

import eigrp_displayer.RouterController;

import javax.swing.*;
import java.awt.*;

public class RouterForm extends JPanel {
    private JCheckBox k1checkbox;
    private JCheckBox k2checkbox;
    private JCheckBox k3checkbox;
    private JCheckBox k4checkbox;
    private JCheckBox k5checkbox;

    public RouterForm(RouterController controller){
        this.k1checkbox = new JCheckBox("K1: ", controller.getDevice().isK1());
        this.k2checkbox = new JCheckBox("K2: ", controller.getDevice().isK2());
        this.k3checkbox = new JCheckBox("K3: ", controller.getDevice().isK3());
        this.k4checkbox = new JCheckBox("K4: ", controller.getDevice().isK4());
        this.k5checkbox = new JCheckBox("K5: ", controller.getDevice().isK5());

        this.layoutComponents();
    }

    public boolean getK1(){
        return this.k1checkbox.isSelected();
    }

    public boolean getK2(){
        return this.k2checkbox.isSelected();
    }

    public boolean getK3(){
        return this.k3checkbox.isSelected();
    }

    public boolean getK4(){
        return this.k4checkbox.isSelected();
    }

    public boolean getK5(){
        return this.k5checkbox.isSelected();
    }

    private void layoutComponents(){
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy = 0;
        gbc.gridx = 0;

        add(k1checkbox, gbc);
        gbc.gridx++;
        add(k2checkbox, gbc);
        gbc.gridx++;
        add(k3checkbox, gbc);
        gbc.gridx++;
        add(k4checkbox, gbc);
        gbc.gridx++;
        add(k5checkbox, gbc);
        gbc.gridx++;
    }
}
