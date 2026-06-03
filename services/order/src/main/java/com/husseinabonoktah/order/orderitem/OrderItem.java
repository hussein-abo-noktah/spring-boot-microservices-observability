package com.husseinabonoktah.order.orderitem;


import com.husseinabonoktah.order.order.Order;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    private Integer productId;
    private double quantity;
}
