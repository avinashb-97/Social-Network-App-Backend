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

    private List<UserDTO> users;

    public static CourseDTO convertEntityToCourseDTO(Course course)
    {
        CourseDTO courseDTO = new CourseDTO();
        BeanUtils.copyProperties(course, courseDTO);
        if(course.getUsers() != null)
        {
            courseDTO.setUsers(UserDTO.convertEntityListToUserDTOList(course.getUsers()));
        }
        return courseDTO;
    }

    public static List<CourseDTO> convertEntityListToCourseDTOList(Set<Course> courseSet)
    {
        List<CourseDTO> courseDTOList = new ArrayList<>();
        if(courseSet == null || courseSet.isEmpty())
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
