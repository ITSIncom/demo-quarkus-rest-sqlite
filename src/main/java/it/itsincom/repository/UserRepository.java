package it.itsincom.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import it.itsincom.entity.User;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public User findByUsername(String username) {
        return find("username", username).firstResult();
    }
}
