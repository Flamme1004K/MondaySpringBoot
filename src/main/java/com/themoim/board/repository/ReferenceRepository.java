package com.themoim.board.repository;

import com.themoim.board.domain.Reference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ReferenceRepository extends JpaRepository<Reference, Long> {

    @Override
    @Query("select m, a from Reference m join Account a on m.writtenBy.id = a.id")
    List<Reference> findAll();
    //? DTO용 Repository도 만드는 경우도 있으띾?
}
