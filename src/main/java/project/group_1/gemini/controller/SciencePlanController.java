package project.group_1.gemini.controller;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.gemini.app.ocs.OCS;
import edu.gemini.app.ocs.example.MySciencePlan;
import edu.gemini.app.ocs.model.DataProcRequirement;
import edu.gemini.app.ocs.model.SciencePlan;

import edu.gemini.app.ocs.model.*;
import org.springframework.ui.Model;

@Controller
public class SciencePlanController {

    OCS ocs = new OCS(false);

    @GetMapping("/all")
    public @ResponseBody Iterable<SciencePlan> getAllSciplan() {
        
        return ocs.getAllSciencePlans();
    }

    @GetMapping("/Astrodashboard")
    public String displayAllSciplan(Model model) {
    
        model.addAttribute("sciplan", ocs.getAllSciencePlans());

        return "/dashboard/Astronomer_screen";
    }

    @GetMapping("/createSciencePlan")
    public String createSciencePlanForm(Model model) {
        model.addAttribute("constellations", StarSystem.CONSTELLATIONS.values());
        model.addAttribute("telescopeLocations", SciencePlan.TELESCOPELOC.values());
        model.addAttribute("createSciencePlan", new SciencePlan());
        return "/Form_Input/createSciencePlan";
    }

    @PostMapping("/scienceplan")
    public ModelAndView createSciencePlan(@RequestParam Map<String, Object> body) {
        System.out.println(body.toString());
        DateTimeFormatter oldFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime startDate = LocalDateTime.parse(body.get("startDate").toString(), oldFormat);
        LocalDateTime endDate = LocalDateTime.parse(body.get("endDate").toString(), oldFormat);

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        MySciencePlan mySP = new MySciencePlan();
        mySP.setCreator(body.get("creator").toString());
        mySP.setSubmitter(body.get("submitter").toString());
        mySP.setFundingInUSD(Double.parseDouble(body.get("funding").toString()));
        mySP.setObjectives(body.get("objective").toString());
        mySP.setStarSystem(StarSystem.CONSTELLATIONS.valueOf(body.get("starSystem").toString()));
        mySP.setStartDate(startDate.format(format));
        mySP.setTelescopeLocation(SciencePlan.TELESCOPELOC.valueOf(body.get("location").toString()));
        mySP.setEndDate(endDate.format(format));
        DataProcRequirement dpr = new DataProcRequirement(body.get("fileType").toString(),
                body.get("fileQuality").toString(), body.get("colorType").toString(),
                Integer.parseInt(body.get("contrast").toString()), Integer.parseInt(body.get("brightness").toString()),
                Integer.parseInt(body.get("saturation").toString()),
                Integer.parseInt(body.get("highlights").toString()), Integer.parseInt(body.get("exposure").toString()),
                Integer.parseInt(body.get("shadows").toString()), Integer.parseInt(body.get("whites").toString()),
                Integer.parseInt(body.get("blacks").toString()), Integer.parseInt(body.get("luminance").toString()),
                Integer.parseInt(body.get("hue").toString()));
        mySP.setDataProcRequirements(dpr);
        ocs.createSciencePlan(mySP);
        return new ModelAndView("redirect:/Astrodashboard");
    }
}
