package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//JPA - Java Persistence API(자바로 데이터를 영구적으로 저장(DB)할 수 있는 API를 제공)

@Builder
@AllArgsConstructor //전체 생성자
@NoArgsConstructor //Bean 생성자
@Data
@Entity //DB에 Table을 생성
public class User {
	@Id //Primary Key 생성을 위해 사용
	@GeneratedValue(strategy = GenerationType.IDENTITY) // ID 번호 증가 전략이 DB를 따라감
	//MySQL을 사용하면 AUTO, Oracle을 사용하면 SEQUENCE, MariaDB는 IDENTITY
	private int id;
	//사용자가 많은 서비스의 경우 사용 범위가 넓은 Long을 사용해야 하지만,
	//커다란 서비스를 만들 것이 아니기 때문에 int를 사용
	
	@Column(length = 20, unique = true)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String name;
	
	private String website; //웹사이트
	
	private String bio; //자기소개
	
	@Column(nullable = false)
	private String email;
	
	private String phone;
	
	private String gender;
	
	private String profileImageUrl; //사진
	
	private String role; //권한
	
	private LocalDateTime createDate;
	
	@PrePersist //DB에 INSERT 되기 직전에 실행
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
