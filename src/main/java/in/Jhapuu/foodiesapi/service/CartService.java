package in.Jhapuu.foodiesapi.service;

import in.Jhapuu.foodiesapi.io.CartRequest;
import in.Jhapuu.foodiesapi.io.CartResponse;

public interface CartService {
    CartResponse addToCart(CartRequest request);

    CartResponse getCart();

    void clearCart();

    CartResponse removeFromCart(CartRequest request);
}
