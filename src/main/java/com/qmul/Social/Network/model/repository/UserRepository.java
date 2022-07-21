package com.qmul.Social.Network.model.repository;


import com.qmul.Social.Network.model.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findUserByEmailIgnoreCase(String email);

    public boolean existsUserByEmailIgnoreCase(String email);

}
