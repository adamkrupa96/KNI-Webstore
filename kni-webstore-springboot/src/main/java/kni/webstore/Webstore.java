package kni.webstore;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import kni.webstore.service.UploadFilesService;

@SpringBootApplication
public class Webstore implements CommandLineRunner {
	
	@Resource
	UploadFilesService uploadFilesService;
	
	public static void main(String[] args) {
		SpringApplication.run(Webstore.class, args);
	}
	
	@Override
	public void run(String... arg) throws Exception {
		uploadFilesService.deleteAllFilesFromStorage();
		uploadFilesService.initStorageForFiles();
	}
}
