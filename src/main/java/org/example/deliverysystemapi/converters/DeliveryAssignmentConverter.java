package org.example.deliverysystemapi.converters;

import org.example.deliverysystemapi.dto.CreateDeliveryAssignmentResponse;
import org.example.deliverysystemapi.entities.DeliveryAssignment;

import java.util.ArrayList;
import java.util.List;

public class DeliveryAssignmentConverter {

    private DeliveryAssignmentConverter() {
    }

    public static CreateDeliveryAssignmentResponse convertDeliveryAssignmentToCreateDeliveryAssignmentResponse(
            DeliveryAssignment deliveryAssignment) {
        CreateDeliveryAssignmentResponse createDeliveryAssignmentResponse = null;
        if (deliveryAssignment != null) {
            createDeliveryAssignmentResponse = new CreateDeliveryAssignmentResponse();
            createDeliveryAssignmentResponse.setId(deliveryAssignment.getId());
            createDeliveryAssignmentResponse.setDate(deliveryAssignment.getDate());
            createDeliveryAssignmentResponse.setOrderId(deliveryAssignment.getOrder().getId());
            createDeliveryAssignmentResponse.setCourierId(deliveryAssignment.getCourier().getId());
        }
        return createDeliveryAssignmentResponse;
    }

    public static List<CreateDeliveryAssignmentResponse> convertDeliveryAssignmentsToCreateDeliveryAssignmentResponses(
            List<DeliveryAssignment> deliveryAssignments) {
        List<CreateDeliveryAssignmentResponse> createDeliveryAssignmentResponses = null;
        if (deliveryAssignments != null) {
            createDeliveryAssignmentResponses = new ArrayList<>();
            for (DeliveryAssignment deliveryAssignment : deliveryAssignments) {
                createDeliveryAssignmentResponses.add(convertDeliveryAssignmentToCreateDeliveryAssignmentResponse(
                        deliveryAssignment));
            }
        }
        return createDeliveryAssignmentResponses;
    }
}
