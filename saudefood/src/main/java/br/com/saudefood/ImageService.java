package br.com.saudefood;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.saudefood.application.service.ApplicationServiceException;
import br.com.saudefood.util.IOUtils;


@Service
public class ImageService {
	
	@Value("${saudefood.files.logotipo}")
	private String logotiposDir;
	
	public void uploadLogotipo(MultipartFile multiparteFile, String fileName) {
		try {
			IOUtils.copy(multiparteFile.getInputStream(), fileName, logotiposDir);
		} catch (IOException e) {
			throw new ApplicationServiceException(e);
		}
	}
	
}
