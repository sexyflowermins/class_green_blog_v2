package com.tenco.blog.handler;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tenco.blog.dto.ApiErrorResponse;
import com.tenco.blog.dto.ExceptionFieldMessage;

@RestControllerAdvice // IoC
public class RestExcptionHandler {

	@ExceptionHandler(value = Exception.class)
	public void exception(Exception e) {
		System.out.println("--------");
		 System.out.println(e.getClass().getName());
		 System.out.println(e.getMessage());
		 System.out.println("--------");
	}
	@ExceptionHandler(value = IllegalArgumentException.class)
	public String illegalArgumentException(IllegalArgumentException e) {
		return "<h1>" + e.getMessage() + "</h1>";
	}
	
	@ExceptionHandler(value = ConstraintViolationException.class)
	public String constraintViolationException(ConstraintViolationException e) {
		System.out.println("ConstraintViolationException 예외 발생");
		return e.toString();
	}
	
//	@ExceptionHandler(value = MethodArgumentNotValidException.class)
//	public List<ExceptionFieldMessage> methodArgumentNotValidException(MethodArgumentNotValidException e) {
//		
//		List<ExceptionFieldMessage> errorList = new ArrayList<>();
//	
//		e.getBindingResult().getAllErrors().forEach( action -> {
//			
//			FieldError fieldError = (FieldError)action;
//			String fieldName = fieldError.getField();
//			String message = fieldError.getDefaultMessage();
//			
//			ExceptionFieldMessage exceptionFieldMessage = 
//					ExceptionFieldMessage.builder().field(fieldName).message(message).build();
//			errorList.add(exceptionFieldMessage);
//		});
//		System.out.println("MethodArgumentNotValidException 예외 발생");
//		return errorList;
//	}
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ApiErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException e) {
		
		List<ExceptionFieldMessage> errorList = new ArrayList<>();
		
		e.getBindingResult().getAllErrors().forEach( action -> {
			
			FieldError fieldError = (FieldError)action;
			String fieldName = fieldError.getField();
			String message = fieldError.getDefaultMessage();
			
			ExceptionFieldMessage exceptionFieldMessage = 
					ExceptionFieldMessage.builder().field(fieldName).message(message).build();
			errorList.add(exceptionFieldMessage);
		});
		System.out.println("MethodArgumentNotValidException 예외 발생");
		return ApiErrorResponse.builder()
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.code("-1")
				.resultCode("fail")
				.message("잘못된 요청입니다")
				.exceptionFieldMessage(errorList)
				.build();
	}
}
