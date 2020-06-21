package com.themoim.board.web;

import com.fasterxml.jackson.annotation.JsonView;
import com.themoim.board.domain.BaseTime;
import com.themoim.board.domain.Reference;
import com.themoim.board.dto.ReferenceDto;
import com.themoim.board.dto.ReferenceRespDto;
import com.themoim.board.service.ReferenceService;
import com.themoim.user.domain.Account;
import com.themoim.user.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/*
RequiredArgsConstructor 와 AllArgsConstructor의 차이?
wrappedResponseDTO?
앱 어플리케이션은 v1/v2형식으로 버전관리가 필요하다?
@Valid https://velog.io/@hellozin/Valid-%EC%98%88%EC%99%B8%EB%A5%BC-%EC%A0%84%EC%97%AD-%EC%BB%A8%ED%8A%B8%EB%A1%A4%EB%9F%AC%EB%A1%9C-%EA%B0%84%EB%8B%A8%ED%95%98%EA%B2%8C-%EC%B2%98%EB%A6%AC%ED%95%98%EA%B8%B0
SWAGGER?
JSONINCLUDE?
람다를 적극활용해보자
*/

@RestController
@RequestMapping(value = "/reference")
@RequiredArgsConstructor
public class ReferenceController {

    private final ReferenceService referenceService;
    private final AccountRepository accountRepository;

    @PostMapping(value = "/post/{cn}")
    public ResponseEntity saveBoard(
            @PathVariable(name ="cn") String cn,
            @Valid @RequestBody ReferenceDto referenceDto
    ) {
        Account account = accountRepository.findByUserId(cn);
        if( account != null) {
            referenceService.saveReference(account, referenceDto);
        }else if (account == null){
            return new ResponseEntity<>("Fail", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping(value="/board")
    public ResponseEntity boardList(
            final Pageable pageable
            ){
        Page<ReferenceRespDto> referenceDtoList =
                referenceService.referencesList(pageable).map(
                        reference -> new ReferenceRespDto(reference.getId(), reference.getWrittenBy().getUsername() ,reference.getTitle()));
        return  new ResponseEntity<>(referenceDtoList,HttpStatus.OK);
    }
}
