package project.group_1.gemini.controller;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.gemini.app.ocs.OCS;
import edu.gemini.app.ocs.model.ObservingProgram;
import edu.gemini.app.ocs.model.SciencePlan;
import project.group_1.gemini.model.Observing;

@Controller
public class ObservingProgramController {
    OCS ocs = new OCS(false);

    @GetMapping("/allob")
    public @ResponseBody ArrayList<Date> tet() {
        
        return ocs.getAllObservationSchedule();
    }
  
    @GetMapping("/SciObdashboard")
    public String displayAllSciplan(Model model) {
        OCS ocs = new OCS(false);
        ArrayList<SciencePlan> n = ocs.getAllSciencePlans();
        ArrayList<Boolean> check = new ArrayList<Boolean>();
        model.addAttribute("sciplan", ocs.getAllSciencePlans());
        for (SciencePlan a : n) {
            if (ocs.getObservingProgramBySciencePlan(a).getGeminiLocation() == null) {
                check.add(false);
            } else {
                check.add(true);
            }

        }
        model.addAttribute("checkob", check);

        return "/dashboard/Science_Observer_screen";
    }

    @GetMapping("/sentsciplan/{id}")
    public String createSciplan(@PathVariable("id") Integer id, Model model) {

        model.addAttribute("createObservingProgram", ocs.getSciencePlanByNo(id));
        model.addAttribute("datarequirement", ocs.getSciencePlanByNo(id).getDataProcRequirements());
        return "/Form_Input/createObservingProgram";
    }

    @GetMapping("/detailObservingProgram/{id}")
    public String displayDetailObservingProgram(@PathVariable("id") Integer id, Model model) {

        model.addAttribute("sciplan", ocs.getSciencePlanByNo(id));
        model.addAttribute("datarequirement", ocs.getSciencePlanByNo(id).getDataProcRequirements());
        model.addAttribute("observingProgram", ocs.getObservingProgramBySciencePlan(ocs.getSciencePlanByNo(id)));
        model.addAttribute("telePositionPair", ocs.getObservingProgramBySciencePlan(ocs.getSciencePlanByNo(id)).getTelePositionPair());
        model.addAttribute("size", ocs.getObservingProgramBySciencePlan(ocs.getSciencePlanByNo(id)).getTelePositionPair().length);

        System.out.println(ocs.getObservingProgramBySciencePlan(ocs.getSciencePlanByNo(id)).getTelePositionPair());
        return "ObservingProgramDisplay";

    }

    @PostMapping(value = "/sentsciplan/createObservingProgram/create/{id}", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String createOP(@RequestParam Map<String, Object> body , @PathVariable Integer id) {
        
        Observing op = new Observing();
        ObservingProgram newop = op.createObservingProgram(body , id);
        ocs.saveObservingProgram(newop);
        if(ocs.saveObservingProgram(newop)){
            System.out.println(ocs.getObservingProgramBySciencePlan(ocs.getSciencePlanByNo(id)));
            System.out.println(ocs.getObservingProgramBySciencePlan(ocs.getSciencePlanByNo(id)).getTelePositionPair().length);
        }
        
        return "redirect:/SciObdashboard";
    }
}


