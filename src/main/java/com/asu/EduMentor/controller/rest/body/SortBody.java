package com.asu.EduMentor.controller.rest.body;

import com.asu.EduMentor.model.Mentor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SortBody {

    String sortMethod;
    List<Mentor> mentors;
}
