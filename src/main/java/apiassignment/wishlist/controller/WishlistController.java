package apiassignment.wishlist.controller;

import apiassignment.wishlist.model.User;
import apiassignment.wishlist.model.Wish;
import apiassignment.wishlist.model.Wishlist;
import apiassignment.wishlist.service.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        User user = (User) session.getAttribute("user");
        List<Wishlist> wishLists = wishlistService.getAllWishlistsByUserId(user.getUserId());

        //Capitalize first character in name
        String name = user.getName().substring(0, 1).toUpperCase() + user.getName().substring(1);

        String imgpath = "../static.images/wishlist.png";

        model.addAttribute("wishlists", wishLists);
        model.addAttribute("name", name);
        model.addAttribute("imgpath", imgpath);
        return "profile";
    }
    @GetMapping("/wishlist/{id}")
    public String getWishlist (@PathVariable("id") int wishlistId, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Wishlist wishlist = wishlistService.getWishlistById(wishlistId);
        List<Wish> wishes = wishlistService.getAllWishesFromWishlistId(wishlistId);

        if (wishlist != null && wishlist.getWishlistName() != null) {
            String wishlistname = wishlist.getWishlistName();
            model.addAttribute("wishlistname", wishlistname);
            System.out.println(wishlistname);
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

        if(!wishlistService.isLoogedIn(session)) {
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

    @GetMapping("/wish/add")
    public String addWish(Model model, HttpSession session) {
        if(!wishlistService.isLoogedIn(session)) {
            return "login";
        }
        User user = (User) session.getAttribute("user");
        model.addAttribute("user",user);
        model.addAttribute("wish", new Wish());
        return "createWish";
    }

    @PostMapping("/wish/save")
    public String saveWish(@ModelAttribute Wish wish, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        wishlistService.addWish(wish);
        return "redirect:/wishlist";
    }

    @GetMapping("wish/{id}/edit")
    public String editWish(Model model, @PathVariable int id) {
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
        return "redirect:/wishlist";
    }

    @PostMapping("/wish/delete/{id}")
    public String deleteWish(@RequestParam int id, HttpSession session) {
        wishlistService.deleteWish(id);
        return "redirect:/wishlist";
    }

    @GetMapping("/profile/edit")
    public String editProfileSide(HttpSession session, Model model){
        if(!wishlistService.isLoogedIn(session)) {
            return "login";
        }
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("updatedUser", new User());
        return "updateUser";
    }

    @PostMapping("/profile/edit")
    public String editProfile(@ModelAttribute User updatedUser, HttpSession session){
        if(!wishlistService.isLoogedIn(session)) {
            return "login";
        }
        wishlistService.updateUser(updatedUser);
        return "redirect:/profile";
    }

}
