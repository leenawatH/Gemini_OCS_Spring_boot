package project.group_1.gemini.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import edu.gemini.app.ocs.OCS;
import edu.gemini.app.ocs.model.SciencePlan;

@Controller
public class ScheDuleController {
    OCS ocs = new OCS(false);
    
    ArrayList<SciencePlan> sciencePlans = ocs.getAllSciencePlans();


    @GetMapping("addSchedule")
    // @ResponseBody
    public String Showform(Model model) {

        return "/Form_Input/addSchedule";
    }

    @PostMapping("/addDate")
    // @ResponseBody
    public String addNewDate(@RequestParam("date") String date) throws ParseException {

        String num[] = date.split("-");

        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(num[0]), (Integer.parseInt(num[1]) - 1), (Integer.parseInt(num[2])));
        Date date1 = cal.getTime();
        ocs.addUnavailableDate(date1);
        return "redirect:/getAllschedule";

    }

    // @GetMapping("getAllschedule")
    // // @ResponseBody
    // public String showSchedule() {

    // return "getAllschedule";
    // }

    // @GetMapping("/all")
    // public @ResponseBody ArrayList<Date> getAllschedule() {

    // return ocs.getAllObservationSchedule();

    // }

    @GetMapping("/AddNew")
    // @ResponseBody
    public String addNew(Model model) {

        return "addSchedule";
    }

    @GetMapping("/getAllschedule")
    // @ResponseBody
    public String getAllschedule(Model model) {
        ArrayList<Date> dates = ocs.getAllObservationSchedule();
        model.addAttribute("dates", dates);
        return "/dashboard/Admin_screen";
    }

    @GetMapping("/delete/{id}")
    // @ResponseBody
    public String deleteDate(@PathVariable(name = "id") String date) {
        // Calendar cal = Calendar.getInstance();
        // cal.set(2023, 11, 21);
        // Date date = cal.getTime();
        String num[] = date.split("-");

        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(num[0]), (Integer.parseInt(num[1]) - 1), (Integer.parseInt(num[2])));
        Date date1 = cal.getTime();

        // return ocs.deleteUnavailableDate(date);
        ocs.deleteUnavailableDate(date1);
        return "redirect:/getAllschedule";
    }

}
