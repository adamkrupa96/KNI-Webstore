package kni.webstore.model;

import java.util.List;

public interface UploadImageModel<T> {
	void setImages(List<T> image);
	List<T> getImages();
	void setImage(T image);
	T getImage();
}
