package apiassignment.wishlist.model;

public class Wish {

    private int wishlistId;
    private int wishId;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String link;
    private boolean isReserved;

    public Wish(int wishlistId, int wishId, String name, String description, String link, boolean isReserved, double price, int quantity) {
        this.wishlistId = wishlistId;
        this.wishId = wishId;
        this.name = name;
        this.description = description;
        this.link = link;
        this.isReserved = isReserved;
        this.price = price;
        this.quantity = quantity;
    }
    public Wish(){

    }

    public int getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(int wishlistId) {
        this.wishlistId = wishlistId;
    }

    public int getWishId() {
        return wishId;
    }

    public double getPrice() {
        return price;
    }

    public String getPriceRounded() {
        int roundedPrice = (int) Math.round(price);
        return "$" + roundedPrice;

    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setWishId(int wishId) {
        this.wishId = wishId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }
}
