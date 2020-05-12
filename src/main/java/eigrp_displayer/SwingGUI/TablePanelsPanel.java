package eigrp_displayer.SwingGUI;

import eigrp_displayer.DeviceController;
import eigrp_displayer.MessageScheduler;
import eigrp_displayer.RouterController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TablePanelsPanel extends JPanel implements Refreshable{
    private List<TablesPanel> tablePanels;

    public TablePanelsPanel(){
        this.tablePanels = new ArrayList<>();
        List<DeviceController> controllers = MessageScheduler.getInstance().getNetwork().getDeviceControllers();
        for(int i = 0; i < controllers.size(); i++){
            if(controllers.get(i) instanceof RouterController){
                this.tablePanels.add(new TablesPanel((RouterController)controllers.get(i)));
            }
        }
        this.layoutComponents();
    }

    private void layoutComponents() {
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy = 0;
        gbc.gridx = 0;
        for(TablesPanel tablesPanel : this.tablePanels){
            add(tablesPanel, gbc);
            gbc.gridy++;
        }
    }

    @Override
    public void refresh() {
        for(TablesPanel panel : this.tablePanels){
            panel.refresh();
        }
    }
}
