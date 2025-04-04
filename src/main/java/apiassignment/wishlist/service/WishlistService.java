package apiassignment.wishlist.service;


import apiassignment.wishlist.dto.DTOFriend;
import apiassignment.wishlist.model.*;
import apiassignment.wishlist.model.Wish;
import apiassignment.wishlist.repository.WishlistRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishlistService {

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

    public User login(String username, String password) {
        return wishlistRepository.login(username, password);
    }

    public boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("user") != null;
    }

    public Wishlist getAllWishesByUserId(int userId) {
        return wishlistRepository.getAllWishesByUserId(userId);
    }

    public List<Wishlist> getAllWishlistsByUserId(int id) {
        return wishlistRepository.getAllWishlistsByUserId(id);
    }

    public List<Wish> getAllWishesFromWishlistId(int id) {
        return wishlistRepository.getAllWishesFromWishlistId(id);
    }

    public boolean isWishReservedById(int id) {
        return wishlistRepository.isWishReservedById(id);
    }

    public void updateIsReservedByWishId(int wishId) {
        wishlistRepository.updateIsReservedByWishId(wishId);
    }

    public String getWishlistTokenByWishId(int id) {
        return wishlistRepository.getWishlistTokenByWishId(id);
    }

    public User getUserByUsername(String username) {
        return wishlistRepository.getUserByUsername(username);
    }

    public User getUserById(int id) {
        return wishlistRepository.getUserById(id);
    }

    public boolean isUsernameFree(String username) {
        return wishlistRepository.isUsernameFree(username);
    }

    public User registerUser(User user) {
        return wishlistRepository.registerUser(user);
    }

    public Wishlist createWishList(int userID, String name) {
        return wishlistRepository.createWishList(userID, name);
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

    public User updateUser(User user) {
        return wishlistRepository.updateUser(user);
    }

    public List<User> searchFriends(String name, int id) {
        return wishlistRepository.searchFriends(name, id);
    }

    public void addFriend(int friendOneId, int friendTwoId, String status) {
        wishlistRepository.addFriend(friendOneId, friendTwoId, status);
    }

    public void acceptFriend(int friendshipId, String status) {
        wishlistRepository.acceptFriend(friendshipId, status);
    }

    public void removeFriend(int friendshipId) {
        wishlistRepository.removeFriend(friendshipId);
    }

    //følgnede metode tager en liste af users og en liste af friend, og kombinere til en liste af DTOFriend
    public List<DTOFriend> combineFriendAndUserLists(List<DTOFriend> combinedFriend, List<User> userInfo, List<Friend> friendInfo) {
        if (userInfo == null) {
            return null;
        }
        for (User i : userInfo) {
            DTOFriend temp = new DTOFriend();
            temp.setName(i.getName());
            temp.setUsername(i.getUsername());
            combinedFriend.add(temp);
        }
        int itr = 0;
        for (DTOFriend b : combinedFriend) {

            b.setFriendship(friendInfo.get(itr).getFriendshipId());
            itr++;
        }

        return combinedFriend;
    }

    public List<User> getFriendRequest(int userId) {
        return wishlistRepository.getFriendRequestName(userId);
    }

        public Wishlist getWishlistByToken (String token){
            return wishlistRepository.getWishlistByToken(token);
        }
        public int getUserIdByToken (String token){
            return wishlistRepository.getUserIdByToken(token);
        }
        public int getUserIdByWishlistId ( int id){
            return wishlistRepository.getUserIdByWishlistId(id);
        }
        public String getUsernameByToken (String token){
            return wishlistRepository.getUsernameByToken(token);
        }
        public void deleteWishlist ( int id){
            wishlistRepository.deleteWishlist(id);
        }
        public void deleteAllWishesWithWishlistId ( int id){
            wishlistRepository.deleteAllWishesWithWishlistId(id);
        }

        public List<Friend> getFriendRequestId ( int userId){
            return wishlistRepository.getFriendRequestId(userId);
        }
        public List<DTOFriend> receivedFriendRequestCombined ( int userId){
            List<DTOFriend> combinedFriend = new ArrayList<>();
            List<User> userInfo = wishlistRepository.getFriendRequestName(userId);
            List<Friend> friendInfo = wishlistRepository.getFriendRequestId(userId);
            return combineFriendAndUserLists(combinedFriend, userInfo, friendInfo);
        }

        public List<Friend> sendFriendRequestId ( int userId){
            return wishlistRepository.sendFriendRequestId(userId);
        }
        public List<User> sendFriendRequestName ( int userId){
            return wishlistRepository.sendFriendRequestName(userId);
        }

        public List<DTOFriend> sendFriendRequestCombined ( int userId){
            List<DTOFriend> combinedFriend = new ArrayList<>();
            List<User> userInfo = wishlistRepository.sendFriendRequestName(userId);
            List<Friend> friendInfo = wishlistRepository.sendFriendRequestId(userId);
            return combineFriendAndUserLists(combinedFriend, userInfo, friendInfo);
        }


        public List<User> getFriends ( int userId){
            return wishlistRepository.getFriendsName(userId);
        }

        public List<Friend> getFriendsId ( int userId){
            return wishlistRepository.getFriendsId(userId);
        }

        public List<DTOFriend> getFriendsCombined ( int userId){
            List<DTOFriend> combinedFriend = new ArrayList<>();
            List<User> userInfo = wishlistRepository.getFriendsName(userId);
            List<Friend> friendInfo = wishlistRepository.getFriendsId(userId);
            return combineFriendAndUserLists(combinedFriend, userInfo, friendInfo);
        }

        public List<Friend> checkIfFriends ( int myId, String username){
            return wishlistRepository.checkIfFriends(myId, username);
        }



}


