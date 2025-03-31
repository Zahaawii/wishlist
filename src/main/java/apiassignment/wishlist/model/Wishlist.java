package apiassignment.wishlist.model;


import java.util.List;
import java.util.UUID;

public class Wishlist {
    private int wishlistId;
    private String wishlistName;
    private String token;

    public Wishlist(int wishlistId, String wishlistName) {
        this.wishlistId = wishlistId;
        this.wishlistName = wishlistName;
        this.token = generateToken();
    }
    public Wishlist(){
    }

    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    public int getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(int wishlistId) {
        this.wishlistId = wishlistId;
    }

    public String getWishlistName() {
        return wishlistName;
    }

    public void setWishlistName(String wishlistName) {
        this.wishlistName = wishlistName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
