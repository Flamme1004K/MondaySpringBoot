package com.themoim.board.service;

import com.themoim.board.domain.Reference;
import com.themoim.board.domain.ReferenceFileLink;
import com.themoim.board.dto.FileLinkDTO;
import com.themoim.board.dto.ReferenceDTO;
import com.themoim.board.repository.ReferenceFileLinkRepository;
import com.themoim.board.repository.ReferenceRepository;
import com.themoim.user.domain.Account;
import com.themoim.user.repository.AccountRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
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


    /*
    2020/07/13 코드는 스토리가 있게 하기
    */

    @Transactional
    public void saveReference(String cn, ReferenceDTO.Req req) throws NotFoundException {
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
        Reference reference = referenceRepository.save(req.toEntity(account));
        List<FileLinkDTO.Req> fileLinkDTO = req.getFile();
            if(fileLinkDTO.size() > 0) {
                for (FileLinkDTO.Req fileReq: fileLinkDTO ) {
                    ReferenceFileLink fileLink = ReferenceFileLink.builder()
                                                                    .reference(reference)
                                                                    .link(fileReq.getLinkDomain())
                                                                    .build();

                    referenceFileLinkRepository.save(fileLink);
                }
            }
    }

    @Transactional(readOnly = true)
    public Page<ReferenceDTO.ListResp> referencesList(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page,size);
        return referenceRepository.findAll(pageRequest).map(
                ReferenceDTO.ListResp::new
        );
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

    /*
    @Transactional(readOnly = true)
    public ReferenceRespDto reference(long id) {
        Reference reference = referenceRepository.findById(id).get();
        return ReferenceRespDto.builder().writeNo(reference.getId()).title(reference.getTitle()).writeName(reference.getWrittenBy().getUsername()).build();
    }

     */
    @Transactional
    public void updateBoard(long boardNum, ReferenceDTO.Req req) {
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
