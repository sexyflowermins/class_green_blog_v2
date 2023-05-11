package com.tenco.blog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tenco.blog.model.User;
import com.tenco.blog.repository.UserRepository;

@RestController // IoC 처리 
public class DummyControllerTest {
	
	//DI
	@Autowired
	private UserRepository userRepository;
	
	public DummyControllerTest(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	// MINE MYPE - application/json
	// 회원등록 샘플
	@PostMapping("/dummy/insert-user")
	public String insertUser(@Validated @RequestBody User user) {
		// 유효성 검사
		user.setRole("user");
		userRepository.save(user);
		System.out.println(user.toString());
		System.out.println("여기 코드 동작 하나요?");
		return "회원가입에 성공";
	}
	//localhost:8080/dummy/user/1
	@GetMapping("/dummy/user/{id}")
	public User getUser(@PathVariable Integer id) {
		// optional - user 있을수도 있고 null 일수도 있다.
		
		//인증검사, 유효성 검사
		//Optional<User> userOp = userRepository.findById(id);
		//1
		//User user = userRepository.findById(id).get();
		//2
//		User user = userRepository.findById(id).orElseGet(() -> {
//			return new User();
//		});
		//3
		User user = userRepository.findById(id).orElseThrow(()->{
			
			return new IllegalArgumentException("해당 유저는 없습니다");
		});
		//1. user 객체가 null 이 아닐때 사용
		//2. orElseGet : 데이가 있으면 그대로 반환 없으면 직접 정의한 객체를 반환 시킬수 있다.
		//3. orElseThrow : 있으면 반환 없으면 예외 던진다
		
		
		System.out.println(user);
		return user;
	}
//	@GetMapping("/dummy/users")
//	public Page<User> pageList(@PageableDefault(size = 2,sort = "id",direction = Direction.DESC) Pageable pageable){
//		//List<User> users = userRepository.findAll();
//		Page<User> pageUser = userRepository.findAll(pageable);
//		return pageUser;
//	}
	
	@GetMapping("/dummy/users")
	public List<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Direction.DESC) Pageable pageable){
		Page<User> pageUser = userRepository.findAll(pageable);
		List<User> users = pageUser.getContent();
		return users;
	}
	//Json 으로 던징 예정
	//Update 를 할때 
	// 기존 로직 처리를 했고
	// 더티 체킹 사용
	@Transactional
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@Validated @PathVariable Integer id,@RequestBody User reqUser) {
		
		//인증검사, 유효성 검사
		//존재여부 확인
		//영속화된 데이터
		User userEntity = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("해당 유저가 존재하지 않습니다.");
		});
		
		//클라이언트가 던진 데이터
		//reqUser
		userEntity.setEmail(reqUser.getEmail());
		userEntity.setPassword(reqUser.getPassword());
		
		//저장처리
		//userRepository.save(userEntity);
		// dirty checking  사용
		// save 를 호출 하지 않았는데 변경 처리 되었다.
		// 트랜잭션이 끝나기 전에 영속성 컨텍스트에 
		// 1차 캐쉬에 들어가 있는 데이터 상태를 변경 감지 한다.
		// 
		return userEntity;
	}
	
}
