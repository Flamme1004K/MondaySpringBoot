package com.themoim.common.listener;

import javax.persistence.PostPersist;
import javax.persistence.PrePersist;

public class DefaultListener {
    @PrePersist
    private void prePersist(Object obj) {
        System.out.println("prePersist = "+obj.toString());
    }
    @PostPersist
    private void postPersist(Object obj) {
        System.out.println("postPersist = "+obj.toString());
    }
}
