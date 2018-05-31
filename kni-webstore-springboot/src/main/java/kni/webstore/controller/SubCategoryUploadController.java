package kni.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kni.webstore.model.ImageName;
import kni.webstore.model.SubCategory;
import kni.webstore.repository.SubCategoryRepository;
import kni.webstore.service.UploadFilesService;

@RestController
@RequestMapping("api/subcat-images")
public class SubCategoryUploadController extends AbstractUploadController<SubCategory, Long> {
	
	@Autowired
	public SubCategoryUploadController(UploadFilesService uploadFilesService,
			SubCategoryRepository subCategoryRepository) {
		
		super(uploadFilesService, subCategoryRepository);
	}

	@Override
	protected void saveEntityWithImageName(Long entityID, String filename) {
		SubCategory subCategory = crudRepository.findOne(entityID);
		ImageName image = new ImageName(filename);
		image.setSubCategory(subCategory);

		subCategory.setImage(image);
		crudRepository.save(subCategory);
	}

}
