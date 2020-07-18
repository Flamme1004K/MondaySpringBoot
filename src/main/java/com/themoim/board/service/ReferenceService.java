package com.themoim.board.service;

import com.themoim.board.domain.Reference;
import com.themoim.board.dto.ReferenceDto;
import com.themoim.board.dto.ReferenceRespDto;
import com.themoim.board.repository.ReferenceFileLinkRepository;
import com.themoim.board.repository.ReferenceRepository;
import com.themoim.user.domain.Account;
import com.themoim.user.repository.AccountRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReferenceService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AccountRepository accountRepository;
    private final ReferenceRepository referenceRepository;
    private final ReferenceFileLinkRepository referenceFileLinkRepository;


    /*
    2020/07/13 코드는 스토리가 있게 하기
    */

    @Transactional
    public void saveReference(String cn, ReferenceDto.Req req) throws NotFoundException {
        /*
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
         */

        Account account = accountRepository.findByUserId(cn).orElseThrow(()->new NotFoundException("회원을 찾을 수 없습니다."));
    }
    @Transactional(readOnly = true)
    public List<ReferenceRespDto> referencesList(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page,size);
        return referenceRepository.findAll(pageRequest).map(
                ReferenceRespDto::new
                ).getContent();
    }


                /*
                reference -> ReferenceRespDto
                        .builder()
                        .writeNo(reference.getId())
                        .writeName(reference.getWrittenBy().getUsername())
                        .title(reference.getTitle()).build()
                 */

    /*
    회사 소스에서 익셉션 레스트 컨트롤 어드바이스를 찾아보자.
    * */

    @Transactional(readOnly = true)
    public ReferenceRespDto reference(long id) {
        Reference reference = referenceRepository.findById(id).get();
        return ReferenceRespDto.builder().writeNo(reference.getId()).title(reference.getTitle()).writeName(reference.getWrittenBy().getUsername()).build();
    }
    @Transactional
    public void updateBoard(long boardNum, ReferenceDto.Req req) {
        /*
        referenceRepository.findById(boardNum).map(referenceChange -> {
            referenceChange.setTitle((req.getTitle()));
            referenceChange.setContent((req.getContent()));
            return referenceChange;
        }).orElseThrow(NullPointerException::new);
         */
        boolean reference = referenceRepository.findById(boardNum).isPresent();
        if(reference) {
            Reference reference1 = new Reference();
            reference1.setContent(req.getContent());
            reference1.setTitle(req.getTitle());
        } else {
            throw new NullPointerException();
        }
    }
}
