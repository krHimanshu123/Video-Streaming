package jar.service;

import java.time.LocalDate;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jar.model.Subscription;
import jar.model.User;
import jar.repository.SubscriptionRepository;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    // Create or update a subscription
    public Subscription createOrUpdateSubscription(User user, String subscriptionType) {
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setSubscriptionType(subscriptionType);
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusMonths(1)); // Example: 1-month subscription
        subscription.setActive(true);
        
        return subscriptionRepository.save(subscription);
    }

    // Check if the user has an active subscription
    public boolean isActiveSubscription(User user) {
        java.util.Optional<Subscription> subscription = subscriptionRepository.findByUserId(user.getId());
        return subscription.isPresent() && subscription.get().isActive();
    }

    public Subscription getSubscriptionForUser(User user) {
        return (Subscription) SubscriptionRepository.findByUserAndActiveTrue(user).orElse(null);
    }

}
