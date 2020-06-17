package com.themoim.board.domain;

import com.themoim.common.converter.BooleanToYNConverter;
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
//@Convert(converter= BooleanToYNConverter.class, attributeName = "isDeleted")
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

    @Column(name="written_by", nullable = false)
    private long writtenBy;

    @Column(name="is_deleted", columnDefinition = "VARCHAR(1) default false")
//    @Convert(converter = BooleanToYNConverter.class)
    private Boolean isDeleted;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reference")
    private List<ReferenceFileLink> referenceFileLink;


}
