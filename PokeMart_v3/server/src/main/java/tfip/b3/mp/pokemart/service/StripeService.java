package tfip.b3.mp.pokemart.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import jakarta.annotation.PostConstruct;
import jakarta.json.Json;

@RestController
public class StripeService {

    @Value("${STRIPE_SECRET_KEY}")
    private String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey; //set global api key
    }

    @GetMapping("api/createPayment")
    public ResponseEntity<String>  createPaymentIntent() throws StripeException{
        PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
                .setCurrency("usd")
                .setAmount(500 * 100L)
                .build();   
        PaymentIntent intent = PaymentIntent.create(createParams);
        String clientSecret = intent.getClientSecret();
        return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Json.createObjectBuilder().add("clientSecret", clientSecret).build().toString());
    }


}
