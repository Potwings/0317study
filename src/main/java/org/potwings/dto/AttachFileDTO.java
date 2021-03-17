package org.potwings.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttachFileDTO {

	private String fileName;
	private String filePath;
	private String uuid;
	private boolean image;
	
	
}
