package apiassignment.wishlist.controller;

import apiassignment.wishlist.dto.DTOFriend;
import apiassignment.wishlist.model.User;
import apiassignment.wishlist.model.Wish;
import apiassignment.wishlist.model.Wishlist;
import apiassignment.wishlist.service.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.LinkedList;

import java.util.List;
import java.util.Map;

@Controller
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }


    @GetMapping("")
    public String homepage(){
        return "homepage";
    }

    @GetMapping("/register")
    public String registerAccount(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String checkRegister(@ModelAttribute User user, Model model){
        if(!wishlistService.isUsernameFree(user.getUsername())){
            model.addAttribute("notFree", true);
            return "register";
        }
        wishlistService.registerUser(user);
        return "redirect:/login";
    }


    @GetMapping("login")
    public String getLogin(){
        return "login";
    }

    @GetMapping("/profile")
    public String getProfile (Model model, HttpSession session) {
        if(!wishlistService.isLoggedIn(session)) {
            return "login";
        }
        User user = (User) session.getAttribute("user");
        if(!wishlistService.isLoggedIn(session)) {
            return "redirect:/login";
        }
        List<Wishlist> wishLists = wishlistService.getAllWishlistsByUserId(user.getUserId());

        //Capitalize first character in name
        String name = user.getName().substring(0, 1).toUpperCase() + user.getName().substring(1);



        model.addAttribute("wishlists", wishLists);
        model.addAttribute("name", name);
        return "profile";
    }

    @GetMapping("/wishlist/{id}")
    public String getWishlist (@PathVariable("id") int wishlistId, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Wishlist wishlist = wishlistService.getWishlistById(wishlistId);
        List<Wish> wishes = wishlistService.getAllWishesFromWishlistId(wishlistId);

        if (wishlist != null && wishlist.getWishlistName() != null) {
            String wishlistname = wishlist.getWishlistName();
            model.addAttribute("wishlistId",wishlistId);
            model.addAttribute("wishlistname", wishlistname);
        }
        model.addAttribute("wishes", wishes);

        return "wishlist";
    }


    @PostMapping("login")
    public String checkLogin(@RequestParam("checkUsername") String username, @RequestParam("checkUserpassword") String password,
                             HttpSession session, Model model){
        User user = wishlistService.login(username, password);
        if(user == null){
            model.addAttribute("wrongLogin", true);
            return "login";
        }
        session.setAttribute("user", user);
        session.setMaxInactiveInterval(3600);
        return "redirect:/profile";
    }


   /* @GetMapping("/profile")
    public String profil(HttpSession session, Model model){

        if(!wishlistService.isLoogedIn(session)){
            return "login";
        }

        User user = (User) session.getAttribute("user");
        Wishlist tempWishlist = wishlistService.getWishlistByuserId(user.getUserId());
        model.addAttribute("user", user);

        if(tempWishlist != null) {
            model.addAttribute("wishlist", tempWishlist);
        } else {
            return "profileEmptyWishlist";
        }

        return "profile";
    }*/


    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }

    @GetMapping("/create/wishlist")
    public String createWishlist(Model model, HttpSession session) {

        if(!wishlistService.isLoggedIn(session)) {
            return "login";
        }

        Wishlist wishlist = new Wishlist();
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("wishlist", wishlist);
        return "createWishList";
    }

    @PostMapping("/create/wishlist")
    public String saveWishList(@ModelAttribute Wishlist wishlist, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user == null) {
            return "redirect:/login";
        }

        wishlistService.createWishList(user.getUserId(), wishlist.getWishlistName() );
        return "redirect:/profile";
    }

    @PostMapping("/wishlist/delete/{id}")
    public String deleteWishlist(@PathVariable int id) {
        wishlistService.deleteAllWishesWithWishlistId(id);
        wishlistService.deleteWishlist(id);
        return "redirect:/profile";
    }

    @GetMapping("/profile/edit")
    public String editProfileSide(HttpSession session, Model model){
        if(!wishlistService.isLoggedIn(session)) {
            return "login";
        }
        User user = (User) session.getAttribute("user");

        model.addAttribute("user", user);
        model.addAttribute("updatedUser", new User());
        return "updateUser";
    }

    @PostMapping("/profile/edit")
    public String editProfile(@ModelAttribute User updatedUser, HttpSession session){
        if(!wishlistService.isLoggedIn(session)) {
            return "login";
        }
        wishlistService.updateUser(updatedUser);
        return "redirect:/profile";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if(!wishlistService.isLoggedIn(session)) {
            return "redirect:/login";
        }
        System.out.println("Deleting user with ID: " + id);

        wishlistService.deleteUser(id);
        return "redirect:/login";
    }

    @GetMapping("/wish/add/{wishlistId}")
    public String addWish(@PathVariable int wishlistId, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        Wish wish = new Wish();
        wish.setWishlistId(wishlistId);
        model.addAttribute("wish", wish);
        return "createWish";
    }

    @PostMapping("wish/save")
    public String saveWish(@ModelAttribute Wish wish, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user == null) {
            return "redirect:/login";
        }
        wishlistService.addWish(wish);
        return "redirect:/wishlist/" + wish.getWishlistId();
    }

    @GetMapping("wish/{id}/edit")
    public String editWish(Model model, @PathVariable int id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        Wish wish = wishlistService.getWishById(id);
        if (wish == null) {
            throw new IllegalArgumentException("Wish doesnt exits");
        }
        model.addAttribute("wish",wish);
        return "updateWish";
    }

    @PostMapping("/wish/update")
    public String updateWish(@ModelAttribute Wish wish, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        wishlistService.updateWish(wish);
        return "redirect:/wishlist/" + wish.getWishlistId();
    }

    @PostMapping("/wish/delete/{id}")
    public String deleteWish(@PathVariable int id, HttpSession session) {
        Wish wish = wishlistService.getWishById(id);
        wishlistService.deleteWish(id);

        return "redirect:/wishlist/" + wish.getWishlistId();
    }

    @GetMapping("wish/view/{id}")
    public String viewWish(@PathVariable int id, Model model, HttpSession session) {
        if(!wishlistService.isLoggedIn(session)) {
            return "redirect:/login";
        }
        Wish wish = wishlistService.getWishById(id);
        if(wish == null) {
            return "redirect:/profile";
        }

        model.addAttribute("wish", wish);


        return "wish";
    }

    @GetMapping("/admin")
    public String adminPanel(HttpSession session, Model model) {
        User loggedUser = (User) session.getAttribute("user");

        if(!wishlistService.isLoggedIn(session)) {
            return "redirect:/login";
        } else if(loggedUser.getRoleId() != 1) {
            return "redirect:/login";
        }

        List<User> getAllUsers = wishlistService.getAllUsers();
        model.addAttribute("getAllUsers", getAllUsers);
        return "adminPanel";
    }

    @GetMapping("/admin/addusers")
    public String adminPanelAddUser(HttpSession session, Model model) {
        User loggedUser = (User) session.getAttribute("user");

        if(!wishlistService.isLoggedIn(session)) {



            return "redirect:/login";



        } else if(loggedUser.getRoleId() != 1) {
            return "redirect:/login";
        }

        User user = new User();
        model.addAttribute("user", user);
        return "adminAddUser";
    }

    @PostMapping("/admin/register")
    public String AdminCheckRegister(@ModelAttribute User user, Model model){
        if(!wishlistService.isUsernameFree(user.getUsername())){
            model.addAttribute("notFree", true);
            return "redirect:/admin/addusers";
        }

        wishlistService.adminRegisterUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/delete/{id}")
    public String adminDeleteUser(@PathVariable int id, HttpSession session) {
        User loggedUser = (User) session.getAttribute("user");

        if(!wishlistService.isLoggedIn(session)) {



            return "redirect:/login";



        } else if(loggedUser.getRoleId() != 1) {
            return "redirect:/login";
        }

        wishlistService.deleteUser(id);
        return "redirect:/admin";
    }



    @GetMapping("/searchFriends")
    public String searchFriends(HttpSession session, Model model){
        if(!wishlistService.isLoggedIn(session)) {
            return "login";
        }
        String name = "";
        model.addAttribute("name", name);

        User user = (User) session.getAttribute("user");

        List<DTOFriend> listOfFriends = wishlistService.getFriendsCombined(user.getUserId());
        List<DTOFriend>listOfFriendRequest = wishlistService.receivedFriendRequestCombined(user.getUserId());
        List<DTOFriend>listOfSendFriendRequests = wishlistService.sendFriendRequestCombined(user.getUserId());

        if(listOfFriends != null){
            model.addAttribute("friends", listOfFriends);
        } else {
            model.addAttribute("noFriends", true);
        }
        if(listOfFriendRequest != null){
            model.addAttribute("friendRequests", listOfFriendRequest);
        } else {
            model.addAttribute("noFriendRequest", true);
        }
        if(listOfSendFriendRequests != null){
            model.addAttribute("sendRequests", listOfSendFriendRequests);
        } else {
            model.addAttribute("noSendRequests", true);
        }

        return "searchForFriends";
    }



    @GetMapping("/searchResultFriends")
    public String searchFriendsResult(HttpSession session, Model model, String name){
        if(!wishlistService.isLoggedIn(session)) {
            return "login";
        }
        User user = (User) session.getAttribute("user");
        if(wishlistService.searchFriends(name, user.getUserId()) == null){
            model.addAttribute("noPersonFound", true);
            return "searchResultFriends";
        }
        model.addAttribute("searchResult", wishlistService.searchFriends(name, user.getUserId()));
        model.addAttribute("found", true);
        return "searchResultFriends";
    }


    @PostMapping("/add/friend/{username}")
    public String addFriend(@PathVariable String username, Model model, HttpSession session){
        if(!wishlistService.isLoggedIn(session)) {
            return "login";
        }
        User user = (User) session.getAttribute("user");

        User addedFriend = wishlistService.getUserByUsername(username);

        String status = "requested";

        wishlistService.addFriend(user.getUserId(), addedFriend.getUserId(), status);


        return "redirect:/searchFriends";
    }


    @PostMapping("/accept/friend/{friendshipId}")
    public String acceptFriendRequest(@PathVariable int friendshipId, HttpSession session){
        if(!wishlistService.isLoggedIn(session)) {
            return "login";
        }
        String status = "friends";
        wishlistService.acceptFriend(friendshipId, status);
        return "redirect:/searchFriends";
    }


    @PostMapping("/remove/friend/{friendshipId}")
    public String removeFriend(@PathVariable int friendshipId, HttpSession session){
        if(!wishlistService.isLoggedIn(session)) {
            return "login";
        }
        wishlistService.removeFriend(friendshipId);
        return "redirect:/searchFriends";
    }

    @GetMapping("/profile/friends/{username}")
    public String myFriendsProfile(@PathVariable String username, HttpSession session, Model model){
        if(!wishlistService.isLoggedIn(session)) {
            return "login";
        }
        User user = (User) session.getAttribute("user");
        if(wishlistService.checkIfFriends(user.getUserId(), username) == null){
            model.addAttribute("You are not friends", true);
            return "homepage";
        }
        User friendUser = wishlistService.getUserByUsername(username);
        List<Wishlist> wishLists = wishlistService.getAllWishlistsByUserId(friendUser.getUserId());
        //Capitalize first character in name
        String name = friendUser.getName().substring(0, 1).toUpperCase() + friendUser.getName().substring(1);

        String imgpath = "../static.images/wishlist.png";

        model.addAttribute("wishlists", wishLists);
        model.addAttribute("name", name);
        model.addAttribute("imgpath", imgpath);


        return "friendsProfile";
    }





    @GetMapping("/share/{token}")
    public String shareWishlist(@PathVariable String token, Model model, HttpSession session) {
        User loggedUser = (User) session.getAttribute("user");
        boolean notLoggedIn;

        if(loggedUser == null || loggedUser.getUserId() != wishlistService.getUserIdByToken(token)) {
            Wishlist wishlist = wishlistService.getWishlistByToken(token);
            List<Wish> wishes = wishlistService.getAllWishesFromWishlistId(wishlist.getWishlistId());
            model.addAttribute("wishlist", wishlist);
            model.addAttribute("wishlistname", wishlist.getWishlistName());
            model.addAttribute("wishlistId", wishlist.getWishlistId());
            model.addAttribute("wishes", wishes);
            model.addAttribute("userName",wishlistService.getUsernameByToken(token));

            if(loggedUser == null) {
                notLoggedIn = true;
            } else {
                notLoggedIn = false;
            }
            model.addAttribute("notLoggedIn",notLoggedIn);
            return "sharedWishlist";
        } else {
            return "ownWishlist";
        }
    }

    @GetMapping("/shared/wish/{token}/{id}")
    public String sharedWishes(@PathVariable int id, @PathVariable String token, Model model, HttpSession session) {
        User loggedUser = (User) session.getAttribute("user");
        boolean notLoggedIn = false;

        if(loggedUser == null) {
            notLoggedIn = true;
        }

        model.addAttribute("token", token);
        model.addAttribute("notLoggedIn",notLoggedIn);

        Wish wish = wishlistService.getWishById(id);
        model.addAttribute("wish", wish);
        model.addAttribute("isReserved", wishlistService.isWishReservedById(id));

        return "sharedWish";
    }



    @PostMapping("/shared/wish/{token}/{id}")
    public String updateIsReserved (@PathVariable int id, @PathVariable String token, HttpSession session) {
        System.out.println("Updating wish with ID: " + id);

        if (wishlistService.isLoggedIn(session)) {
            wishlistService.updateIsReservedByWishId(id);
            System.out.println("Wish updated successfully.");
            return "redirect:/shared/wish/" + token +"/" + id;
        } else {
            System.out.println("User not logged in, redirecting to login.");
            return "redirect:/login";
        }

    }


    @GetMapping("/admin/update/{id}")
    public String adminUpdateUser(@PathVariable int id, HttpSession session, Model model) {
        User loggedUser = (User) session.getAttribute("user");
        User getUser = wishlistService.getUserById(id);
        if(!wishlistService.isLoggedIn(session)) {
            return "redirect:/login";
        } else if (loggedUser.getRoleId() != 1) {
            return "redirect:/login";
        }
        model.addAttribute("user", getUser);
        model.addAttribute("updatedUser", new User());
        return "adminUpdateUser";
    }

    @PostMapping("/admin/update")
    public String adminUpdateUser(@ModelAttribute User updatedUser, HttpSession session){

        if(!wishlistService.isLoggedIn(session)) {
            return "login";
        }

        wishlistService.updateUser(updatedUser);

        return "redirect:/admin";
    }



}
