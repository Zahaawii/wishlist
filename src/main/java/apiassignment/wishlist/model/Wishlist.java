package apiassignment.wishlist.model;


import java.util.ArrayList;
import java.util.List;

public class Wishlist {
    private int wishlistId;
    private String wishlistName;
    private List<Wish>wishes;

    public Wishlist(int wishlistId, String wishlistName, List<Wish> wishes) {
        this.wishlistId = wishlistId;
        this.wishlistName = wishlistName;
        this.wishes = wishes;
    }
    public Wishlist(){
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

    public List<Wish> getWishes() {

        return (wishes != null) ? wishes : new ArrayList<>();
    }

    public void setWishes(List<Wish> wishes) {
        this.wishes = wishes;
    }

    public int getAmountOfWishes () {
        return wishes.size();
    }

}
