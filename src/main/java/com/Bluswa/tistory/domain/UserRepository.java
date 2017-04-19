package com.Bluswa.tistory.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByUserID(String userID);

}
