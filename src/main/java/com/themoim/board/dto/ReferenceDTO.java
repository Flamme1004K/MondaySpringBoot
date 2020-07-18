package com.themoim.board.dto;

import com.themoim.board.domain.Reference;
import com.themoim.board.domain.ReferenceFileLink;
import com.themoim.common.converter.BooleanToYNConverter;
import com.themoim.user.domain.Account;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class ReferenceDTO {

        @Getter
        @NoArgsConstructor
        public static class ListResp {
            private Long no;
            private String title;
            private String writtenName;
            private LocalDateTime createDate;

            public ListResp(Reference reference) {
                this.no = reference.getId();
                this.title = reference.getTitle();
                this.writtenName = reference.getWrittenBy().getUsername();
                this.createDate = reference.getCreateAt();
            }
        }

        @Getter
        @NoArgsConstructor
        public static class Resp {

            private long writtenId;
            private String title;
            private String content;
            private List<FileLinkDTO.Resp> file = new ArrayList<>();

            public Resp(long writtenId, String title, String content, List<FileLinkDTO.Resp> file) {
                this.writtenId = writtenId;
                this.title = title;
                this.content = content;
                this.file = file;
            }
        }

        @Getter
        @NoArgsConstructor
        public static class Req {
            private long writtenId;
            private String title;
            private String content;
            private List<FileLinkDTO.Req> file = new ArrayList<>();

            @Builder
            public Req(long writtenId, String title, String content, List<FileLinkDTO.Req> file) {
                this.writtenId = writtenId;
                this.title = title;
                this.content = content;
                this.file = file;
            }

            public Reference toEntity(Account account){
                Reference reference = Reference.builder()
                        .writtenBy(account)
                        .title(title)
                        .content(content)
                        .build();
                return reference;

            }
        }

}
