package io.lilo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import io.lilo.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    //@Query("SELECT u from User u WHERE u.email = :email")
    User findByEmail(@Param("email") String testEmail);
}
