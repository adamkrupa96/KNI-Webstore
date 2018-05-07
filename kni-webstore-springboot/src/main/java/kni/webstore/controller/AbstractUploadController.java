package kni.webstore.controller;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import kni.webstore.model.ImageName;
import kni.webstore.model.UploadImageModel;
import kni.webstore.service.UploadFilesService;

/**
 * 
 * @author adam
 * kontroler i endpointy w celu osobnego ladowania informacji o obiekcie (w pierwszej kolejnosci), 
 * a pozniej jego obrazkow/plikow
 */
public abstract class AbstractUploadController<T extends UploadImageModel<ImageName>, ID extends Serializable> {
 
	protected UploadFilesService uploadFilesService;
	protected CrudRepository<T, ID> crudRepository;
	
	public AbstractUploadController(UploadFilesService uploadFilesService, CrudRepository<T, ID> crudRepository) {
		this.uploadFilesService = uploadFilesService;
		this.crudRepository = crudRepository;
	}
 
	@PostMapping("/images")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<String> saveFileInServerStorage(@RequestParam("file") MultipartFile file,
			@RequestParam("entityId") ID entityID) {
		String message = "";
		try {
			uploadFilesService.saveFile(file);
			
			saveEntityWithImageName(entityID, file.getOriginalFilename());
			
			message = String.format("You successfully uploaded %s !", file.getOriginalFilename());
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} catch (Exception e) {
			message = String.format("FAIL to upload %s !", file.getOriginalFilename());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}
	
	protected abstract void saveEntityWithImageName(ID entityID, String filename);
 
	/**
	 * @param filesNames -> lista z nazwami plikow z bazy danych dla konkretnego obiektu
	 * @return
	 * zwraca liste sciezek do plikow (na serwerze)
	 * sciezki sa budowane przy pomocy MvcUriComponentsBuildera
	 * ktory do sciezki naszego serwera (localhost:8080/ a pozniej po zdeployowaniu na serwerze np. google.pl/)
	 * dopisuje w tym przypadku sciezke z metody getFile -> images/ + nazwa pliku pobrana z przekazanej listy
	 * pozniej te sciezki wykorzystujemy do wyswietlania plikow (w <img src="sciezka" />
	 */
	/* do poprawienia przy nastepnym commicie
	 * @PostMapping("/images/paths/")
	public ResponseEntity<List<String>> getPathsForFiles(@RequestBody List<String> filenames) {
		List<String> fileNames = filenames
				.stream().map(fileName -> MvcUriComponentsBuilder
						.fromMethodName(this.getClass(), "getFile", fileName).build().toString())
				.collect(Collectors.toList());
		
		return ResponseEntity.ok().body(fileNames);
	}*/
	
	/**
	 * 
	 * @param filename przekazujemy nazwe pliku w celu uzyskania sciezki do pliku
	 * @return zwraca sciezke do pliku ktorej uzywamy pozniej na froncie 
	 * pod <img src="zwrocona sciezka" />
	 */
	@GetMapping("/images/path/{filename:.+}")
	public ResponseEntity<String> getPathForFile(@PathVariable String filename) {
		String returnFileName = MvcUriComponentsBuilder
						.fromMethodName(this.getClass(), "getFile", filename)
						.build().toString();
		
		System.out.println(returnFileName);
		return ResponseEntity.ok().body(returnFileName);
	}

	/**
	 * 
	 * @param filename
	 * @return
	 * zwraca nam plik o podanej nazwie wraz z naglowkiem 
	 */
	@GetMapping("/images/{filename:.+}")
	@ResponseBody
	public ResponseEntity<?> getFile(@PathVariable String filename) {
		Resource file = uploadFilesService.getFileByFilename(filename);
		return (ResponseEntity<?>) ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
}
