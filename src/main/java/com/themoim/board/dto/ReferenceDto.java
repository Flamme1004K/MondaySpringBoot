package com.themoim.board.dto;

import com.themoim.board.domain.Reference;
import com.themoim.board.domain.ReferenceFileLink;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReferenceDto {

    private long writtenId;
    private String title;
    private String content;
    private List<String> Link;

    public Reference toEntity(long userId){

        Reference reference = Reference.builder()
                                        .writtenBy(userId)
                                        .title(title)
                                        .content(content)
                                        .isDeleted(false)
                                        .build();
        return reference;
    }

}
