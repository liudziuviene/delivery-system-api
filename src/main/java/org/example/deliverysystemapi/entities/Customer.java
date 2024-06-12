package org.example.deliverysystemapi.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String phoneNo;
    private String address;
    private String email;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;
}