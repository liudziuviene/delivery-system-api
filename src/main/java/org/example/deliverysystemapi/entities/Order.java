package org.example.deliverysystemapi.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "`order`")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pickupAddress;
    private String deliveryAddress;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
    private String status;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private DeliveryAssignment deliveryAssignment;

}
