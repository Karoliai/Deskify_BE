package org.deskify.repository;

import org.deskify.model.domain.dtoUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<dtoUser, Long> {
    List<dtoUser> findAll();
    List<dtoUser> findAllById(Long id);
    List<dtoUser> findAllByEmail(String email);
    List<dtoUser> findAllByFirstName(String firstName);
    List<dtoUser> findAllByLastName(String lastName);
    List<dtoUser> findAllByUsername(String username);
    dtoUser findUserById(Long id);
    Optional<dtoUser> findUserByEmail(String email);
}
