package com.cos.photogramstart.web.dto.user;

import javax.validation.constraints.NotBlank;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class UserUpdateDto {
	@NotBlank
	private String name; //필수
	@NotBlank
	private String password; //필수
	private String website;
	private String bio;
	private String phone;
	private String gender;
	
	// 조금 위험함. 코드 수정이 필요할 예정
	public User toEntity() {
		return User.builder()
				.name(name) //이름을 입력하지 않으면 DB에 공백의 Data가 입력됨, 그렇기 때문에 Validation 체크 필수
				.password(password) //패스워드를 입력하지 않으면 DB에 공백의 Data가 입력됨, 그렇기 때문에 Validation 체크 필수
				.website(website)
				.bio(bio)
				.phone(phone)
				.gender(gender)
				.build();
	}
}
