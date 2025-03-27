package apiassignment.wishlist.controller;

import apiassignment.wishlist.model.User;
import apiassignment.wishlist.model.Wishlist;
import apiassignment.wishlist.service.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }


    @GetMapping("/homepage")
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

    @GetMapping("profile")
    public String getProfile (Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("notLoggedIn", true);
            return "redirect:/login";
        }
        model.addAttribute("name", user.getName());
        return "profile";
    }


    @PostMapping("login")
    public String checkLogin(@RequestParam("username") String username, @RequestParam("password") String password,
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


    @GetMapping("/profile")
    public String profil(HttpSession session, Model model){

        if(!wishlistService.isLoogedIn(session)){
            return "homepage";
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
    }


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
        return "redirect:/profil";
    }



}
