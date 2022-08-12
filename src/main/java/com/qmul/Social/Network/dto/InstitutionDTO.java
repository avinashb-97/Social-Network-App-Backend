package com.qmul.Social.Network.dto;

import com.qmul.Social.Network.model.persistence.Institution;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstitutionDTO {

    private long id;

    private String name;

    private String adminMail;

    private String code;

    private List<DepartmentDTO> departments;

    public static InstitutionDTO convertEntityToInstitutionDTO(Institution institution)
    {
        InstitutionDTO institutionDTO = new InstitutionDTO();
        BeanUtils.copyProperties(institution, institutionDTO);
        if(institution.getDepartments() != null)
        {
            institutionDTO.setDepartments(DepartmentDTO.convertEntityListToDepartmentDTOList(institution.getDepartments()));
        }
        return institutionDTO;
    }

    public static List<InstitutionDTO> convertEntityListToInstitutionDTOList(List<Institution> institutions)
    {
        List<InstitutionDTO> institutionDTOS = new ArrayList<>();
        for(Institution institution : institutions)
        {
            institutionDTOS.add(convertEntityToInstitutionDTO(institution));
        }
        return institutionDTOS;
    }
}
