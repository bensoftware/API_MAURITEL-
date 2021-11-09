package com.bpm.apimauritel.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RechargeClassiqueDto {
	
	@Schema(description = "Sender's phone number.",example = "37818077", required = true)
	@Size(min=8)
	@Size(max=8)
	@NotNull(message = "Not null")
	private String sender;
	@Schema(description = "Receiver's phone number.",example = "34212133", required = true)
	@Size(min=8)
	@Size(max=8)
	private String receiver;
	@Schema(description = "Amout to be sent.",example = "350", required = true)
	private String amount;
}
