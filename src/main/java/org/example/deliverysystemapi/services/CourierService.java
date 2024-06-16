package org.example.deliverysystemapi.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.deliverysystemapi.dto.CreateCourierRequest;
import org.example.deliverysystemapi.dto.UpdateCourierRequest;
import org.example.deliverysystemapi.entities.Courier;
import org.example.deliverysystemapi.exceptions.DuplicateCourierException;
import org.example.deliverysystemapi.repositories.CourierRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CourierService {
    private final CourierRepository courierRepository;

    public List<Courier> getAllCouriers() {
        return courierRepository.findAll();
    }

    public Courier getCourierById(Long id) {
        Courier courier = courierRepository.findById(id).orElse(null);
        if (courier == null) {
            log.warn("Courier with ID {} not found", id);
        }
        return courier;
    }

    public Courier addCourier(CreateCourierRequest createCourierRequest) {
        Courier courier = new Courier();
        if (createCourierRequest != null) {
            courier.setName(createCourierRequest.getName());
            courier.setSurname(createCourierRequest.getSurname());
            courier.setVehicleType(createCourierRequest.getVehicleType());
            courier.setPhoneNo(createCourierRequest.getPhoneNo());
            courier.setAddress(createCourierRequest.getAddress());
            courier.setEmail(createCourierRequest.getEmail());
        }
        try {
            return courierRepository.saveAndFlush(courier);
        } catch (DataIntegrityViolationException ex) {
            log.error("Error adding courier: {}", ex.getMessage());
            throw new DuplicateCourierException("Phone number or email already exists");
        }
    }

    public Courier updateCourier(Long id, UpdateCourierRequest updateCourierRequest) {
        Courier courier = courierRepository.findById(id).orElse(null);
        if (courier == null) {
            log.warn("Courier with ID {} not found", id);
            return null;
        }

        if (updateCourierRequest.getName() != null) {
            courier.setName(updateCourierRequest.getName());
        }
        if (updateCourierRequest.getAddress() != null) {
            courier.setAddress(updateCourierRequest.getAddress());
        }
        if (updateCourierRequest.getEmail() != null) {
            courier.setEmail(updateCourierRequest.getEmail());
        }
        if (updateCourierRequest.getSurname() != null) {
            courier.setSurname(updateCourierRequest.getSurname());
        }
        if (updateCourierRequest.getVehicleType() != null) {
            courier.setVehicleType(updateCourierRequest.getVehicleType());
        }
        if (updateCourierRequest.getPhoneNo() != null) {
            courier.setPhoneNo(updateCourierRequest.getPhoneNo());
        }
        try {
            return courierRepository.saveAndFlush(courier);
        } catch (DataIntegrityViolationException ex) {
            log.error("Error updating courier: {}", ex.getMessage());
            throw new DuplicateCourierException("Phone number or email already exists");
        }
    }

    @Transactional
    public void deleteCourierById(Long id) {
        courierRepository.deleteById(id);
    }

    public List<Courier> filterCouriers(String name, String surname, String vehicleType) {
        if (name != null && !name.isEmpty()) {
            return courierRepository.findByName(name);
        } else if (surname != null && !surname.isEmpty()) {
            return courierRepository.findBySurname(surname);
        } else if (vehicleType != null && !vehicleType.isEmpty()) {
            return courierRepository.findByVehicleType(vehicleType);
        } else {
            return courierRepository.findAll();
        }
    }

    public Courier filterCouriersByUniqueFields(String phoneNo, String email) {
        if (phoneNo != null && !phoneNo.isEmpty()) {
            return courierRepository.findByPhoneNo(phoneNo);
        } else if (email != null && !email.isEmpty()) {
            return courierRepository.findByEmail(email);
        } else {
            return null;
        }
    }
}


