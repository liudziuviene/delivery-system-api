package org.example.deliverysystemapi.repositories;

import org.example.deliverysystemapi.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
