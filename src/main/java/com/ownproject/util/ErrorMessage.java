package com.ownproject.util;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorMessage {
	@JsonFormat(timezone = "GMT+07:00")
	private Date timestamp;
	private String message;
	private String description;
	private int statusCode;
}
