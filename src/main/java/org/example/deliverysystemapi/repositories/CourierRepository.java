package org.example.deliverysystemapi.repositories;

import org.example.deliverysystemapi.entities.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourierRepository extends JpaRepository<Courier, Long> {
}
