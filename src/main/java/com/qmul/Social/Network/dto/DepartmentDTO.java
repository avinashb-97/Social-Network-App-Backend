package com.qmul.Social.Network.dto;


import com.qmul.Social.Network.model.persistence.Department;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class DepartmentDTO {

    private long id;

    private String name;

    public static DepartmentDTO convertEntityToDepartmentDTO(Department department)
    {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        BeanUtils.copyProperties(department, departmentDTO);
        return departmentDTO;
    }

    public static List<DepartmentDTO> convertEntityListToDepartmentDTOList(Set<Department> departmentSet)
    {
        List<DepartmentDTO> departmentDTOList = new ArrayList<>();
        for(Department department : departmentSet)
        {
            departmentDTOList.add(convertEntityToDepartmentDTO(department));
        }
        return departmentDTOList;
    }
}
