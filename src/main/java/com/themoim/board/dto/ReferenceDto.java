package com.themoim.board.dto;

import com.themoim.board.domain.Reference;
import com.themoim.common.converter.BooleanToYNConverter;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class ReferenceDto {

    private long writtenId;
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
    private List<String> Link = new ArrayList<>();

    public ReferenceDto(long writtenId,String title, String content) {
        this.writtenId = writtenId;
        this.title = title;
        this.content = content;
    }

    public Reference toEntity(long userId){

        Reference reference = Reference.builder()
                                        .writtenBy(userId)
                                        .title(title)
                                        .content(content)
                                        .build();
        return reference;
    }

}
