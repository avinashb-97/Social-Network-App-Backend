package com.qmul.Social.Network.dto;


import com.qmul.Social.Network.model.persistence.Course;
import com.qmul.Social.Network.model.persistence.Department;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class CourseDTO {

    private long id;

    private String name;

    private Date createdTime;

    public static CourseDTO convertEntityToCourseDTO(Course course)
    {
        CourseDTO courseDTO = new CourseDTO();
        BeanUtils.copyProperties(course, courseDTO);
        return courseDTO;
    }

    public static List<CourseDTO> convertEntityListToCourseDTOList(Set<Course> courseSet)
    {
        List<CourseDTO> courseDTOList = new ArrayList<>();
        if(courseSet.isEmpty())
        {
            return courseDTOList;
        }
        for(Course course : courseSet)
        {
            courseDTOList.add(convertEntityToCourseDTO(course));
        }
        Collections.sort(courseDTOList, (a, b) -> a.getCreatedTime().compareTo(b.getCreatedTime()));
        return courseDTOList;
    }
}
