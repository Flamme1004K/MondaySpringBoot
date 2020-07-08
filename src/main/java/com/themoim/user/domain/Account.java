package com.themoim.user.domain;


import com.themoim.board.domain.Reference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="a_id" , nullable = false)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(name="user_id", nullable = false)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    private String phoneNumber;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="lastLogin_at")
    private LocalDateTime lastLoginAt;

}
