package eigrp_displayer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/displayer")
public class ViewController {

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
    public String showDeviceForm(@ModelAttribute("device") Device device, Model model){
        return "deviceForm";
    }

    @PostMapping("/processDeviceForm")
    public String processDeviceForm(@ModelAttribute("device") Device device, Model model){
        return "index";
    }

    @GetMapping("/connectionForm")
    public String showConnectionForm(@ModelAttribute("connection") Connection connection, Model model){
        return "connectionForm";
    }

    @PostMapping("/processConnectionForm")
    public String processConnectionForm(@ModelAttribute("connection") Connection connection, Model model){
        return "index";
    }

    @RequestMapping("/display")
    public String display(){
        return "display";
    }
}
