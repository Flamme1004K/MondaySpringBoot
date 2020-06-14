package com.themoim.board.dto;

import com.themoim.board.domain.Reference;
import com.themoim.board.domain.ReferenceFileLink;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReferenceDto {

    private long writtenId;
    private String title;
    private String content;
    private String Link;

    public Reference toEntity(long userId){

        Reference reference = Reference.builder()
                                        .writtenBy(userId)
                                        .title(title)
                                        .content(content)
                                        .build();
        return reference;
    }

}
