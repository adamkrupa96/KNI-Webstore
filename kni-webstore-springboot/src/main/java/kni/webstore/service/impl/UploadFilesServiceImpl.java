package kni.webstore.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import kni.webstore.service.UploadFilesService;

@Service
public class UploadFilesServiceImpl implements UploadFilesService {

	private final String UPLOAD_FILES_LOCATION = "uploaded-images";
	private final Path rootLocation = Paths.get(UPLOAD_FILES_LOCATION);

	/**
	 * 
	 * @param file
	 *            Files.copy - kopiuje plik ze zrodla ktorym
	 *            jest file.getInputStream() do lokalizacji ktora jest tworzona
	 *            przez dolaczenie do rootLocation nazwy pliku (pod ta nazwa pozniej
	 *            jest przechowywany plik)
	 */
	@Override
	public void saveFile(MultipartFile file) {
		try {
			Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
			file.getInputStream().close();
		} catch (Exception e) {
			throw new RuntimeException("Exception thrown during saving file.");
		}
	}

	@Override
	public void saveFiles(List<MultipartFile> files) {
		files.stream().forEach(file -> {
			try {
				Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
				file.getInputStream().close();
			} catch (IOException e) {
				throw new RuntimeException("Exception thrown during saving files.");
			}
		});
	}

	/**
	 * 
	 * @param filename
	 *            pobiera plik korzystajac z jego nazwy
	 *            metoda rootLocation.resolve(filename) dolacza do sciezki z folderem
	 *            w ktorym sa pliki jego nazwe w celu utworzenia sciezki dostepu do pliku         
	 */
	@Override
	public Resource getFileByFilename(String filename) {
		try {
			Path file = rootLocation.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Exception thrown during downloading file.");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Exception thrown during downloading file.");
		}
	}

	@Override
	public List<Resource> getFilesByFilenames(List<String> filenames) {
		List<Resource> resources = new LinkedList<>();

		filenames.stream().forEach(filename -> {
			Path file = rootLocation.resolve(filename);
			try {
				Resource resource = new UrlResource(file.toUri());
				if (resource.exists() || resource.isReadable()) {
					resources.add(resource);
				} else {
					throw new RuntimeException("Exception thrown during downloading files.");
				}
			} catch (MalformedURLException e) {
				throw new RuntimeException("Exception thrown during downloading files.");
			}
		});
		return resources;
	}

	@Override
	public void deleteAllFilesFromStorage() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	@Override
	public void deleteFileByFilename(String filename) {
		try {
			Files.deleteIfExists(this.rootLocation.resolve(filename));
		} catch (IOException e) {
			throw new RuntimeException(String.format("Could not delete %s file!", filename));
		}
	}

	@Override
	public void deleteFilesByFilenames(List<String> filenames) {
		filenames.stream().forEach(filename -> {
			try {
				Files.deleteIfExists(this.rootLocation.resolve(filename));
			} catch (IOException e) {
				throw new RuntimeException(String.format("Could not delete files!"));
			}
		});
	}

	@Override
	public void initStorageForFiles() {
		try {
			Files.createDirectory(rootLocation);
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize storage for uploaded files!");
		}
	}

}