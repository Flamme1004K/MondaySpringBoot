package com.themoim.board;

import com.themoim.board.service.ReferenceService;
import com.themoim.user.domain.Account;
import com.themoim.user.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class BoardApplicationTests {

	@Autowired
	ReferenceService referenceService;
	@Autowired
	AccountRepository accountRepository;

	@Test
	void contextLoads() {

//		Optional<Account> id = accountRepository.findById("root");
//		referenceService.saveRepository();
//		assertEquals
	}

}
