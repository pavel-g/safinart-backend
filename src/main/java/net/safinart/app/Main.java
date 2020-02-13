package net.safinart.app;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.safinart.app.exceptions.NotAuthorized;
import net.safinart.app.exceptions.SessionDead;
import net.safinart.app.exceptions.WrongPassword;
import net.safinart.app.exceptions.WrongUsername;
import net.safinart.app.models.LoginData;
import net.safinart.app.models.NewItem;
import net.safinart.app.models.Token;
import net.safinart.app.models.Toy;
import net.safinart.app.services.AuthService;
import net.safinart.app.services.ImageService;
import net.safinart.app.services.ItemService;
import net.safinart.app.services.NewItemService;

@RestController
@EnableAutoConfiguration
@SpringBootApplication
public class Main {

    private Logger logger = LoggerFactory.getLogger(Main.class);

    @Autowired
    private ItemService itemService;

    @Autowired
    private NewItemService newItemService;

    @Autowired
    private ImageService ImageService;

    @Autowired
    private AuthService authService;

    @RequestMapping("/")
    String home() {
        this.logger.debug("Debug message at route \"/\"");
        return "Hello World!";
    }

    @GetMapping(path = "/list")
    List<Toy> getList2() {
        var items = this.itemService.list();
        var toys = new ArrayList<Toy>();
        items.forEach((item) -> toys.add(Toy.createFromItem(item)));
        return toys;
    }

    @PostMapping(path = "/add")
    Toy addItem(@RequestBody NewItem newItem, @RequestHeader("auth-token") UUID sessionId)
            throws IOException, NotAuthorized, SessionDead
    {
        this.authService.checkToken(sessionId);
        this.logger.debug("add new item: " + newItem);
        this.newItemService.setNewItem(newItem);
        this.newItemService.saveFile();
        this.newItemService.saveItem();
        var savedItem = this.newItemService.getItem();
        return Toy.createFromItem(savedItem);
    }

    @GetMapping(path = "/images/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody byte[] getImage(@PathVariable("name") String name) throws IOException {
        var imageFullName = this.ImageService.getImageFullPath(name);
        var in = new FileInputStream(imageFullName);
        var data = in.readAllBytes();
        in.close();
        return data;
    }

    @GetMapping(path = "/images_preview/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody byte[] getImagePreview(@PathVariable("name") String name) throws IOException {
        var imageFullName = this.ImageService.getPreviewImageFullPath(name);
        var in = new FileInputStream(imageFullName);
        var data = in.readAllBytes();
        in.close();
        return data;
    }

    @PostMapping(path = "/login")
    public Token login(@RequestBody LoginData loginData) throws WrongUsername, WrongPassword {
        var session = this.authService.checkLogin(loginData.name, loginData.password);
        return Token.createFromSession(session);
    }
    
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
    
}