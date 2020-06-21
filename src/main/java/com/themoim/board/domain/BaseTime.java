package com.themoim.board.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTime {

    @Column(name="create_at")
    @CreatedDate
    private LocalDateTime createAt;

    @Column(name="modified_at")
    @LastModifiedDate
    private LocalDateTime modifiedAt;
}
