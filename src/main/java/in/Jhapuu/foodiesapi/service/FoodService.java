package in.Jhapuu.foodiesapi.service;

import in.Jhapuu.foodiesapi.io.FoodRequest;
import in.Jhapuu.foodiesapi.io.FoodResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FoodService {

    String uploadFile(MultipartFile file);
    String getUrlFromPublicId(String publicId);
    FoodResponse addFood(FoodRequest request, MultipartFile file);
    List<FoodResponse> readFoods();
    FoodResponse readFood(String id);
    boolean deleteFile(String publicId);
    void deleteFood(String id);
}
