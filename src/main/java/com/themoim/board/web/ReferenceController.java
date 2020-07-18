package com.themoim.board.web;

import com.themoim.board.dto.ReferenceDto;
import com.themoim.board.dto.ReferenceRespDto;
import com.themoim.board.service.ReferenceService;
import com.themoim.common.response.ResponseMessage;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
@RequestMapping(value = "/api/v1/reference")
@RequiredArgsConstructor
public class ReferenceController {

    private final ReferenceService referenceService;

    @ApiOperation(value="saveBoard", notes = "게시판 저장")
    @PostMapping(value = "/{cn}")
    public ResponseEntity saveBoard(
            @PathVariable(name ="cn") String cn,
            @Valid @RequestBody ReferenceDto.Req req
    ) throws NotFoundException {
        referenceService.saveReference(cn, req);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @ApiOperation(value="boardList", notes = "게시판 조회")
    @GetMapping
    public ResponseMessage<List<ReferenceRespDto>> boardList(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size
            ){
        List<ReferenceRespDto> referenceDtoList = referenceService.referencesList(page, size);
        ResponseMessage<List<ReferenceRespDto>> resp = ResponseMessage.ok(referenceDtoList);
        return resp;
    }

    @ApiOperation(value="readBoard", notes = "게시글 일기")
    public ResponseMessage<ReferenceRespDto> readBoard(
            @PathVariable (name = "boardNum") long boardNum
    ) {
        ReferenceRespDto referenceRespDto = referenceService.reference(boardNum);
        ResponseMessage<ReferenceRespDto> resp = ResponseMessage.ok(referenceRespDto);
        return resp;
    }

    @ApiOperation(value="updateBoard", notes = "게시판 수정")
    @PutMapping(value = "/{boardNum}")
    public ResponseEntity boardUpdate(
            @PathVariable(name ="boardNum") long boardNum,
            @RequestBody ReferenceDto.Req req) {
        referenceService.updateBoard(boardNum,req);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
