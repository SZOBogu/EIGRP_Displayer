package eigrp_displayer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/displayer")
public class ViewController {
    List<DeviceController> createdDeviceControllers = new ArrayList<>();
    List<Connection> createdConnections = new ArrayList<>();

    @RequestMapping("/")
    public String index(){
        return "index";
    }
    @GetMapping("/netForm")
    public String showNetForm(@ModelAttribute("network") Network network, Model model){
        return "networkForm";
    }

    @PostMapping("/processNetForm")
    public String processNetForm(@ModelAttribute("network") Network network, Model model){
        return "index";
    }

    @GetMapping("/deviceForm")
    public String showDeviceForm(@ModelAttribute("device") EndDevice device, Model model){
        return "device";
    }

    @PostMapping("/processDeviceForm")
    public String processDeviceForm(@ModelAttribute("device") EndDevice device, Model model){
        return "index";
    }

    @GetMapping("/routerForm")
    public String showRouterForm(@ModelAttribute("router") Router router, Model model){
        return "router";
    }

    @PostMapping("/processRouterForm")
    public String processRouterForm(@ModelAttribute("router") Router router, Model model){
        return "index";
    }

    @GetMapping("/extNetworkForm")
    public String showExtNetworkForm(@ModelAttribute("extNetwork") ExternalNetwork externalNetwork, Model model){
        return "externalNetwork";
    }

    @PostMapping("/processExtNetworkForm")
    public String processExtNetworkForm(@ModelAttribute("extNetwork") ExternalNetwork externalNetwork, Model model){
        return "index";
    }

    @GetMapping("/connectionForm")
    public String showConnectionForm(@ModelAttribute("connection") Cable connection, Model model){
        return "connection";
    }

    @PostMapping("/processConnectionForm")
    public String processConnectionForm(@ModelAttribute("connection") Cable connection, Model model){
        return "index";
    }

    @RequestMapping("/display")
    public String display(){
        return "display";
    }
}
