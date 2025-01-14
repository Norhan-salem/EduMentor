package com.asu.EduMentor.controller.rest;


import com.asu.EduMentor.controller.rest.body.SortBody;
import com.asu.EduMentor.controller.rest.mentorSorter.strategy.MentorSorter;
import com.asu.EduMentor.controller.rest.mentorSorter.strategy.NameSortingStrategy;
import com.asu.EduMentor.controller.rest.mentorSorter.strategy.TotalHoursSortingStrategy;
import com.asu.EduMentor.model.Mentor;
import com.asu.EduMentor.model.User;
import com.asu.EduMentor.model.UserType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SortController {

    MentorSorter mentorSorter;

    @PostMapping("/sort")
    public ResponseEntity<List<Mentor>> sortMentors(@RequestBody SortBody sortBody) {
        List<Mentor> mentors = sortBody.getMentors();
        String sortMethod = sortBody.getSortMethod();
        // Set the sorting strategy based on the sortMethod parameter
        if ("name".equalsIgnoreCase(sortMethod)) {
            mentorSorter.setSortingStrategy(new NameSortingStrategy());
        } else if ("total_hours".equalsIgnoreCase(sortMethod)) {
            mentorSorter.setSortingStrategy((new TotalHoursSortingStrategy()));
        } else {
            return ResponseEntity.badRequest().body(null); // Return 400 for invalid sortMethod
        }

        // Sort the mentors
        List<Mentor> sortedMentors = mentorSorter.executeSort((ArrayList<Mentor>) mentors);

        // Return the sorted list of mentors
        return ResponseEntity.ok(sortedMentors);
    }

}
