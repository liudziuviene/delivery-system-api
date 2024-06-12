package org.example.deliverysystemapi.converters;

import org.example.deliverysystemapi.dto.CreateCourierResponse;
import org.example.deliverysystemapi.dto.SimpleCourierResponse;
import org.example.deliverysystemapi.entities.Courier;

import java.util.ArrayList;
import java.util.List;

public class CourierConverter {

    private CourierConverter() {
    }

    public static CreateCourierResponse convertCourierToCreateCourierResponse(Courier courier) {
        CreateCourierResponse createCourierResponse = null;
        if (courier != null) {
            createCourierResponse = new CreateCourierResponse();
            createCourierResponse.setId(courier.getId());
            createCourierResponse.setName(courier.getName());
            createCourierResponse.setSurname(courier.getSurname());
            createCourierResponse.setVehicleType(courier.getVehicleType());
            createCourierResponse.setPhoneNo(courier.getPhoneNo());
            createCourierResponse.setAddress(courier.getAddress());
            createCourierResponse.setEmail(courier.getEmail());
        }
        return createCourierResponse;
    }

    public static List<CreateCourierResponse> convertCouriersToCreateCourierResponses(List<Courier> couriers) {
        List<CreateCourierResponse> createCourierResponses = null;
        if (couriers != null) {
            createCourierResponses = new ArrayList<>();
            for (Courier courier : couriers) {
                createCourierResponses.add(convertCourierToCreateCourierResponse(courier));
            }
        }
        return createCourierResponses;
    }

    public static SimpleCourierResponse convertCourierToSimpleCourierResponse(Courier courier) {
        SimpleCourierResponse simpleCourierResponse = null;
        if (courier != null) {
            simpleCourierResponse = new SimpleCourierResponse();
            simpleCourierResponse.setId(courier.getId());
            simpleCourierResponse.setName(courier.getName());
            simpleCourierResponse.setSurname(courier.getSurname());
        }
        return simpleCourierResponse;
    }

    public static List<SimpleCourierResponse> convertCouriersToSimpleCourierResponses(List<Courier> couriers) {
        List<SimpleCourierResponse> simpleCourierResponses = null;
        if (couriers != null) {
            simpleCourierResponses = new ArrayList<>();
            for (Courier courier : couriers) {
                simpleCourierResponses.add(convertCourierToSimpleCourierResponse(courier));
            }
        }
        return simpleCourierResponses;
    }
}
