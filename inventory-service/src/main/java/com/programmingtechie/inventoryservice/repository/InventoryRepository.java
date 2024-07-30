package com.programmingtechie.inventoryservice.repository;

import com.programmingtechie.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    List<Inventory> findByProductIdIn(List<Integer> stock);

    List<Inventory> findByProductId(Integer productId);

    List<Inventory> findBySkuCodeIn(List<String> stock);

    @Modifying
    @Query(value = "DELETE FROM inventory is WHERE is.id = ?1")
    void deleteByIdInventoty(@Param("id") Integer id);

}
