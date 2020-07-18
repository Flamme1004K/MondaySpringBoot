package com.themoim.board.dto;

import com.themoim.board.domain.Reference;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReferenceRespDto {

    private long writeNo;
    private String writeName;
    private String title;

    public ReferenceRespDto(Reference ref) {
       this.writeNo = ref.getId();
       this.writeName = ref.getWrittenBy().getUsername();
       this.title = ref.getTitle();
    }


}
