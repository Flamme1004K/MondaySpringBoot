package com.themoim.board.repository;

import com.themoim.board.domain.Reference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ReferenceRepository extends JpaRepository<Reference, Long> {

    @Override
    @Query("select m,a from Reference m join fetch Account a on m.writtenBy = a.id")
    List<Reference> findAll();
}
