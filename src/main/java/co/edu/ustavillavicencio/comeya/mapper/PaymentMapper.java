package co.edu.ustavillavicencio.comeya.mapper;

import co.edu.ustavillavicencio.comeya.model.entity.PaymentEntity;
import co.edu.ustavillavicencio.comeya.dto.payment.PaymentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    @Mapping(target = "orderId", ignore = true)
    @Mapping(source = "status", target = "status")
    @Mapping(source = "value", target = "amount")
    @Mapping(source = "method", target = "method")
    @Mapping(source = "date", target = "paidAt", ignore = true)
    @Mapping(target = "transactionReference", ignore = true)
    PaymentResponse toResponse(PaymentEntity payment);
}
