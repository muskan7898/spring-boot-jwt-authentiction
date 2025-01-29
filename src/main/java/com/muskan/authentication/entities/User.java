package com.muskan.authentication.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(nullable = false)
    private String password;
}
