package bccontrol;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PagesController {
	@RequestMapping("/")
	public String showLandingPage(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
		model.addAttribute("name", name);
		return "index";
	}

	@RequestMapping("/filedata")
	public String showFileDataPage(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
		model.addAttribute("name", name);
		return "filedata";
	}

	@RequestMapping("/offchain")
	public String showOffChainDataPage(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
		model.addAttribute("name", name);
		return "offchain";
	}

	@RequestMapping("/audittrail")
	public String showAuditTrailPage(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
		model.addAttribute("name", name);
		return "audittrail";
	}
}
