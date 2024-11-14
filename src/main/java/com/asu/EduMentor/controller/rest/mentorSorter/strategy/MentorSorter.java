package com.asu.EduMentor.controller.rest.mentorSorter.strategy;

import com.asu.EduMentor.model.Mentor;

import java.util.ArrayList;

public class MentorSorter {
    ISortingStrategy sortingStrategy;

    public void setSortingStrategy(ISortingStrategy sortingStrategy){
        this.sortingStrategy = sortingStrategy;
    }

    public ArrayList<Mentor> executeSort(ArrayList<Mentor> mentors){
        return this.sortingStrategy.sort(mentors);
    }
}
