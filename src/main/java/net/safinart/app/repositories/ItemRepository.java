package net.safinart.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.safinart.app.models.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {}