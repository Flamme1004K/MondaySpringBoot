package com.themoim.board.service;

import com.themoim.board.domain.ReferenceFileLink;
import com.themoim.board.dto.ReferenceDto;
import com.themoim.board.repository.ReferenceFileLinkRepository;
import com.themoim.board.repository.ReferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReferenceService {

    private final ReferenceRepository referenceRepository;
    private final ReferenceFileLinkRepository referenceFileLinkRepository;

    public void saveRepository(Long userId, ReferenceDto referenceDto){
        long referenceId =  referenceRepository.save(referenceDto.toEntity(userId)).getId();

        if(referenceDto.getLink().length() > 0  && referenceDto.getLink() != null){
            ReferenceFileLink referenceFileLink = ReferenceFileLink.builder().rId(referenceId).link(referenceDto.getLink()).build();
            referenceFileLinkRepository.save(referenceFileLink);
        }
    }

}
