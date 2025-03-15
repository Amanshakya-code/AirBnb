package com.aman.AirBnb.AirBnb.Service;

import com.aman.AirBnb.AirBnb.Entities.BookingEntity;
import com.aman.AirBnb.AirBnb.Entities.UserEntity;
import com.aman.AirBnb.AirBnb.Repositories.BookingRepository;
import com.aman.AirBnb.AirBnb.Service.Interfaces.CheckoutService;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckoutServiceImpl implements CheckoutService {

    private final BookingRepository bookingRepository;

    @Override
    public String getCheckoutSession(BookingEntity booking, String successUrl, String failureUrl) {
        log.info("Creating session for booking with ID: {}", booking.getId());
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            CustomerCreateParams customerParams = CustomerCreateParams.builder()
                    .setName(user.getName())
                    .setEmail(user.getEmail())
                    .build();
            Customer customer = Customer.create(customerParams);

            SessionCreateParams sessionParams = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setBillingAddressCollection(SessionCreateParams.BillingAddressCollection.REQUIRED)
                    .setCustomer(customer.getId())
                    .setSuccessUrl(successUrl)
                    .setCancelUrl(failureUrl)
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency("inr")
                                                    .setUnitAmount(booking.getAmount().multiply(BigDecimal.valueOf(100)).longValue())
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName(booking.getHotel().getName() +" : "+ booking.getRoom().getType())
                                                                    .setDescription("Booking ID: "+booking.getId())
                                                                    .build()
                                                    )
                                                    .build()
                                    )
                                    .build()
                    )
                    .build();

            Session session = Session.create(sessionParams);

            booking.setPaymentSessionId(session.getId());
            bookingRepository.save(booking);

            log.info("Session created successfully for booking with ID: {}", booking.getId());
            return session.getUrl();

        } catch (StripeException e) {
            throw new RuntimeException(e);
        }

    }
}
