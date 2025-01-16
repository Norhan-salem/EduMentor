package com.asu.EduMentor.controller.rest;


import com.asu.EduMentor.controller.rest.body.SortBody;
import com.asu.EduMentor.controller.rest.mentorSorter.strategy.MentorSorter;
import com.asu.EduMentor.controller.rest.mentorSorter.strategy.NameSortingStrategy;
import com.asu.EduMentor.controller.rest.mentorSorter.strategy.TotalHoursSortingStrategy;
import com.asu.EduMentor.controller.rest.response.MentorDTO;
import com.asu.EduMentor.model.Mentor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SortController {

    MentorSorter mentorSorter = new MentorSorter();

    @PostMapping("/sort")
    public ResponseEntity<List<MentorDTO>> sortMentors(@RequestBody SortBody sortBody) {
        List<Mentor> mentors = sortBody.getMentors();
        String sortMethod = sortBody.getSortMethod();
        // Set the sorting strategy based on the sortMethod parameter
        if ("alphabetical".equalsIgnoreCase(sortMethod)) {
            mentorSorter.setSortingStrategy(new NameSortingStrategy());
        } else if ("hours".equalsIgnoreCase(sortMethod)) {
            mentorSorter.setSortingStrategy((new TotalHoursSortingStrategy()));
        } else {
            return ResponseEntity.badRequest().body(null); // Return 400 for invalid sortMethod
        }

        // Sort the mentors
        List<Mentor> sortedMentors = mentorSorter.executeSort((ArrayList<Mentor>) mentors);
        List<MentorDTO> sortedMentorDTOs = MentorDTO.fromMentors(sortedMentors);

        // Return the sorted list of mentors
        return ResponseEntity.ok(sortedMentorDTOs);
    }

}
