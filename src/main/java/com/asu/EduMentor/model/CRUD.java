package com.asu.EduMentor.model;

import java.util.List;

public interface CRUD {

    Object create();
    Object update(Object updatedObject);
    Object read(int id);
    List<Object> readAll();
    boolean delete(int id);

}
