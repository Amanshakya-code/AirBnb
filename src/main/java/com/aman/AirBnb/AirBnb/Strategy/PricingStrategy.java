package com.aman.AirBnb.AirBnb.Strategy;

import com.aman.AirBnb.AirBnb.Entities.InventoryEntity;

import java.math.BigDecimal;
public interface PricingStrategy {

    BigDecimal calculatePrice(InventoryEntity inventory);
}
