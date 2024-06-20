package com.csme.assist.leave.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LeaveSummuryDTO {

	private int leavesTaken;
	private int exceededLeaves;
}
