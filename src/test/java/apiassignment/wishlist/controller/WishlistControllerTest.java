package apiassignment.wishlist.controller;


import apiassignment.wishlist.model.User;
import apiassignment.wishlist.model.Wish;
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
    @Autowired
    private HttpSession httpSession;

    @BeforeEach
    void setUp() {
        //User testUser = new User(1, "test", "test", "test", 1, null);
        //MockHttpSession session = new MockHttpSession();

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

    //Unit test for GET createwishlist
    @Test
    void testCreateWishList() throws Exception {

        session.setAttribute("user", testUser);

        when(wishlistService.isLoggedIn(any(HttpSession.class))).thenReturn(true);
        when(wishlistService.getAllWishlistsByUserId(anyInt())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/create/wishlist").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("createWishList"));
    }

    //Unit test for GET profile side
    @Test
    void testProfileSide() throws Exception {

        session.setAttribute("user", testUser);
        when(wishlistService.isLoggedIn(any(HttpSession.class))).thenReturn(true);

        mockMvc.perform(get("/profile/edit").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("updateUser"));
    }

    //Unit test for GET addWish site
    @Test
    void testAddWish() throws Exception {
        session.setAttribute("user", testUser);

        when(wishlistService.isLoggedIn(any(HttpSession.class))).thenReturn(true);

        mockMvc.perform(get("/wish/add/1").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("createWish"));
    }

    //Unit test for GET edit wish site
    @Test
    void testEditWish() throws Exception {
        session.setAttribute("user", testUser);
        Wish wish = new Wish(1, 1, "test", "test", "test", true, 1, 1);
        when(wishlistService.isLoggedIn(any(HttpSession.class))).thenReturn(true);
        when(wishlistService.getWishById(1)).thenReturn(wish);

        mockMvc.perform(get("/wish/1/edit").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("updateWish"));
    }

    @Test
    void testViewWish() throws Exception {
        session.setAttribute("user", testUser);
        Wish wish = new Wish(1, 1, "test", "test", "test", true, 1, 1);
        when(wishlistService.isLoggedIn(any(HttpSession.class))).thenReturn(true);
        when(wishlistService.getWishById(1)).thenReturn(wish);

        mockMvc.perform(get("/wish/view/1").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("wish"));
    }

    @Test
    void testAdminPanel() throws Exception {
        session.setAttribute("user", testUser);
        when(wishlistService.isLoggedIn(any(HttpSession.class))).thenReturn(true);

        mockMvc.perform(get("/admin").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("adminPanel"));
    }

    @Test
    void testAdminPanelAddUser() throws Exception {
        session.setAttribute("user", testUser);
        when(wishlistService.isLoggedIn(any(HttpSession.class))).thenReturn(true);

        mockMvc.perform(get("/admin/addusers").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("adminAddUser"));
    }

    @Test
    void testAdminUpdateUser() throws Exception {
        session.setAttribute("user", testUser);

        when(wishlistService.isLoggedIn(any(HttpSession.class))).thenReturn(true);
        when(wishlistService.getUserById(1)).thenReturn(testUser);
        mockMvc.perform(get("/admin/update/1").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("adminUpdateUser"));
    }

    @Test
    void testSharedWishes () throws Exception {
        session.setAttribute("user", testUser);
        Wish wish = new Wish(1, 1, "test", "test", "test", true, 1, 1);

        when(wishlistService.getWishById(1)).thenReturn(wish);3
        when(wishlistService.isLoggedIn(any(HttpSession.class))).thenReturn(true);

        mockMvc.perform(get("/shared/wish/1").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("sharedWish"));
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
