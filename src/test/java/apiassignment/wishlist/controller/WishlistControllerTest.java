package apiassignment.wishlist.controller;


import apiassignment.wishlist.model.User;
import apiassignment.wishlist.service.WishlistService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WishlistController.class)
public class WishlistControllerTest {

    User testUser = new User(1, "test", "test", "test", 1, null);
    MockHttpSession session = new MockHttpSession();


    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WishlistService wishlistService;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }

    //Unit test for GET homepage
    @Test
    void homepage() throws Exception {
        mockMvc.perform(get(""))
                .andExpect(status().isOk())
                .andExpect(view().name("homepage"));
    }

    //Unit test for GET register
    @Test
    void register() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    //Unit test for GET login
    @Test
    void login() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect((status().isOk()))
                .andExpect(view().name("login"));
    }

    @Test
    void profileWhenNotLoggedIn() throws Exception {
        when(wishlistService.isLoggedIn(any(HttpSession.class))).thenReturn(false);
        mockMvc.perform(get("/profile"))
                .andExpect((status().is3xxRedirection()))
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void profilWhenLoggedIn() throws Exception {


        session.setAttribute("user", testUser);

        when(wishlistService.isLoggedIn(any(HttpSession.class))).thenReturn(true);
        when(wishlistService.getAllWishlistsByUserId(anyInt())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/profile").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"));
    }

    //Unit test for GET wishlist
    @Test
    void testWishlist() throws Exception {
        mockMvc.perform(get("/wishlist/1"))
                .andExpect((status().isOk()))
                .andExpect(view().name("wishlist"));
    }

    //Unit test for Get logout
    @Test
    void testLogout() throws Exception {
        mockMvc.perform(get("/logout"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void testCreateWishList() throws Exception {

        session.setAttribute("user", testUser);

        when(wishlistService.isLoggedIn(any(HttpSession.class))).thenReturn(true);
        when(wishlistService.getAllWishlistsByUserId(anyInt())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/create/wishlist").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("createWishList"));
    }

    @Test
    void testProfileSide() throws Exception {

        session.setAttribute("user", testUser);
        when(wishlistService.isLoggedIn(any(HttpSession.class))).thenReturn(true);
        when(wishlistService.getAllWishlistsByUserId(anyInt())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/profile/edit").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("updateUser"));
    }

    //Virker ikke, afvent.
    @Test
    void testRegister() throws Exception {
        mockMvc.perform(post("/register")
                .param("name","zahaa")
                .param("username", "zahaa")
                .param("password","1234"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("login"));

        verify(wishlistService, times(1)).registerUser(any(User.class));
    }
}
