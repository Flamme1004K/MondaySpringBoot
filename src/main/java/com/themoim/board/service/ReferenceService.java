package com.themoim.board.service;

import com.themoim.board.domain.Reference;
import com.themoim.board.domain.ReferenceFileLink;
import com.themoim.board.dto.ReferenceDto;
import com.themoim.board.repository.ReferenceFileLinkRepository;
import com.themoim.board.repository.ReferenceRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.naming.NotContextException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReferenceService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ReferenceRepository referenceRepository;
    private final ReferenceFileLinkRepository referenceFileLinkRepository;

    public void saveReference(Long userId, ReferenceDto referenceDto){
        logger.info("saveReference start");
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
                    logger.info("create fileLink Reference");
                } else if(linksList == null) {
                    logger.info("create not fileLink Reference");
                }
            } else {
                throw new NullPointerException("reference create fail");
            }
        } catch (NullPointerException e) {
            logger.info("No Reference ");
        }
        logger.info("saveReference end");
    }

}
