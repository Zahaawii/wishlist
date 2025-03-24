package apiassignment.wishlist.controller;

import apiassignment.wishlist.service.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("/test")
    public String homepage(){
        return "homepage";
    }

}
