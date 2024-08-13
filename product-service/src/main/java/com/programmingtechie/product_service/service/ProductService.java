package com.programmingtechie.product_service.service;


import com.programmingtechie.product_service.dto.ProductPerchaseRequest;
import com.programmingtechie.product_service.dto.ProductPerchaseResponse;
import com.programmingtechie.product_service.dto.ProductRequest;
import com.programmingtechie.product_service.dto.ProductResponse;
import com.programmingtechie.product_service.exception.ProductsPurchaseException;
import com.programmingtechie.product_service.model.Branch;
import com.programmingtechie.product_service.model.Product;
import com.programmingtechie.product_service.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
// tạo contructor cho các trường yeu cầu như final
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public Integer createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .id(productRequest.getId())
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .availableQuantity(productRequest.getAvailableQuantity())
                .branch(Branch.builder()
                        .id(productRequest.getBrandId())
                        .build())
                .build();

        productRepository.save(product);
        log.info("Product {} is saved", product.getId());
        return product.getId();
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
//        products.stream().map(product -> {
//            return ProductResponse.builder()
//                    .id(product.getId())
//                    .name(product.getName())
//                    .description(product.getDescription())
//                    .price(product.getPrice())
//                    .build();
//        }).toList();
        return products.stream().map(this::mapToProductResponse).collect(Collectors.toList());
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .availableQuantity(product.getAvailableQuantity())
                .brandId(product.getBranch().getId())
                .brandName(product.getBranch().getName())
                .brandDescription(product.getBranch().getDescription())
                .build();
    }

    public List<ProductPerchaseResponse> purchaseProducts(List<ProductPerchaseRequest> requests) {
        // lấy các id của sản phẩm muốn mua
        List<Integer> products = requests.stream().map(ProductPerchaseRequest::getProductId).toList();

        // lấy id sản phẩm muoons mua tìm kiếm trong db xem còn hay k
        List<Product> storedProducts = productRepository.findAllByIdInOrderById(products);

        //ng dùng mua 1,2,3 mà trong db có 1,2 => lỗi
        if(products.size() != storedProducts.size()){
             throw new ProductsPurchaseException("One or more product is not exist");
        }

        List<ProductPerchaseRequest> storesRequest = requests.stream()
                .sorted(Comparator.comparing(ProductPerchaseRequest::getProductId)).collect(Collectors.toList());

        var purchasedProducts = new ArrayList<ProductPerchaseResponse>();

        for(int i = 0 ; i < storedProducts.size() ;  i++) {
            Product product = storedProducts.get(i);
            ProductPerchaseRequest productRequest = storesRequest.get(i);

            if (product.getAvailableQuantity() < productRequest.getQuantity()) {
                throw new ProductsPurchaseException(
                        "Không đủ số lượng hàng tồn kho cho sản phẩm có ID:: " + productRequest.getProductId());
            }
            // update số lượng sản phẩm trong kho ví dụ
            // trong kho có 10 mà mua 3 thì còn 7
            double newAvailableQuantity = product.getAvailableQuantity() - productRequest.getQuantity();
            product.setAvailableQuantity(newAvailableQuantity);

            productRepository.save(product);
            purchasedProducts.add(toproductPurchaseResponse(product, productRequest.getQuantity()));
        }
        return purchasedProducts;
    }

    private ProductPerchaseResponse toproductPurchaseResponse(Product product, double quantity) {
        return ProductPerchaseResponse.builder()
                .productId(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(quantity)
                .build();
    }

    public ProductResponse findProductById(Integer id) {
        return productRepository.findById(id).map(this::mapToProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy id sản phẩm"));
    }
}
