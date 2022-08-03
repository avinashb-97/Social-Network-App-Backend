package com.qmul.Social.Network.model.repository;

import com.qmul.Social.Network.model.persistence.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {

    public Optional<Institution> findInstituionByCode(String code);

    public boolean existsInstituionByName(String institutionName);

}
