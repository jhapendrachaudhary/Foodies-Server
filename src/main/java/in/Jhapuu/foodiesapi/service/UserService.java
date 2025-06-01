package in.Jhapuu.foodiesapi.service;

import in.Jhapuu.foodiesapi.io.UserRequest;
import in.Jhapuu.foodiesapi.io.UserResponse;

public interface UserService {

  UserResponse registerUser(UserRequest userRequest);

  String findByUserId();
}
