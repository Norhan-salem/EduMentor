package com.asu.EduMentor.controller.rest.mentorSorter.strategy;

import com.asu.EduMentor.model.Mentor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SortByTotalHours implements ISortingStrategy {
    @Override
    public ArrayList<Mentor> sort(ArrayList<Mentor> mentors) {
        Collections.sort(mentors, Comparator.comparingDouble(Mentor::getTotalHours).reversed());
        return mentors;
    }
}
