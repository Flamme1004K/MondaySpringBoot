package com.themoim.board.service;

import com.themoim.board.domain.Reference;
import com.themoim.board.domain.ReferenceFileLink;
import com.themoim.board.dto.ReferenceDto;
import com.themoim.board.dto.ReferenceRespDto;
import com.themoim.board.repository.ReferenceFileLinkRepository;
import com.themoim.board.repository.ReferenceRepository;
import com.themoim.user.domain.Account;
import com.themoim.user.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReferenceService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AccountRepository accountRepository;
    private final ReferenceRepository referenceRepository;
    private final ReferenceFileLinkRepository referenceFileLinkRepository;


    @Transactional
    public void saveReference(String cn, ReferenceDto referenceDto){
        logger.info("saveReference start");

            Account account = accountRepository.findByUserId(cn).orElseThrow(NullPointerException::new);
            Reference reference = referenceRepository.save(referenceDto.toEntity(account));
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
                    }
                } else {
                    throw new NullPointerException();
                }
    }
    @Transactional(readOnly = true)
    public List<ReferenceRespDto> referencesList(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page,size);
        return referenceRepository.findAll(pageRequest).map(
                reference -> ReferenceRespDto
                        .builder()
                        .writeNo(reference.getId())
                        .writeName(reference.getWrittenBy().getUsername())
                        .title(reference.getTitle()).build()
                ).getContent();
    }
}
