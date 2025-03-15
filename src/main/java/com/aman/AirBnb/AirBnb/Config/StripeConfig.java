package com.aman.AirBnb.AirBnb.Config;

import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {

    public StripeConfig(@Value("${stripe.secret.key}") String stripeSecretKey) {
        Stripe.apiKey = stripeSecretKey;
    }
}
