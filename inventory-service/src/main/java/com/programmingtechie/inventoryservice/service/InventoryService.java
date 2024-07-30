package com.programmingtechie.inventoryservice.service;

import com.programmingtechie.inventoryservice.dto.InventoryResponse;
import com.programmingtechie.inventoryservice.model.Inventory;
import com.programmingtechie.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    @SneakyThrows
    public List<InventoryResponse> isInStock(List<Integer> productId) {
        log.info("Started");
//        Test khi inventory gọi api lâu hơn 3s thì sẽ retry 3 lần rồi đưa ra lỗi 500
//        Thread.sleep(10000);
        log.info("End");
        List<Inventory> inventories = inventoryRepository.findByProductIdIn(productId);

        if (inventories.isEmpty()) {
            throw new RuntimeException("No inventory found");
        }
        List<InventoryResponse> inventoryResponses = new ArrayList<>();
        for(Integer id : productId){
            InventoryResponse inventoryResponse = new InventoryResponse();
            inventoryResponse.setProductId(id);
            List<Inventory> integer = inventoryRepository.findByProductId(id);
            if(integer.size() < 0){
                inventoryResponse.setInStock(false);
            }else {
                inventoryResponse.setInStock(true);
            }
            inventoryResponses.add(inventoryResponse);

            // đặt thành công th trừ sản phẩm trong kho đi mặc đinh là san pham dau tien trong list
            inventoryRepository.deleteByIdInventoty(integer.get(0).getId());
            List<Inventory> afterDelete = inventoryRepository.findByProductId(id);
            log.info("After delete: {}", afterDelete.size());
        }

//        List<InventoryResponse> inventoryResponses = inventories.stream().map(
//                inventory -> InventoryResponse.builder()
//                .productId(inventory.getProductId())
//                .isInStock(inventory.getQuantity() > 0)
//                .build()).toList();

        return inventoryResponses;
//        return inventoryRepository.findByProductIdIn(productId).stream()
//                .map(inventory ->
//                        InventoryResponse.builder()
//                                .skuCode(inventory.getSkuCode())
//                                .isInStock(inventory.getQuantity() > 0)
//                                .build()
//                ).toList();
    }
}
