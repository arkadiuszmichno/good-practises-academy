package pl.praktycznajava.module2.encapsulation.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(staticName = "of")
public class Order {

   List<OrderItem> items;
   DeliveryType deliveryType;
   OrderStatus status;
   Address deliveryAddress;
   BigDecimal totalAmount;
   BigDecimal discountAmount;
   BigDecimal deliveryCost;

   public void updateDeliveryAddress(Address deliveryAddress) {
      this.deliveryAddress = deliveryAddress;
      this.deliveryCost = calculateDeliveryCost();
   }

   public void updateDeliveryType(DeliveryType deliveryType) {
      this.deliveryType = deliveryType;
      this.deliveryCost = calculateDeliveryCost();
   }

   public void addProduct(OrderItem item) {
       totalAmount = totalAmount.add(item.getPrice());
       discountAmount = calculateDiscount(totalAmount);
       this.deliveryCost = calculateDeliveryCost();
   }

    public void completeOrder() {
        for (OrderItem item : items) {
            item.completeOrder();
        }
        status = OrderStatus.COMPLETED;
    }

    private BigDecimal calculateDiscount(BigDecimal totalAmount) {
        BigDecimal discount = BigDecimal.ZERO;
        if (totalAmount.compareTo(BigDecimal.valueOf(500)) > 0) {
            discount = totalAmount.multiply(BigDecimal.valueOf(0.2));
        } else if (totalAmount.compareTo(BigDecimal.valueOf(50)) > 0) {
            discount = totalAmount.multiply(BigDecimal.valueOf(0.05));
        }
        return discount;
    }

    private BigDecimal calculateDeliveryCost() {
      double totalWeight = getItemsTotalWeight();
      double deliveryTypeCost = getDeliveryCost();

      double shippingCost = totalWeight * 0.5 + deliveryTypeCost;
      if (!deliveryAddress.isPolandCountry()) {
         shippingCost += 80;
      }
      if (totalWeight > 100) {
         shippingCost *= 1.1;
      }
      return BigDecimal.valueOf(shippingCost);
   }

   private double getItemsTotalWeight() {
      return items.stream()
              .mapToDouble(OrderItem::getTotalWeight)
              .sum();
   }

   private double getDeliveryCost() {
      return deliveryType == DeliveryType.EXPRESS ? 30 : 15;
   }
}
