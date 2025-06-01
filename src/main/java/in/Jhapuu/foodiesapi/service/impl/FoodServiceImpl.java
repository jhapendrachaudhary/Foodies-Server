package in.Jhapuu.foodiesapi.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import in.Jhapuu.foodiesapi.entity.FoodEntity;
import in.Jhapuu.foodiesapi.helper.AppConstant;
import in.Jhapuu.foodiesapi.helper.CloudinaryURL;
import in.Jhapuu.foodiesapi.io.FoodRequest;
import in.Jhapuu.foodiesapi.io.FoodResponse;
import in.Jhapuu.foodiesapi.repository.FoodRepository;
import in.Jhapuu.foodiesapi.service.FoodService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;
    private final Cloudinary cloudinary;

    public FoodServiceImpl(FoodRepository foodRepository, Cloudinary cloudinary){
        this.foodRepository=foodRepository;
        this.cloudinary=cloudinary;
    }

    @Override
    public String uploadFile(MultipartFile file) {

//        String key = UUID.randomUUID().toString();

        try {
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return uploadResult.get("secure_url").toString(); // returns actual Cloudinary URL
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public String getUrlFromPublicId(String publicId) {
        return cloudinary.url()
                .transformation(
                        new Transformation<>()
                                .width(AppConstant.WIDTH)
                                .height(AppConstant.HEIGHT)
                                .crop(AppConstant.CROP)
                ).generate(publicId);


    }

    @Override
    public FoodResponse addFood(FoodRequest request, MultipartFile file) {
       FoodEntity newFoodEntity = convertToEntity(request);
       String imageUrl = uploadFile(file);
       newFoodEntity.setImageUrl(imageUrl);
       newFoodEntity =  foodRepository.save(newFoodEntity);
        return convertToResponse(newFoodEntity);
    }

    @Override
    public List<FoodResponse> readFoods() {
        List<FoodEntity> databaseEntities = foodRepository.findAll();
        return databaseEntities.stream().map(object-> convertToResponse(object)).collect(Collectors.toList());
    }

    @Override
    public FoodResponse readFood(String id) {
       FoodEntity existingFood= foodRepository.findById(id).orElseThrow(()->new RuntimeException("food not found for the id "+id));
       return convertToResponse(existingFood);
    }

    @Override
    public boolean deleteFile(String publicId) {
        try {
            Map<String, Object> result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            return "ok".equals(result.get("result"));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void deleteFood(String id) {
        FoodResponse response = readFood(id);
        // Extract publicId from URL
        String publicId = extractPublicIdFromUrl(response.getImageUrl());

        // Delete from Cloudinary
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete image from Cloudinary", e);
        }

        // Delete from MongoDB
        foodRepository.deleteById(id);
    }
    private String extractPublicIdFromUrl(String url) {
        try {
            int lastSlash = url.lastIndexOf('/');
            int dot = url.lastIndexOf('.');
            if (lastSlash >= 0 && dot > lastSlash) {
                return url.substring(lastSlash + 1, dot);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private FoodEntity convertToEntity(FoodRequest request){
        return FoodEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .category(request.getCategory())
                .build();
    }

    private FoodResponse convertToResponse(FoodEntity entity){
       return  FoodResponse.builder()
               .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .imageUrl(entity.getImageUrl())
                .category(entity.getCategory())
                .build();
    }
}
