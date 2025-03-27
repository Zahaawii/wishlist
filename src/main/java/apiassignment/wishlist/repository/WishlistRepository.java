package apiassignment.wishlist.repository;


import apiassignment.wishlist.model.User;
import apiassignment.wishlist.model.Wish;
import apiassignment.wishlist.model.Wishlist;
import apiassignment.wishlist.rowmappers.UserRowmapper;
import apiassignment.wishlist.rowmappers.WishRowmapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class WishlistRepository {

    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;

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



    public Wishlist getWishlistByuserId(int userId ){

        String sql= "SELECT wishes.* FROM wishes join wishlists on wishes.wishlistID = wishlists.wishlistID where wishlists.userID = ?";
        List<Wish>listOfWishes = jdbcTemplate.query(sql, new WishRowmapper(), userId);
        if(listOfWishes.isEmpty()){
            return null;
        }
        int wishlistId = listOfWishes.getFirst().getWishlistId();
        String wishlistName = listOfWishes.getFirst().getName();
        return new Wishlist(wishlistId, wishlistName, listOfWishes);
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















}
