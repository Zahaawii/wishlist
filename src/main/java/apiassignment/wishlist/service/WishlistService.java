package apiassignment.wishlist.service;


import apiassignment.wishlist.model.User;
import apiassignment.wishlist.model.Wish;
import apiassignment.wishlist.model.Wishlist;
import apiassignment.wishlist.model.Wish;
import apiassignment.wishlist.repository.WishlistRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService{

    private final WishlistRepository wishlistRepository;

    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public List<User> getAllUsers() {
        return wishlistRepository.getAllUsers();
    }

    public User adminRegisterUser(User user) {
        return wishlistRepository.adminRegisterUser(user);
    }

    public void deleteUser(int id) {
        wishlistRepository.deleteUser(id);
    }
    public User login(String username, String password){
        return wishlistRepository.login(username, password);
    }

    public boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("user") != null;
    }

    public Wishlist getAllWishesByUserId(int userId ){
        return wishlistRepository.getAllWishesByUserId(userId);
    }
    public List<Wishlist> getAllWishlistsByUserId (int id) {
        return wishlistRepository.getAllWishlistsByUserId(id);
    }

    public List<Wish> getAllWishesFromWishlistId(int id) {
        return wishlistRepository.getAllWishesFromWishlistId(id);
    }

    public User getUserByUsername(String username){
        return wishlistRepository.getUserByUsername(username);
    }

    public boolean isUsernameFree(String username){
        return wishlistRepository.isUsernameFree(username);
    }

    public User registerUser(User user){
        return wishlistRepository.registerUser(user);
    }
    public void createWishList(int userID, String name) {
        wishlistRepository.createWishList(userID, name);
    }
    public void addWish(Wish wish) {
        wishlistRepository.addWish(wish);
    }
    public Wishlist getWishlistById(int id) {
        return wishlistRepository.getWishlistByID(id);
    }
    public Wish getWishById(int id) {
        return wishlistRepository.getWishById(id);
    }
    public List<Wish> getAllWishesByWishlistId(int id) {
        return wishlistRepository.getAllWishesByWishlistId(id);
    }
    public void updateWish(Wish wish) {
        wishlistRepository.updateWish(wish);
    }
    public void deleteWish(int id) {
        wishlistRepository.deleteWish(id);
    }
    public User updateUser(User user){
        return wishlistRepository.updateUser(user);
    }
    public Wishlist getWishlistByToken(String token) {
        return wishlistRepository.getWishlistByToken(token);
    }





}


