package apiassignment.wishlist.repository;


import apiassignment.wishlist.model.Friend;
import apiassignment.wishlist.model.User;
import apiassignment.wishlist.model.Wish;
import apiassignment.wishlist.model.Wishlist;
import apiassignment.wishlist.rowmappers.FriendRowmapper;
import apiassignment.wishlist.rowmappers.UserRowmapper;
import apiassignment.wishlist.rowmappers.WishRowmapper;
import apiassignment.wishlist.rowmappers.WishlistRowmapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

import java.util.ArrayList;
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

    //Man søger efter et navn, og så kommer der en liste op af brugere, der indeholder det navn
    // hvis man ikke allerede har en relation til dem
    public List<User>searchFriends(String name, int id){
        List<User> allUsers = getAllUsers();
        List<User> usersWithSpecifikName = new ArrayList<>();
        for(User i: allUsers) {
            if (i.getName().toLowerCase().contains(name.toLowerCase())) {
                if(i.getUserId() != id) {
                    if(isThereAlreadyARelation(id, i.getUserId())){
                        //tjekker om der allerede er en relation, fx ven eller venanmodning
                        usersWithSpecifikName.add(i);
                    }
                }
            }
        }
        if(usersWithSpecifikName.isEmpty()){
            return null;
        }
        return usersWithSpecifikName;
    }

    //tjekker om der allerede er en relation, fx ven eller venanmodning
    public boolean isThereAlreadyARelation(int userId, int friendId){
        String sql = "SELECT * FROM friends WHERE friendOne = ? and friendTwo = ?";
        List<Friend> friendList = jdbcTemplate.query(sql, new FriendRowmapper(), userId, friendId);
        if(!friendList.isEmpty()){
            return false;
        }
        String sqlSecond = "SELECT * FROM friends WHERE friendTwo = ? and friendOne = ?";
        List<Friend> friendListSecond = jdbcTemplate.query(sqlSecond, new FriendRowmapper(), userId, friendId);
        if(!friendListSecond.isEmpty()){
            return false;
        }
        return true;
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

    public List<Wish> getAllWishesFromWishlistId(int id) {
        String sql = "SELECT * FROM wishes WHERE wishlistID = ?";
        List<Wish> wishes = jdbcTemplate.query(sql, new WishRowmapper(), id);

        return wishes;
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
            ps.setInt(4, 2);
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
                ps.setInt(1, wish.getWishlistId());
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

    public Wishlist getWishlistByID(int id) {
        String sql = "SELECT * FROM wishlists WHERE wishlistID = ?";
        List<Wishlist> wishlists = jdbcTemplate.query(sql, new WishlistRowmapper(),id);
        if (wishlists.isEmpty()) {
            return null;
        } else {
            return wishlists.get(0);
        }
    }

    public Wish getWishById(int id) {
        String sql = "SELECT * FROM wishes WHERE wishID = ?";
        List<Wish> wish = jdbcTemplate.query(sql, new WishRowmapper(), id);

        if (wish.isEmpty()) {
            return null;
        } else {
            return wish.get(0);
        }
    }

    public List<Wish> getAllWishesByWishlistId(int id) {
        String sql = "SELECT * FROM wishes WHERE wishlistID = ?";
        return jdbcTemplate.query(sql, new WishRowmapper(), id);
    }

    public void updateWish(Wish wish) {
        String sql = "UPDATE wishes SET wishName = ?, description = ?, price = ?, quantity = ?, link = ? WHERE wishID = ?";
        jdbcTemplate.update(sql,wish.getName(),wish.getDescription(),wish.getPrice(),wish.getQuantity(),wish.getLink(),wish.getWishId());
    }

    public void deleteWish(int id) {
        String sql = "DELETE FROM wishes WHERE wishID = ?";
        jdbcTemplate.update(sql,id);
    }
    public User updateUser(User user){
        String sql = "UPDATE users SET name = ?, username = ?, password = ? WHERE userID = ?";
        jdbcTemplate.update(sql, user.getName(), user.getUsername(), user.getPassword(), user.getUserId());
        return user;
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


    public void addFriend(int friendOneId, int friendTwoId, String status){
        String sql = "INSERT INTO friends (friendOne, FriendTwo, friendStatus) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, friendOneId, friendTwoId, status);
    }

    public void acceptFriend(int friendshipId, String status){
        String sql = "UPDATE friends SET friendStatus = ? WHERE friendshipId = ?";
        jdbcTemplate.update(sql, status, friendshipId);
    }

    public void removeFriend(int friendshipId){
        String sql ="DELETE FROM friends WHERE friendshipId = ?";
        jdbcTemplate.update(sql, friendshipId);
    }


    public List<Friend> getFriendRequestId(int userId){
        String sql = "SELECT * FROM friends WHERE friendTwo = ? and friendStatus = ?";
        List<Friend>friendList = jdbcTemplate.query(sql, new FriendRowmapper(), userId, "requested");
        if(friendList.isEmpty()){
            return null;
        }
        return friendList;
    }

    public List<User> getFriendRequestName(int userId){
        List<User> friendRequest = new ArrayList<>();
        List<Friend> friendListId= getFriendRequestId(userId);
        if(friendListId == null){
            return null;
        }
        List<Integer> ids = new ArrayList<>();
        for(Friend i: friendListId){
            ids.add(i.getFriendOne());
        }
        for(Integer nums: ids){
            friendRequest.add(getUserById(nums));
        }
        return friendRequest;

    }

    public List<Friend>sendFriendRequestId(int userId){
        String sql ="SELECT * FROM friends WHERE friendOne = ? and friendStatus = ?";
        List<Friend>friendList = jdbcTemplate.query(sql, new FriendRowmapper(), userId, "requested");
        if(friendList.isEmpty()){
            return null;
        }
        return friendList;
    }

    public List<User>sendFriendRequestName(int userId){
        List<User> friendRequest = new ArrayList<>();
        List<Friend> friendListId= sendFriendRequestId(userId);
        if(friendListId == null){
            return null;
        }
        List<Integer> ids = new ArrayList<>();
        for(Friend i: friendListId){
            ids.add(i.getFriendTwo());
        }
        for(Integer nums: ids){
            friendRequest.add(getUserById(nums));
        }
        return friendRequest;
    }


    public List<Friend> getFriendsId(int userId){
        String sql = "SELECT * FROM friends WHERE friendOne = ? and friendStatus = ?";
        List<Friend>friendList = jdbcTemplate.query(sql, new FriendRowmapper(), userId, "friends");
        String sqlSecondColumn = "SELECT * FROM friends WHERE friendTwo = ? and friendStatus = ?";
        List<Friend>secondFriendList = jdbcTemplate.query(sqlSecondColumn, new FriendRowmapper(), userId, "friends");

        if(!friendList.isEmpty() && !secondFriendList.isEmpty() ){
            friendList.addAll(secondFriendList);
            return friendList;
        }
        if(!friendList.isEmpty()){
            return friendList;
        }
        if(!secondFriendList.isEmpty()){
            return secondFriendList;
        }
        return null;
    }

    public List<User>getFriendsName(int userId){
        List<User>friends = new ArrayList<>();
        List<Integer> ids = new ArrayList<>();

        List<Friend> friendsId = getFriendsId(userId);
        if(friendsId == null){
            return null;
        }

        for(Friend i: friendsId){
            if(i.getFriendOne() != userId){
                ids.add(i.getFriendOne());
            } else if(i.getFriendTwo() != userId){
                ids.add(i.getFriendTwo());
            }
        }
        for(Integer nums: ids){
            friends.add(getUserById(nums));
        }

        return friends;
    }

    public List<Friend>checkIfFriends(int myId, String username){
        int friendsId = getUserByUsername(username).getUserId(); //Vennen hvis profil vi vil se, her modtager vi deres username
        //Når vi har deres username, så kan vi finde deres id
        //vi kan ved hjælp af vores id og vennensId, tjekke om vi er venner.
        String friendStatus = "friends";
        String sql = "SELECT * FROM friends WHERE friendOne = ? and friendTwo =? and friendStatus =?";
        List<Friend> firstFriendList= jdbcTemplate.query(sql, new FriendRowmapper(), myId, friendsId, friendStatus);
        if(!firstFriendList.isEmpty()){
            return firstFriendList;
        }
        //vi skal køre metoden to forskellige gange, da vores navn fx kan være i friendOne eller friendTwo, alt efter hvem der sendte venandmodningen
        String sqlSecondCombination = "SELECT * FROM friends WHERE friendTwo = ? and friendOne = ? and friendStatus = ?";
        List<Friend> seccondFriendList = jdbcTemplate.query(sqlSecondCombination, new FriendRowmapper(), myId, friendsId, friendStatus);
        if(!seccondFriendList.isEmpty()){
            return seccondFriendList;
        }
        return null;
    }






}
