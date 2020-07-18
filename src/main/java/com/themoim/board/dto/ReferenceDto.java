package com.themoim.board.dto;

import com.themoim.board.domain.Reference;
import com.themoim.board.domain.ReferenceFileLink;
import com.themoim.common.converter.BooleanToYNConverter;
import com.themoim.user.domain.Account;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class ReferenceDto {

        @Getter
        @NoArgsConstructor
        public static class Resp {
            private long writtenId;
            private String title;
            private String content;
            private List<FileLinkDTO.Resp> file = new ArrayList<>();

            @Builder
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
