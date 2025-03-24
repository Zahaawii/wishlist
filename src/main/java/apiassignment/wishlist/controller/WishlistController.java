package apiassignment.wishlist.controller;

import apiassignment.wishlist.service.WishlistService;
import org.springframework.stereotype.Controller;

@Controller
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

}
