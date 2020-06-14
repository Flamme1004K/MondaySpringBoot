package com.themoim.board.web;

import com.themoim.board.dto.ReferenceDto;
import com.themoim.board.service.ReferenceService;
import com.themoim.user.domain.Account;
import com.themoim.user.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/reference")
@RequiredArgsConstructor
public class ReferenceController {

    private final ReferenceService referenceService;
    private final AccountRepository accountRepository;

    @PostMapping(value = "/post/{cn}")
    public ResponseEntity saveBoard(
            @PathVariable(name ="cn") String cn,
            ReferenceDto referenceDto
    ) {
        Account id = accountRepository.findByUserIdEquals(cn);
        if(id != null) {
            long userID = id.getId();
            referenceService.saveRepository(userID, referenceDto);
        }else if (id == null){
            return new ResponseEntity<>("fail", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
