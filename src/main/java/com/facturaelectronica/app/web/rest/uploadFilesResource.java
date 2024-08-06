package com.facturaelectronica.app.web.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.facturaelectronica.app.service.UploadFilesService;
import com.facturaelectronica.app.service.dto.UploadFileDTO;

@RestController
@RequestMapping("/api")
public class uploadFilesResource {

	private final UploadFilesService uploadFilesService;
	
	public uploadFilesResource(UploadFilesService uploadFilesService) {
		this.uploadFilesService = uploadFilesService;
	}
	
	@PostMapping(value = "uploadFile")
	public ResponseEntity<Void> uploadFile(@RequestBody UploadFileDTO uploadFileDTO) {
		//uploadFilesService.upload();
		return ResponseEntity.noContent().build();
	}
	

    @PostMapping(value = "convertirPDFaTexto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String convertirPDFaTexto(@RequestParam("file") MultipartFile archivoPDF) throws Exception{
        //System.out.println(uploadFilesService.convertPdfToText(archivoPDF, "iberdrola"));
        return "Se recibió el archivo PDF: " + archivoPDF.getOriginalFilename();
    }
}
