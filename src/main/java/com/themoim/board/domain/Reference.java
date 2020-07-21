package com.themoim.board.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.themoim.board.dto.ReferenceDTO;
import com.themoim.user.domain.Account;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="a_id")
    private Account writtenBy;

    @Column(name="is_deleted", columnDefinition = "VARCHAR(1) default false")
    //@Convert(converter = BooleanToYNConverter.class)
    private Boolean isDeleted;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reference")
    private List<ReferenceFileLink> referenceFileLink = new ArrayList<>();


    /*

    since : 2020/07/13
    업데이트 구문 ->

    */
    public void update(ReferenceDTO.Req req) {
        this.title = req.getTitle();
        this.content = req.getContent();
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
