package in.Jhapuu.foodiesapi.io;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@Builder
public class OrderResponse {
    private String id;

    private String userId;
    private String userAddress;
    private String phoneNumber;
    private String email;
    private List<OrderItem> orderItems;
    private double amount;
    private String paymentStatus;
    private String paymentReferenceId;
    private String orderStatus;
}
