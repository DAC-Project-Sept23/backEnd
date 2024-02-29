package com.app.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.entities.Ebook;
import com.app.entities.Transaction;
import com.app.entities.User;
import com.app.repositories.BookRepository;
import com.app.repositories.TransactionRepository;
import com.app.repositories.UserRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CheckoutController {

    @Value("${client.url}")
    private String clientUrl;

    @Value("${stripe.private.key}")
    private String stripePrivateKey;
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private TransactionRepository transRepo;
    
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    
    private static final SecureRandom random = new SecureRandom();
    
    @PostMapping("/create-checkout-session")
    public Map<String, Object> createCheckoutSession(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            Stripe.apiKey = stripePrivateKey;
            Session session = Session.create(createSessionParameters(request));
            response.put("sessionId", session.getId());
        } catch (StripeException e) {
            response.put("error", e.getMessage());
        }
        return response;
    }

    private Map<String, Object> createSessionParameters(Map<String, Object> request) {
        Map<String, Object> sessionParams = new HashMap<>();
        sessionParams.put("payment_method_types", new String[]{"card"});
        sessionParams.put("mode", "payment");
        sessionParams.put("line_items", mapToLineItems(request));
        
        String bookIdStr = (String) request.get("bookId");
        Long bookId = Long.parseLong(bookIdStr);
        String userIdInt = (String) request.get("userId");
        Long userId = Long.parseLong(userIdInt);
        
        String key = generateRandomKey(10);
        Ebook book = bookRepository.findById(bookId).orElseThrow(()->new ResourceNotFoundException("Book Not Found"));
        User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
        Transaction trans = new Transaction();
        trans.setSecretkey(key);
        trans.setStatus(false);
        trans.setBook(book);
        trans.setUser(user);
        trans.setPrice(book.getPrice());
        try{
        	transRepo.save(trans);
        }catch(Exception e)
        {
        	throw new ResourceNotFoundException("Please try again later");
        }
		sessionParams.put("success_url",
				clientUrl + "/success?userId=" + userId + "&bookId=" + bookId + "&key=" + key);
        sessionParams.put("cancel_url", clientUrl + "/cancel");
        return sessionParams;
    }

    private List<Map<String, Object>> mapToLineItems(Map<String, Object> request) {
        List<Map<String, Object>> lineItems = new ArrayList<>();

        String bookIdStr = (String) request.get("bookId");
        Long bookId = Long.parseLong(bookIdStr);
        
        Integer quantity = (Integer) request.get("quantity");
        Ebook book = bookRepository.findById(bookId).orElseThrow(()->new ResourceNotFoundException("Book Not Found"));
        if (book != null) {
            Map<String, Object> lineItem = new HashMap<>();
            double priceInCentsDouble = book.getPrice(); // Assuming getPriceInCents returns a double
            int priceInCents = (int) (priceInCentsDouble * 100); // Convert to cents
            lineItem.put("price_data", Map.of(
                    "currency", "usd",
                    "product_data", Map.of("name", book.getTitle()),
                    "unit_amount", priceInCents)
            );
            lineItem.put("quantity", quantity);
            lineItems.add(lineItem);
        }
        return lineItems;
    }

    public static String generateRandomKey(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}


