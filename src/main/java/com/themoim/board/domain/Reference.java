package com.themoim.board.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.themoim.common.converter.BooleanToYNConverter;
import com.themoim.common.listener.DefaultListener;
import com.themoim.user.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Convert(converter= BooleanToYNConverter.class, attributeName = "isDeleted")
//@EntityListeners(DefaultListener.class)
public class Reference extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="r_id", nullable = false)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="written_by")
    private Account writtenBy;

    @Column(name="is_deleted", columnDefinition = "VARCHAR(1) default false")
    //@Convert(converter = BooleanToYNConverter.class)
    private Boolean isDeleted;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reference")
    @JsonManagedReference
    private List<ReferenceFileLink> referenceFileLink = new ArrayList<>();

}
