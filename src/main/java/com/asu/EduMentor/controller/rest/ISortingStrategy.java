package com.asu.EduMentor.controller.rest;

import com.asu.EduMentor.model.Mentor;

import java.util.ArrayList;

public interface ISortingStrategy {
    public ArrayList<Mentor> sort(ArrayList<Mentor> mentors);
}
