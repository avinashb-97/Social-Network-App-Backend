package com.qmul.Social.Network.dto;


import com.mysql.cj.xdevapi.Collection;
import com.qmul.Social.Network.model.persistence.Department;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class DepartmentDTO {

    private long id;

    private String name;

    private Date createdTime;

    private List<CourseDTO> courses;

    public static DepartmentDTO convertEntityToDepartmentDTO(Department department)
    {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        BeanUtils.copyProperties(department, departmentDTO);
        departmentDTO.setCourses(CourseDTO.convertEntityListToCourseDTOList(department.getCourses()));
        return departmentDTO;
    }

    public static List<DepartmentDTO> convertEntityListToDepartmentDTOList(Set<Department> departmentSet)
    {
        List<DepartmentDTO> departmentDTOList = new ArrayList<>();
        if(departmentSet.isEmpty())
        {
            return departmentDTOList;
        }
        for(Department department : departmentSet)
        {
            departmentDTOList.add(convertEntityToDepartmentDTO(department));
        }
        Collections.sort(departmentDTOList, (a,b) -> a.getCreatedTime().compareTo(b.getCreatedTime()));
        return departmentDTOList;
    }
}
