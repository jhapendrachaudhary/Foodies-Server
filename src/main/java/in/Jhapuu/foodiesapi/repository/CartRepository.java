package in.Jhapuu.foodiesapi.repository;

import in.Jhapuu.foodiesapi.entity.CartEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends MongoRepository<CartEntity, String> {
    Optional<CartEntity> findByUserId(String loggedInUser);
    void deleteByUserId(String loggedInUser);
}
