package net.safinart.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.safinart.app.models.Item;
import net.safinart.app.models.NewItem;

import java.io.IOException;

@Service
public class NewItemService {

    private NewItem newItem;
    private Item item;

    @Value("${app.images_dir}")
    private String imagesDir;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ImageService imageService;

    public void setNewItem(NewItem newItem) {
        this.newItem = newItem;
        this.item = null;
    }

    public void saveFile() throws IOException {
        var imageName = this.newItem.getImageName();
        var imageData = this.newItem.getImageData();
        this.imageService.saveImage(imageName, imageData);
    }
    
    public void saveItem() {
        this.item = Item.createFromNewItem(this.newItem);
        this.itemService.save(this.item);
    }
    
    public Item getItem() {
        return this.item;
    }
    
}