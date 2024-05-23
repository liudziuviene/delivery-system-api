package org.example.deliverysystemapi.repositories;

import org.example.deliverysystemapi.entities.DeliveryAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryAssignmentRepository extends JpaRepository<DeliveryAssignment, Long> {
}
