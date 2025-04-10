package apiassignment.wishlist.model;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Wishlist {
    private int userId;
    private int wishlistId;
    private String wishlistName;
    private String token;
    private List<Wish> wishes;

    public Wishlist(int wishlistId, String wishlistName) {
        this.wishlistId = wishlistId;
        this.wishlistName = wishlistName;
        this.token = generateToken();

    }

    public Wishlist() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public List<Wish> getWishes() {

        return (wishes != null) ? wishes : new ArrayList<>();

    }

    public void setWishes(List<Wish> wishes) {
        this.wishes = wishes;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getAmountOfWishes() {
        return wishes.size();
    }

    @Override
    public String toString() {
        return "Wishlist{" +
                "wishlistId=" + wishlistId +
                ", wishlistName='" + wishlistName + '\'' +
                ", token='" + token + '\'' +
                ", wishes=" + wishes +
                '}';
    }
}
