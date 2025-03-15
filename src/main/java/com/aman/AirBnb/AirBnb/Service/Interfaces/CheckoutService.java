package com.aman.AirBnb.AirBnb.Service.Interfaces;


import com.aman.AirBnb.AirBnb.Entities.BookingEntity;

public interface CheckoutService {

    String getCheckoutSession(BookingEntity booking, String successUrl, String failureUrl);

}
