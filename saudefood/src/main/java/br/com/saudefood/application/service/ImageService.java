package br.com.saudefood.application.service;

import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.saudefood.util.IOUtils;


@Service
public class ImageService {
	
	@Value("${saudefood.files.logotipo}")
	private String logotiposDir;
	
	@Value("${saudefood.files.categoria}")
	private String categoriaDir;
	
	@Value("${saudefood.files.comida}")
	private String comidasDir;
	
	public void uploadLogotipo(MultipartFile multiparteFile, String fileName) {
		
		try {
			IOUtils.copy(multiparteFile.getInputStream(), fileName, logotiposDir);
		} catch (IOException e) {
			throw new ApplicationServiceException(e);
		}
	}
	
	public byte[] getBytes(String type, String imgName) {
		
		try {
				
			String dir;
			
			if("comida".equals(type)) {
				dir = comidasDir;
			}else if("logotipo".equals(type)) {
				dir = logotiposDir;
			}else if("categoria".equals(type)) {
				dir = categoriaDir;
			}else {
				throw new Exception(type + "não é um tipo de imagem válido");
			}
			
			return IOUtils.getByte(Paths.get(dir, imgName));
		}catch (Exception e) {
			throw new ApplicationServiceException(e);
		}
		}
	
}
