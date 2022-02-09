package com.bpm.apimauritel.dtos;

import javax.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class Recharge {

	@Schema(description = "Sender's phone number.", example = "37818077", required = true)
	// @Size(min=8)
	// @Size(max=11)
	@NotNull(message = "Not null")
	private String sender;

	@Schema(description = "Receiver's phone number.", example = "34212133", required = true)
	// @Size(min=8)
	// @Size(max=11)
	private String receiver;

	@Schema(description = "Amount to be sent.", example = "350", required = true)
	private String amount;

	@Schema(description = "idTransction or bundleId generated in MOBILE app.", example = "350", required = true)
	private String bundleId;

	private String codeService;
}
