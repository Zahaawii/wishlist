package apiassignment.wishlist.model;

import java.util.List;

public class User {

    private int userId;
    private String name;
    private String username;
    private String password;
    private int roleId;
    private List<Wishlist> wishlists;

    public User(int userId, String name, String username, String password, int roleId, List<Wishlist> wishlists) {
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.password = password;
        this.roleId = roleId;
        this.wishlists = wishlists;
    }

    public User() {

    }

    public List<Wishlist> getWishlists() {
        return wishlists;
    }

    public void setWishlists(List<Wishlist> wishlists) {
        this.wishlists = wishlists;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
