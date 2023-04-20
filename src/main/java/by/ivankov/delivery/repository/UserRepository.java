package by.ivankov.delivery.repository;

import by.ivankov.delivery.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    User findUserByUsername(String username);
    List<User> findAll();
    void deleteById(Long id);
    Optional<User> findById(Long id);
    User getUserByUsername(String username);
}
