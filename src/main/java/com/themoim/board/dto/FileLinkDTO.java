package com.themoim.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class FileLinkDTO {

    @Getter
    @NoArgsConstructor
    public static class Resp {
        private long id;
        String linkDomain;

        @Builder
        public Resp(long id, String linkDomain) {
            this.id = id;
            this.linkDomain = linkDomain;
        }

    }

    @Getter
    @NoArgsConstructor
    public static class Req{
        private long id;
        private String linkDomain;

        @Builder
        public Req(long id, String linkDomain) {
            this.id = id;
            this.linkDomain = linkDomain;
        }
    }
}
