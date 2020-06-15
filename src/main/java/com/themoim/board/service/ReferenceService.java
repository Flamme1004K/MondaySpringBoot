package com.themoim.board.service;

import com.themoim.board.domain.Reference;
import com.themoim.board.domain.ReferenceFileLink;
import com.themoim.board.dto.ReferenceDto;
import com.themoim.board.repository.ReferenceFileLinkRepository;
import com.themoim.board.repository.ReferenceRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.naming.NotContextException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReferenceService {

    private final ReferenceRepository referenceRepository;
    private final ReferenceFileLinkRepository referenceFileLinkRepository;

    public void saveRepository(Long userId, ReferenceDto referenceDto){
        try {
        Reference reference =  referenceRepository.save(referenceDto.toEntity(userId));
            if (reference != null) {
                List<String> linksList = referenceDto.getLink();
                if (linksList != null && linksList.size() >0 ) {
                    for (String link : linksList) {
                        ReferenceFileLink referenceFileLink = ReferenceFileLink.builder()
                                .reference(reference)
                                .link(link)
                                .build();
                        referenceFileLinkRepository.save(referenceFileLink);
                    }
                } else if(linksList == null) {
                    //메세지 적어주기
                    throw new NullPointerException("reference create fail");
                }
            } else {
                throw new NullPointerException("reference create fail");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

}
