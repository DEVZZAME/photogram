package com.cos.photogramstart.handler;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

@RestController
//Data를 return하기 위해 사용
@ControllerAdvice
//Exception이 터지면 모두 가로채는 어노테이션
public class ControllerExceptionHandler {
	
	@ExceptionHandler(CustomValidationException.class)
	//RuntimeException이 발동하는 모든 class를 아래 함수가 가로채게 만듦
	//public CMRespDto<?> validationException(CustomValidationException e) {
	public String validationException(CustomValidationException e) {
		//CMRespDto, Script 비교
		//1. 클라이언트에게 응답할 때는 Script가 훨씬 좋음(브라우저, 클라이언트)
		//2. Ajax 통신 - CMRespDto가 좋음(개발자)
		//3. Android 통신 - CMRespDto가 좋음(개발자)
		return Script.back(e.getErrorMap().toString());
		//return new CMRespDto<Map>(-1, e.getMessage(), e.getErrorMap());
	}
	
	@ExceptionHandler(CustomValidationApiException.class)
	public ResponseEntity<?> validationApiException(CustomValidationApiException e) {
		return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST);
	}
}
