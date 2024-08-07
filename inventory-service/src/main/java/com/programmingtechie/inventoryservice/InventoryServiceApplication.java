package com.programmingtechie.inventoryservice;

import com.programmingtechie.inventoryservice.model.Inventory;
import com.programmingtechie.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	/**
	 * Tạo data cho table inventory
	 * Khi ứng dụng được chạy thì sẽ lưu vào database
	 * @param inventoryRepository
	 * @return
	 */

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository){
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setProductCode("iphone_13");
			inventory.setQuantity(100);
			inventoryRepository.save(inventory);

			Inventory inventory1 = new Inventory();
			inventory1.setProductCode("iphone_13_red");
			inventory1.setQuantity(0);

			inventoryRepository.save(inventory1);
		};
	}
}
