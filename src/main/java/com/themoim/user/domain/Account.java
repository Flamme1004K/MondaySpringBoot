package com.themoim.user.domain;


import com.themoim.board.domain.Reference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    private Date createdAt;

    @Column(name="lastLogin_at")
    private Date lastLoginAt;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="written_by")
    private List<Reference> referencesList;

}
