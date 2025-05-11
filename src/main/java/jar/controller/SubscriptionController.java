package jar.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jar.model.Subscription;
import jar.model.User;
import jar.repository.UserRepository;
import jar.service.SubscriptionService;
import jar.dto.SubscriptionRequest;

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserRepository userRepository;

    // Endpoint to create or update a subscription
    @PostMapping("/create")
    public ResponseEntity<?> createSubscription(@RequestBody SubscriptionRequest request, Principal principal) {
        String username = principal.getName();

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        User user = optionalUser.get();
        Subscription subscription = subscriptionService.createOrUpdateSubscription(user, request.getSubscriptionType());

        return new ResponseEntity<>(subscription, HttpStatus.CREATED);
    }

    // Optional: Add a GET endpoint to check current subscription
    @GetMapping("/my")
    public ResponseEntity<?> getMySubscription(Principal principal) {
        String username = principal.getName();

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        User user = optionalUser.get();
        Subscription subscription = subscriptionService.getSubscriptionForUser(user);

        if (subscription == null) {
            return new ResponseEntity<>("No active subscription found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(subscription, HttpStatus.OK);
    }
}
