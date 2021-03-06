package com.themoim.board.service;

import com.themoim.board.domain.Reference;
import com.themoim.board.domain.ReferenceFileLink;
import com.themoim.board.dto.FileLinkDTO;
import com.themoim.board.dto.ReferenceDTO;
import com.themoim.board.repository.ReferenceFileLinkRepository;
import com.themoim.board.repository.ReferenceRepository;
import com.themoim.common.error.exception.InvalidValueException;
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

import java.util.ArrayList;
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

    public void saveReference(String cn, ReferenceDTO.Req req) throws InvalidValueException {
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

        Account account = accountRepository.findByUserId(cn).orElseThrow(()->new InvalidValueException("회원을 찾을 수 없습니다."));
        Reference reference = referenceRepository.save(req.toEntity(account));
        List<FileLinkDTO.Req> fileLinkDTO = req.getFile();

        if(fileLinkDTO.size() > 0) {
            saveFile(fileLinkDTO, reference);
        }
    }

    private void saveFile(List<FileLinkDTO.Req> fileLinkDTO, Reference reference) {
        for (FileLinkDTO.Req fileReq: fileLinkDTO ) {
            serveralFileSave(fileReq, reference);

        }
    }

    private void serveralFileSave(FileLinkDTO.Req fileReq, Reference reference) {
        ReferenceFileLink fileLink = ReferenceFileLink.builder()
                .reference(reference)
                .link(fileReq.getLinkDomain())
                .build();
        referenceFileLinkRepository.save(fileLink);
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
    public ReferenceDTO.Resp reference(long id) throws InvalidValueException {

        Reference reference = referenceRepository.findById(id).orElseThrow(()-> new InvalidValueException("게시글을 찾을 수 없습니다."));

        List<ReferenceFileLink> fileLinksConfirm = reference.getReferenceFileLink();

        if(fileLinksConfirm.size()>0) {

            List<FileLinkDTO.Resp> fileRespList = makeFile(fileLinksConfirm);

            return ReferenceDTO.Resp.isFile(reference, fileRespList);

        } else {

            return ReferenceDTO.Resp.notFile(reference);
        }
    }

    private List<FileLinkDTO.Resp> makeFile(List<ReferenceFileLink> fileLinks) {
        List<FileLinkDTO.Resp> fileRespList = new ArrayList<>();

        for (ReferenceFileLink file : fileLinks
        ) {
            FileLinkDTO.Resp fileResp = FileLinkDTO.Resp.builder().id(file.getId()).linkDomain(file.getLink()).build();
            fileRespList.add(fileResp);
        }
        return fileRespList;
    }

    @Transactional
    public void updateBoard(long boardNum, ReferenceDTO.Req req) throws  InvalidValueException{
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
                    return referenceChange;
                }
        ).orElseThrow(()-> new InvalidValueException("해당 게시물을 찾을 수 없습니다."));

        List<FileLinkDTO.Req> fileLinkDTO = req.getFile();
        if(fileLinkDTO.size() > 0) {
            updateFile(fileLinkDTO, reference);
        }

    }

    private void updateFile(List<FileLinkDTO.Req> fileLinkDTO, Reference reference) {

        for (FileLinkDTO.Req fileReq: fileLinkDTO ) {
            boolean isDeleteFile = referenceFileLinkRepository.findById(fileReq.getId()).isPresent();

            if(isDeleteFile) {
                referenceFileLinkRepository.deleteById(fileReq.getId());
            } else {
                serveralFileSave(fileReq, reference);
            }
        }
    }

    //1. Dto의 파일과 Req의 파일을 먼저 비교해야되지 않을까?
        // 먼저 referenceFileLinkReposiotry에 대한 정보부터 얻어오자?
        // old file과 new file의 사이즈 중에 가장 큰걸로 해야하지 않을까?
        // --> 스터디 : 그냥 업데이트 해주는 방식?
        // 너무 어렵게 생각했다.
//        if(req.getFile().size() > 0) {
//
//            List<ReferenceFileLink> oldFileLink = reference.getReferenceFileLink();
//            List<FileLinkDTO.Req> newFileLink = req.getFile();
//            int fileLinkSize = Math.max(oldFileLink.size(), newFileLink.size());
//
//            for(int i=0; i < fileLinkSize; i++) {
//                boolean fileLink = referenceFileLinkRepository.findById(oldFileLink.get(i).getId()).equals(newFileLink.get(i).getId());
//                if(fileLink) {
//                    ReferenceFileLink file = ReferenceFileLink.builder().link(newFileLink.get(i).getLinkDomain()).build();
//                } else {
//
//                }
//            }
//
//        }

}
