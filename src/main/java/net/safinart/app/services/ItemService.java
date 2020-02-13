package net.safinart.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.safinart.app.models.Item;
import net.safinart.app.repositories.ItemRepository;

@Service
public class ItemService {
    
    @Autowired
    private ItemRepository itemRepository;
    
    public List<Item> list() {
        return itemRepository.findAll();
    }
    
    public void save(Item item) {
        itemRepository.save(item);
    }
    
}