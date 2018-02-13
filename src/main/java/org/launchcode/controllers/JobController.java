package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job jobs = jobData.findById(id);
        model.addAttribute("jobs", jobs);
        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        if (errors.hasErrors())
            return "new-job";

            Employer newEmployer = null;
            for (int i = 0; i < jobForm.getEmployers().size(); i++) {
                if (jobForm.getEmployers().get(i).getId() == jobForm.getEmployerId()) {
                    newEmployer = jobForm.getEmployers().get(i);
                    break;
                }
            }

            Location newLocation = null;

            for (int i = 0; i < jobForm.getLocations().size(); i++) {
                if (Objects.equals(jobForm.getLocations().get(i).getValue(), jobForm.getLocation())) {
                    newLocation = jobForm.getLocations().get(i);
                    break;
                }

            }
            PositionType newPositionType = null;
            for (int i = 0; i < jobForm.getPositionTypes().size(); i++) {
                if (Objects.equals(jobForm.getPositionTypes().get(i).getValue(), jobForm.getPositionType())) {
                    newPositionType = jobForm.getPositionTypes().get(i);
                    break;
                }
            }

            CoreCompetency newCoreCompetency = null;
            for (int i = 0; i < jobForm.getCoreCompetencies().size(); i++) {
                if (Objects.equals(jobForm.getCoreCompetencies().get(i).getValue(), jobForm.getCoreCompetency())) {
                    newCoreCompetency = jobForm.getCoreCompetencies().get(i);
                    break;
                }
            }

            Job newJob = new Job(jobForm.getName(), newEmployer, newLocation, newPositionType, newCoreCompetency);
            jobData.add(newJob);
            model.addAttribute(newJob);
            return "redirect:/";


    }
    }







