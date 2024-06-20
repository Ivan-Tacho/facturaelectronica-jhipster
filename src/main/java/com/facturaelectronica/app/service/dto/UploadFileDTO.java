package com.facturaelectronica.app.service.dto;

import org.springframework.web.multipart.MultipartFile;

public class UploadFileDTO {

	private String name;
	
    private MultipartFile file;

    
	public UploadFileDTO() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "UploadFileDTO [name=" + name + "]";
	}
    
}
