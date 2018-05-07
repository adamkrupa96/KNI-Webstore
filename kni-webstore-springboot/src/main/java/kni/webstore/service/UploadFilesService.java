package kni.webstore.service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface UploadFilesService {
	void saveFile(MultipartFile file);
	void saveFiles(List<MultipartFile> files);
	Resource getFileByFilename(String filename);
	List<Resource> getFilesByFilenames(List<String> filenames);
	void deleteAllFilesFromStorage();
	void deleteFileByFilename(String filename);
	void deleteFilesByFilenames(List<String> filenames);
	void initStorageForFiles();
}
