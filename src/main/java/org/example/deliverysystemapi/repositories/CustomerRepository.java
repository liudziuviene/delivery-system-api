package org.example.deliverysystemapi.repositories;

import org.example.deliverysystemapi.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByName(String name);

    List<Customer> findBySurname(String surname);

    Customer findByPhoneNo(String phoneNo);

    Customer findByEmail(String email);
}
