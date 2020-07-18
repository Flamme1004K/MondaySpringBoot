package com.themoim.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class FileLinkDTO {

    @Getter
    @NoArgsConstructor
    public static class Resp {
        String linkName;
        String linkDomain;

        @Builder
        public Resp(String linkName, String linkDomain) {
            this.linkName = linkName;
            this.linkDomain = linkDomain;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Req{
        private long id;
        private String linkName;
        private String linkDomain;

        @Builder
        public Req(long id, String linkName, String linkDomain) {
            this.id = id;
            this.linkName = linkName;
            this.linkDomain = linkDomain;
        }
    }
}
