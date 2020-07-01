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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/*
TODO : RequiredArgsConstructor 와 AllArgsConstructor의 차이?
TODO : wrappedResponseDTO?
TODO : 앱 어플리케이션은 v1/v2형식으로 버전관리가 필요하다?
TODO : @Valid https://velog.io/@hellozin/Valid-%EC%98%88%EC%99%B8%EB%A5%BC-%EC%A0%84%EC%97%AD-%EC%BB%A8%ED%8A%B8%EB%A1%A4%EB%9F%AC%EB%A1%9C-%EA%B0%84%EB%8B%A8%ED%95%98%EA%B2%8C-%EC%B2%98%EB%A6%AC%ED%95%98%EA%B8%B0
TODO : SWAGGER?
TODO : JSONINCLUDE?
TODO : 람다를 적극활용해보자
TODO : 람다 포맷
TODO : 타입시스템이 잘되어있는 언어에서 왜 타입이 잘 안되어있을까? --> 제네릭
TODO : 리스폰스 , Exceoption 핸들러만들기
see 쓰는법


*/



@RestController
@RequestMapping(value = "/reference")
@RequiredArgsConstructor
public class ReferenceController {
    private final ReferenceService referenceService;
    @PostMapping(value = "/post/{cn}")
    public ResponseEntity saveBoard(
            @PathVariable(name ="cn") String cn,
            @Valid @RequestBody ReferenceDto referenceDto
    ) {
        referenceService.saveReference(cn, referenceDto);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity boardList(
            @RequestParam Integer page,
            @RequestParam Integer size
            ){
        List<ReferenceRespDto> referenceDtoList = referenceService.referencesList(page, size);
        return new ResponseEntity<>(referenceDtoList,HttpStatus.OK);
    }
//
//    @PutMapping
//    public ResponseEntity boardUpdate(
//        @RequestBody final ReferenceDto.req req
//    ){
//        referenceService.saveReference();
//    }
}
