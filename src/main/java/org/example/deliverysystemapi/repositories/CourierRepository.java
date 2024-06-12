package org.example.deliverysystemapi.repositories;

import org.example.deliverysystemapi.entities.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourierRepository extends JpaRepository<Courier, Long> {

    List<Courier> findByName(String name);

    List<Courier> findBySurname(String surname);

    List<Courier> findByVehicleType(String vehicleType);

    Courier findByPhoneNo(String phoneNo);

    Courier findByEmail(String email);
}
