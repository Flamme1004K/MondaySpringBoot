package com.themoim.board.service;

import com.themoim.board.domain.Reference;
import com.themoim.board.domain.ReferenceFileLink;
import com.themoim.board.dto.ReferenceDto;
import com.themoim.board.repository.ReferenceFileLinkRepository;
import com.themoim.board.repository.ReferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ReferenceService {

    @Autowired
    ReferenceRepository referenceRepository;
    @Autowired
    ReferenceFileLinkRepository referenceFileLinkRepository;

    public void saveRepository(Long userId, ReferenceDto referenceDto){
        long referenceId =  referenceRepository.save(referenceDto.toEntity(userId)).getId();
        if(referenceDto.getLink().length()>0  && referenceDto.getLink() != null){
            ReferenceFileLink referenceFileLink = ReferenceFileLink.builder().rId(referenceId).link(referenceDto.getLink()).build();
            referenceFileLinkRepository.save(referenceFileLink);
        }
    }

}
