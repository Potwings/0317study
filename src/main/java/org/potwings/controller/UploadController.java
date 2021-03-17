package org.potwings.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.potwings.dto.AttachFileDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;

@RestController
@Log4j
public class UploadController {
	
	@GetMapping("/view/{file}/{uuid}")
	public ResponseEntity<byte[]> view(@PathVariable("file")String fileName, @PathVariable("uuid")String uuid) {
		log.info("view-------------");
		
		File file = new File("C:\\upload\\"+ uuid +"_"+fileName);
		
		HttpHeaders header = new HttpHeaders();
		
		header.add("Content-Type", "image/jpg");
		
		ResponseEntity<byte[]> res = null;
		try {
			res =  new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),header,HttpStatus.OK);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	

	@PostMapping("/upload")
	public ResponseEntity<List<AttachFileDTO>> upload(MultipartFile[] files) {
		
		String path = "C:\\upload\\";
		
		List<AttachFileDTO> list = new ArrayList<>();

		for (int i = 0; i < files.length; i++) {

			MultipartFile file = files[i];
			
			String fileName = file.getOriginalFilename();
			
			String filePath = path+getFolder();
			
			UUID uuid = UUID.randomUUID();
			
			boolean isImage = file.getContentType().startsWith("image");
			
			String newFileName = uuid.toString()+"_"+fileName;
		
			File targetFile = new File(path,newFileName);
			
			AttachFileDTO attachFileDTO = new AttachFileDTO(fileName, filePath, uuid.toString(), isImage);
			
			list.add(attachFileDTO);
			
			try {
				file.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		return new ResponseEntity<>(list, HttpStatus.OK);

	}
	
	private String getFolder() {
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		
		String folderpath = simpleDateFormat.format(new Date());
		
		folderpath.replace("-", File.pathSeparator);
		
		return folderpath;
	}

}
