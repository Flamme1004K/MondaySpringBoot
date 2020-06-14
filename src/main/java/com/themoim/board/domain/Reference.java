package com.themoim.board.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reference extends AllDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="r_id", nullable = false)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Lob
    private String content;

    @Column(name="written_by", nullable = false)
    private long writtenBy;

    @Column(name="is_deleted")
    private String isDeleted;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="r_id")
    private ReferenceFileLink fileLinks;


}
