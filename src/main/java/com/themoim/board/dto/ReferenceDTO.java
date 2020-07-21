package com.themoim.board.dto;

import com.themoim.board.domain.Reference;
import com.themoim.board.domain.ReferenceFileLink;
import com.themoim.common.converter.BooleanToYNConverter;
import com.themoim.user.domain.Account;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class ReferenceDTO {

        @Getter
        @NoArgsConstructor
        @ToString
        public static class ListResp {
            private Long no;
            private String title;
            private String writtenName;
            private LocalDateTime createDate;


            @Builder
            public ListResp(Long no, String title, String writtenName, LocalDateTime createDate ) {
                this.no = no;
                this.title = title;
                this.writtenName = writtenName;
                this.createDate = createDate;
            }



            /*
            public ListResp(Reference reference) {
                this.no = reference.getId();
                this.title = reference.getTitle();
                this.writtenName = reference.getWrittenBy().getUsername();
                this.createDate = reference.getCreateAt();
            }

             */
        }


        /*static */
        @Getter
        @NoArgsConstructor
        public static class Resp {
            private long boardNo;
            private String writtenName;
            private String title;
            private String content;
            private List<FileLinkDTO.Resp> file = new ArrayList<>();

            @Builder
            public Resp(long boardNo, String writtenName, String title, String content, List<FileLinkDTO.Resp> file) {
                this.boardNo = boardNo;
                this.writtenName = writtenName;
                this.title = title;
                this.content = content;
                this.file = file;
            }
        }

        @Getter
        @NoArgsConstructor
        public static class Req {
            private long writtenId;

            @NotBlank(message = "제목을 입력해주세요.")
            @Size(max=50, message = "글자수는 50글자 밑으로 써주세요.")
            private String title;

            @NotBlank(message = "내용을 입력해주세요.")
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
