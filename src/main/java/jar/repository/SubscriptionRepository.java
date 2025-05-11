package jar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jar.model.Subscription;
import jar.model.User;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription> findByUserId(Long userId);
    List<Subscription> findByActiveTrue();
	static org.apache.el.stream.Optional findByUserAndActiveTrue(User user) {
		// TODO Auto-generated method stub
		return null;
	}
	static org.apache.el.stream.Optional findByUserAndActiveTrue1(User user) {
		// TODO Auto-generated method stub
		return null;
	}
}

