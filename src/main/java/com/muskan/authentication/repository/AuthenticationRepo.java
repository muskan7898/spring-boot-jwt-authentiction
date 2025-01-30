package com.muskan.authentication.repository;
import com.muskan.authentication.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationRepo extends JpaRepository<User, Long>{
    boolean existsByUsername(String name);
}
