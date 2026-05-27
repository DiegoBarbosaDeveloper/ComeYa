package co.edu.ustavillavicencio.comeya.mapper;

import co.edu.ustavillavicencio.comeya.model.entity.PaymentEntity;
import co.edu.ustavillavicencio.comeya.model.enums.PaymentMethod;
import co.edu.ustavillavicencio.comeya.dto.payment.PaymentResponse;
import co.edu.ustavillavicencio.comeya.dto.payment.PaymentUpdateRequest;
import co.edu.ustavillavicencio.comeya.model.enums.PaymentType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    // MapStruct mappings: map value -> amount and date -> paidAt.
    @Mapping(source = "value", target = "amount")
    @Mapping(source = "date", target = "paidAt")
    @Mapping(target = "orderId", ignore = true)
    PaymentResponse toResponse(PaymentEntity payment);

    // Provide a custom mapping for enums to avoid unmapped-constant errors.
    default PaymentType map(PaymentMethod method) {
        if (method == null) return null;
        try {
            return PaymentType.valueOf(method.name());
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", ignore = true)
    @Mapping(source = "amount", target = "value")
    @Mapping(target = "method", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "orders", ignore = true)
    void updateEntityFromRequest(PaymentUpdateRequest req, @MappingTarget PaymentEntity entity);
}
