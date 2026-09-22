package dearest.dearestshop.api;

import dearest.dearestshop.dto.orderdto.*;
import dearest.dearestshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> addOrder(@RequestBody OrderCreateDto orderCreateDto) {
        Long orderId = orderService.addOrder(orderCreateDto);

        ApiResponse<Long> response = new ApiResponse<>(
                true,
                "주문생성 성공",
                orderId
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponseDto>>> orders(OrderSearchCondition condition) {
        List<OrderResponseDto> orders = orderService.orders(condition);

        ApiResponse<List<OrderResponseDto>> response = new ApiResponse<>(
                true,
                "주문조회 성공",
                orders
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderDetailResponseDto>> detailOrder(@PathVariable("orderId") Long orderId) {
        OrderDetailResponseDto dto = orderService.detailOrder(orderId);

        ApiResponse<OrderDetailResponseDto> response = new ApiResponse<>(
                true,
                "주문상세조회 성공",
                dto
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<OrderMyDto>>> myPage() {
        List<OrderMyDto> orderMyDtos = orderService.myPage();

        ApiResponse<List<OrderMyDto>> response = new ApiResponse<>(
                true,
                "마이페이지조회 성공",
                orderMyDtos
        );
        return ResponseEntity.ok(response);
    }
}
