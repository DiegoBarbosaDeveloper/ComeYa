package co.edu.ustavillavicencio.comeya.dto.dashboard;

import java.math.BigDecimal;

import lombok.*;
 
import java.math.BigDecimal;
 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class DashboardStatsDTO {

    private long totalUsers;
    private long totalOrders;
    private long ordersPendientes;
    private long ordersEnPreparacion;
    private long ordersListos;
    private long ordersEntregados;
    private BigDecimal ventasHoy;
    private BigDecimal ventasTotales;

}
