package com.facturaelectronica.app.domain;

import java.util.Arrays;

public class UploadFile {

	private String name;
	
	private byte[] file;

	public UploadFile() {
		super();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "UploadFile [name=" + name + ", file=" + Arrays.toString(file) + "]";
	}
	
	
}
