package com.cos.photogramstart.web.api;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
//API컨트롤러는 Data를 응답해야 하기 때문에 모두 RestController 어노테이션 사용
public class UserApiController {
	
	private final UserService userService;
	
	@PutMapping("/api/user/{id}")
	public CMRespDto<?> update(@PathVariable int id, 
			@Valid UserUpdateDto userUpdateDto,
			BindingResult bindingResult, //꼭 @Valid가 적혀있는 다음 파라미터에 적어야 됨
			@AuthenticationPrincipal PrincipalDetails pricipalDetails) {
		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();

			for (FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			throw new CustomValidationApiException("유효성검사 실패", errorMap);
		}
		//Data를 받기 위한 DTO가 필요함
		User userEntity = userService.회원수정(id, userUpdateDto.toEntity());
		pricipalDetails.setUser(userEntity);//세션 정보 변경
		return new CMRespDto<>(1, "회원수정 완료", userEntity);
	}
}