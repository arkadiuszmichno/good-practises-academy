package pl.praktycznajava.module2.encapsulation.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(staticName = "of")
public class OrderItem {

    Product product;
    int quantity;

    double getTotalWeight() {
        return product.getWeight() * quantity;
    }

    BigDecimal getPrice() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    public void completeOrder() {
        if (product.getStockQuantity() < quantity) {
            throw new InsufficientStockException(product, quantity);
        }
        product.setStockQuantity(product.getStockQuantity() - quantity);
    }
}
