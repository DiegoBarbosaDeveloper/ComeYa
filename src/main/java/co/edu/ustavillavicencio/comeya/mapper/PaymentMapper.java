package co.edu.ustavillavicencio.comeya.mapper;

import co.edu.ustavillavicencio.comeya.model.entity.PaymentEntity;
import co.edu.ustavillavicencio.comeya.model.enums.PaymentMethod;
import co.edu.ustavillavicencio.comeya.dto.payment.PaymentResponse;
import co.edu.ustavillavicencio.comeya.model.enums.PaymentType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    // MapStruct mappings: map value -> amount and date -> paidAt.
    @Mapping(source = "value", target = "amount")
    @Mapping(source = "date", target = "paidAt")
    // map order id from the related order(s) property on the entity
    @Mapping(target = "orderId", ignore = true) // We'll set this manually in the service layer since it's a collection
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
}
