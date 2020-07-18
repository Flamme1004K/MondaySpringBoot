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

import java.util.ArrayList;
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
    public List<ReferenceDTO.ListResp> referencesList(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page,size);
        return referenceRepository.findAll(pageRequest).map(
                reference -> ReferenceDTO.ListResp.builder()
                                                .no(reference.getId())
                                                .writtenName(reference.getWrittenBy().getUsername())
                                                .title(reference.getTitle())
                                                .createDate(reference.getCreateAt())
                                                .build()
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
    public ReferenceDTO.Resp reference(long id) {
        Reference reference = referenceRepository.findById(id).get();
        List<ReferenceFileLink> fileLinks = reference.getReferenceFileLink();
        if(fileLinks.size()>0) {
            List<FileLinkDTO.Resp> fileRespList = new ArrayList<>();
            for (ReferenceFileLink file : fileLinks
                 ) {
                FileLinkDTO.Resp fileResp = FileLinkDTO.Resp.builder().id(file.getId()).linkDomain(file.getLink()).build();
                fileRespList.add(fileResp);
            }
            return ReferenceDTO.Resp.builder()
                    .boardNo(reference.getId())
                    .writtenName(reference.getWrittenBy().getUsername())
                    .title(reference.getTitle())
                    .content(reference.getContent())
                    .file(fileRespList)
                    .build();
        } else {
            return ReferenceDTO.Resp.builder()
                    .boardNo(reference.getId())
                    .writtenName(reference.getWrittenBy().getUsername())
                    .title(reference.getTitle())
                    .content(reference.getContent())
                    .build();
        }
    }


    @Transactional
    public void updateBoard(long boardNum, ReferenceDTO.Req req) {
        /*
        referenceRepository.findById(boardNum).map(referenceChange -> {
            referenceChange.setTitle((req.getTitle()));
            referenceChange.setContent((req.getContent()));
            return referenceChange;
        }).orElseThrow(NullPointerException::new);

        boolean reference = referenceRepository.findById(boardNum).isPresent();
        if(reference) {
            Reference reference1 = new Reference();
            reference1.setContent(req.getContent());
            reference1.setTitle(req.getTitle());
        } else {
            throw new NullPointerException();
        }

         */
        Reference reference = referenceRepository.findById(boardNum).map(
                referenceChange -> {
                    referenceChange.setTitle((req.getTitle()));
                    referenceChange.setContent(req.getContent());
                    return  referenceChange;
                }
        ).orElseThrow(NullPointerException::new);

        if(req.getFile().size()>0) {
            for(FileLinkDTO.Req file : req.getFile()) {
                referenceFileLinkRepository.findById(file.getId()).map(
                        fileLink -> {
                            fileLink.update(file.getLinkDomain());
                            return fileLink;
                        }
                ).orElseGet(
                       ReferenceFileLink::new
                );
            }
        }


    }
}
