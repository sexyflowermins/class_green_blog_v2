package com.tenco.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tenco.blog.model.User;

//@Repository // 하지만 여기서는 생략 가능 - 이유는 내부에서 선언을 해주고 있어서 가능하다
public interface UserRepository extends JpaRepository<User, Integer>{
	//select , selectAll , insert ,update, delete 등 
}
