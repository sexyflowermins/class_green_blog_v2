package com.tenco.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * 
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionFieldMessage {
	
	private String field;
	private String message;
	
}
