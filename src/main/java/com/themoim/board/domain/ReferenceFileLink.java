package com.themoim.board.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReferenceFileLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="rfl_id", nullable = false)
    private long id;

    @Column(nullable = false)
    private String link;

    @ManyToOne
    @JoinColumn(name = "r_id")
    @JsonBackReference
    private Reference reference;
}
