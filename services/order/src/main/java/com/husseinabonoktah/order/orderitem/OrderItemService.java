package com.husseinabonoktah.order.orderitem;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository repository;
    private final OrderItemMapper mapper;

    public void saveOrderItem(OrderItemRequest request) {
        var order = mapper.toOrderItem(request);
        repository.save(order);
    }

    public List<OrderItemResponse> findAllByOrderId(Integer orderId) {
        return repository.findAllByOrderId(orderId)
                .stream()
                .map(mapper::toOrderItemResponse)
                .collect(Collectors.toList());
    }
}
