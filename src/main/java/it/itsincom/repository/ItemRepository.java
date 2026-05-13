package it.itsincom.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import it.itsincom.entity.Item;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ItemRepository implements PanacheRepository<Item> {
}
