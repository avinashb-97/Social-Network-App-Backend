package com.qmul.Social.Network.model.repository;

import com.qmul.Social.Network.model.Instituion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstitutionRepository extends JpaRepository<Instituion, String> {

    public Optional<Instituion> findInstituionByAdminMail(String email);

    public Optional<Instituion> findInstituionByCode(String code);

    public boolean existsInstituionByName(String institutionName);

}
