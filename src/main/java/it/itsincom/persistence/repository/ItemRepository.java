package it.itsincom.persistence.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import it.itsincom.persistence.entity.Item;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ItemRepository implements PanacheRepository<Item> {
}
