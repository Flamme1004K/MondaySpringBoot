package com.themoim.board.repository;

import com.themoim.board.domain.ReferenceFileLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReferenceFileLinkRepository extends JpaRepository<ReferenceFileLink, Long> {
}
