package com.example.cmart.app.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.cmart.app.entity.ChatMessageEntity;

public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long>{

	@Transactional	
	@Query(value = "SELECT * FROM chat_message c "
			+ "WHERE c.username = ?1 "
			+ "ORDER BY c.createDate ASC",
			nativeQuery = true)
	Page<ChatMessageEntity> findAllMessageUsername(String username, Pageable pageable);
}
