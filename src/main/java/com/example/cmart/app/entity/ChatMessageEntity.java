package com.example.cmart.app.entity;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "chat_message")
public class ChatMessageEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//@ManyToOne
	//@JoinColumn(name = "room_id")
	//private RoomChatEntity roomChat;
	
	@Column
	private String username;
	
	@Column
	private String message;
	
	@Column(name = "to_user")
	private String toUser;
	
	@Column(name = "create_date")
	//@CreatedDate
	private LocalDateTime createDate;
	
	@Column(name="room_user")
	private String roomUser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/*
	public RoomChatEntity getRoomChat() {
		return roomChat;
	}

	public void setRoomChat(RoomChatEntity roomChat) {
		this.roomChat = roomChat;
	}
	 */
	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public String getRoomUser() {
		return roomUser;
	}

	public void setRoomUser(String roomUser) {
		this.roomUser = roomUser;
	}
	
	
}
