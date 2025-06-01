package in.Jhapuu.foodiesapi.controller;

import in.Jhapuu.foodiesapi.io.OrderRequest;
import in.Jhapuu.foodiesapi.io.OrderResponse;
import in.Jhapuu.foodiesapi.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.awt.image.RasterFormatException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrderWithPayment(@RequestBody OrderRequest request){
        return orderService.createOrderWithPayment(request);
    }
    @PostMapping("verify")
    public void verifyPayment(@RequestBody Map<String, String> paymentData){
        orderService.verifyPayment(paymentData,"paid");
    }
    @GetMapping
    public List<OrderResponse> getOrders(){
        return orderService.getUserOrders();
    }
    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteOrder(@PathVariable String orderId){
        orderService.removeOrder(orderId);
    }
    @GetMapping("/all")
    public List<OrderResponse> getOrdersOfAllUsers(){
      return  orderService.getOrdersOfAllUsers();
    }

    @PatchMapping("status/{orderId}")
    public void updateOrderStatus(@PathVariable String orderId, @RequestParam String status){
        orderService.updateOrderStatus(orderId,status);
    }

}
