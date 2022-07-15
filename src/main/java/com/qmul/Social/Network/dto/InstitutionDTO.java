package com.qmul.Social.Network.dto;

import com.qmul.Social.Network.model.Instituion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstitutionDTO {

    private long id;

    private String name;

    private String adminMail;

    private String code;

    public static InstitutionDTO convertEntityToInstitutionDTO(Instituion instituion)
    {
        InstitutionDTO institutionDTO = new InstitutionDTO();
        BeanUtils.copyProperties(instituion, institutionDTO);
        return institutionDTO;
    }
}
