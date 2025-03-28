package apiassignment.wishlist.repository;


import apiassignment.wishlist.model.User;
import apiassignment.wishlist.model.Wish;
import apiassignment.wishlist.model.Wishlist;
import apiassignment.wishlist.rowmappers.UserRowmapper;
import apiassignment.wishlist.rowmappers.WishRowmapper;
import apiassignment.wishlist.rowmappers.WishlistRowmapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import java.sql.PreparedStatement;
import java.sql.Statement;

import java.util.List;

@Repository
public class WishlistRepository {


    private JdbcTemplate jdbcTemplate;

    public WishlistRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<User>getAllUsers(){
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new UserRowmapper());
    }


    public User getUserByUsername(String username){
        String sql = "SELECT * FROM users WHERE username = ?";
        List<User>temp = jdbcTemplate.query(sql, new UserRowmapper(), username);
        if(temp.isEmpty()){
            return null;
        }
        return temp.getFirst();
    }

    public User getUserById(int userId){
        String sql = "SELECT * FROM users WHERE userID = ?";
        List<User>temp = jdbcTemplate.query(sql, new UserRowmapper(), userId);
        if(temp.isEmpty()){
            return null;
        }
        return temp.getFirst();
    }


    public User login(String username, String password){
        String sql = "SELECT * FROM users WHERE username = ?";
        List<User> temp = jdbcTemplate.query(sql, new UserRowmapper(), username);
        if(temp.isEmpty()){
            return null;
        }
        User user = temp.getFirst();
        if(!user.getPassword().equals(password)){
            return null;
        }
        return user;
    }

    public Wishlist getAllWishesByUserId(int userId ){

        String sql= "SELECT wishes.* FROM wishes join wishlists on wishes.wishlistID = wishlists.wishlistID where wishlists.userID = ?";
        List<Wish>listOfWishes = jdbcTemplate.query(sql, new WishRowmapper(), userId);
        if(listOfWishes.isEmpty()){
            return null;
        }
        int wishlistId = listOfWishes.getFirst().getWishlistId();
        String wishlistName = listOfWishes.getFirst().getName();
        return new Wishlist(wishlistId, wishlistName, listOfWishes);
    }

    public List<Wishlist> getAllWishlistsByUserId(int id) {
        String sql = "SELECT * FROM wishlists WHERE userID = ?";
        List<Wishlist> wishlists = jdbcTemplate.query(sql, new WishlistRowmapper(), id);
        return wishlists;
    }


    public boolean isUsernameFree(String username){
        String sql = "SELECT * FROM users WHERE username = ?";
        List<User> users = jdbcTemplate.query(sql, new UserRowmapper(), username);
        if(users.isEmpty()){
            return true;
        }
        return false;
    }

    public User registerUser(User user){
        String sql = "INSERT INTO users (name, username, password, roleID) VALUES(?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getName());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setInt(4, 1);
            return ps;
        }, keyHolder);

        int userId =keyHolder.getKey() != null ? keyHolder.getKey().intValue() : -1;

        if(userId != -1){
            user.setUserId(userId);
        }

        return user;

    }



    public void createWishList(int userId, String wishListName) {
        String sql = "INSERT INTO wishlists (UserID, wishlistName) VALUES (?, ?)";

        KeyHolder keyHolder= new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, userId);
            ps.setString(2, wishListName);
            return ps;
        }, keyHolder);

    }

    public void addWish(Wish wish) {
        try {
            String sql = "INSERT INTO wishes (wishlistID, wishName, description, price, quantity, link) VALUES (?, ?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setInt(1, 1); //husk at ændre til den specifike wishlist's id
                ps.setString(2, wish.getName());
                ps.setString(3, wish.getDescription());
                ps.setDouble(4, wish.getPrice());
                ps.setInt(5, wish.getQuantity());
                ps.setString(6, wish.getLink());
                return ps;
            }, keyHolder);

            int wishId = keyHolder.getKey() != null ? keyHolder.getKey().intValue() : -1;

            if (wishId != -1) {
                wish.setWishId(wishId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int id) {
        String sql = "DELETE FROM USERS WHERE USERID = ?";
        jdbcTemplate.update(sql, id);
    }



    public User adminRegisterUser(User user){

        String checkRoleQuery = "SELECT COUNT(*) FROM roles WHERE roleID = ?";
        Integer count = jdbcTemplate.queryForObject(checkRoleQuery, Integer.class, user.getRoleId());

        if (count == 0) {
            throw new IllegalArgumentException("RoleID " + user.getRoleId() + " does not exist.");
        }
        String sql = "INSERT INTO users (name, username, password, roleID) VALUES(?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getName());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getRoleId());
            return ps;
        }, keyHolder);

        int userId =keyHolder.getKey() != null ? keyHolder.getKey().intValue() : -1;

        if(userId != -1){
            user.setUserId(userId);
        }

        return user;

    }



}
