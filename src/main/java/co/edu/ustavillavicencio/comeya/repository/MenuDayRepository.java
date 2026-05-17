package co.edu.ustavillavicencio.comeya.repository;

import co.edu.ustavillavicencio.comeya.model.entity.MenuDayEntity;
import co.edu.ustavillavicencio.comeya.model.entity.MenuDayId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuDayRepository extends JpaRepository<MenuDayEntity, MenuDayId> {
}