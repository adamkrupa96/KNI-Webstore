package kni.webstore.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.multipart.MultipartFile;
import kni.webstore.model.ImageName;

public class ImageNameUtils {
	
	public static List<ImageName> getNamesOfImages(List<MultipartFile> files) {
		List<ImageName> imagesNames = files.stream()
            .map(file -> new ImageName(file.getOriginalFilename()))
            .collect(Collectors.toList());
		
		return imagesNames;
	}
}
