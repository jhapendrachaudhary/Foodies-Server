package in.Jhapuu.foodiesapi.entity;

import in.Jhapuu.foodiesapi.io.OrderItem;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Document(collection = "orders")
@Builder
@Data
public class OrderEntity {
    @Id
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
