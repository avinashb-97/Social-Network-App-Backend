package com.qmul.Social.Network.dto;

import com.qmul.Social.Network.model.persistence.Institution;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstitutionDTO {

    private long id;

    private String name;

    private String adminMail;

    private String code;

    public static InstitutionDTO convertEntityToInstitutionDTO(Institution institution)
    {
        InstitutionDTO institutionDTO = new InstitutionDTO();
        BeanUtils.copyProperties(institution, institutionDTO);
        return institutionDTO;
    }
}
