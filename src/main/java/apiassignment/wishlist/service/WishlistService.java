package apiassignment.wishlist.service;


import apiassignment.wishlist.model.User;
import apiassignment.wishlist.model.Wishlist;
import apiassignment.wishlist.repository.WishlistRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;

    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }


    public User login(String username, String password){
        return wishlistRepository.login(username, password);
    }

    public boolean isLoogedIn(HttpSession session) {
        return session.getAttribute("user") != null;
    }

    public Wishlist getWishlistByuserId(int userId ){
        return wishlistRepository.getWishlistByuserId( userId);
    }
    public User getUserByUsername(String username){
        return wishlistRepository.getUserByUsername(username);
    }

    public void createWishList(int userID, String name) {
        wishlistRepository.createWishList(userID, name);
    }

}
