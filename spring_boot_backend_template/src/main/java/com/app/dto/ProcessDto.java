package com.app.dto;


import com.app.entities.Status;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProcessDto {
	private Long adminId;
	private Long bookId;
	private String comment;
	private Status sts;
}
