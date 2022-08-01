package com.qmul.Social.Network.exception;

import com.qmul.Social.Network.model.persistence.Course;

public class CourseNotFoundException extends RuntimeException{

    public CourseNotFoundException()
    {
        super();
    }

    public CourseNotFoundException(String message)
    {
        super(message);
    }


}
