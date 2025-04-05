package apiassignment.wishlist.controller;


import apiassignment.wishlist.model.User;
import apiassignment.wishlist.model.Wish;
import apiassignment.wishlist.model.Wishlist;
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

        session.setAttribute("user", testUser);
        when(wishlistService.isLoggedIn(any(HttpSession.class))).thenReturn(true);
        when(wishlistService.checkWishlistIdWithUserId(1,testUser.getUserId())).thenReturn(true);


        mockMvc.perform(get("/wishlist/1").session(session))
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

        when(wishlistService.checkWishlistIdWithUserId(1, testUser.getUserId())).thenReturn(true);
        when(wishlistService.isLoggedIn(any(HttpSession.class))).thenReturn(true);

        mockMvc.perform(get("/wish/add/1").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("createWish"));
    }

    //Unit test for GET edit wish site
    @Test
    void testEditWish() throws Exception {
        User testUser = new User(1, "test", "test", "test", 1, null);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", testUser);

        Wish wish = new Wish(1, 1, "test", "test", "test", true, 1);

        when(wishlistService.isLoggedIn(any(HttpSession.class))).thenReturn(true);
        when(wishlistService.checkWishIdWithUserId(1, testUser.getUserId())).thenReturn(true);
        when(wishlistService.checkWishIdWithUserIdWithoutFriends(1, testUser.getUserId())).thenReturn(true);
        when(wishlistService.getWishById(1)).thenReturn(wish);

        session.setAttribute("user", testUser);

        mockMvc.perform(get("/wish/1/edit").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("updateWish"));
    }

    @Test

    void testViewWish() throws Exception {
        User testUser = new User(1, "test", "test", "test", 1, null);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", testUser);

        Wish wish = new Wish(1, 1, "test", "test", "test", true, 1);

        when(wishlistService.isLoggedIn(any(HttpSession.class))).thenReturn(true);
        when(wishlistService.checkWishIdWithUserId(1, testUser.getUserId())).thenReturn(true);
        when(wishlistService.checkWishIdWithUserIdWithoutFriends(1, testUser.getUserId())).thenReturn(true);
        when(wishlistService.getWishById(1)).thenReturn(wish);

        mockMvc.perform(get("/wish/view/1").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("wish"))
                .andExpect(model().attributeExists("wish"))
                .andExpect(model().attribute("wish", wish))
                .andExpect(model().attribute("myWishes", true));
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
        Wish wish = new Wish(1, 1, "test", "test", "test", true, 1);

        when(wishlistService.getWishById(1)).thenReturn(wish);
        when(wishlistService.isLoggedIn(any(HttpSession.class))).thenReturn(true);

        mockMvc.perform(get("/shared/wish/1/1").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("sharedWish"));
    }




    //Post mapping test til register user
    @Test
    void testRegisterWithFreeUsername() throws Exception {

        when(wishlistService.registerUser(any())).thenReturn(new User());
        when(wishlistService.isUsernameFree(any())).thenReturn(true);

        mockMvc.perform(post("/register")
                        .param("id", "100")
                .param("name","moeh")
                .param("username", "abdi")
                .param("password","1234")
                        .param("roleID", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    //Post mapping test til register user uden free username
    @Test
    void testRegisterWithoutFreeUsername() throws Exception {

        mockMvc.perform(post("/register")
                .param("id","100")
                .param("name","tester")
                .param("username","123")
                .param("password","1234")
                .param("roleID", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    //Post mapping test til login
    @Test
    void testLoginUser() throws Exception {

        when(wishlistService.login(any(), any())).thenReturn(testUser);

        mockMvc.perform(post("/login")
                .param("checkUsername", "test")
                .param("checkUserpassword", "test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile"));

    }

    //Post mapping test til create wishlist
    @Test
    void testCreateWishlist() throws Exception {

        User user = new User(1, "test", "test", "test", 1, null);

        when(wishlistService.isLoggedIn(any(HttpSession.class))).thenReturn(true);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", user);

        mockMvc.perform(post("/create/wishlist").session(session)
                .param("wishlistName", "test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile"));
    }

    //Post mapping test til create wishlist without login
    @Test
    void testCreateWithlistWithoutLogin() throws Exception {

        when(wishlistService.isLoggedIn(any(HttpSession.class))).thenReturn(false);
        mockMvc.perform(post("/create/wishlist")
                        .param("wishlistName", "test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

    }

    //Post mapping test til edit user
    @Test
    void testEditProfile() throws Exception {

        when(wishlistService.isLoggedIn(any(HttpSession.class))).thenReturn(true);

        mockMvc.perform(post("/profile/edit")
                .param("name", testUser.getName())
                .param("username", testUser.getUsername())
                .param("password", testUser.getPassword())
                .param("userId", "1")
                .param("roleId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile"));

    }

    //Post mapping test til edit user uden login
    @Test
    void testEditProfileWithoutLogin() throws Exception {

        mockMvc.perform(post("/profile/edit")
                        .param("name", testUser.getName())
                        .param("username", testUser.getUsername())
                        .param("password", testUser.getPassword())
                        .param("userId", "1")
                        .param("roleId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

    }

    @Test
    void testDeleteUser() throws Exception {
        User user = new User(1, "test", "test", "test", 1, null);
        when(wishlistService.isLoggedIn(any(HttpSession.class))).thenReturn(true);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", user);

        mockMvc.perform(post("/delete/1").session(session)).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/login"));

    }

    @Test
    void testSaveWish() throws Exception {

        User user = new User(1, "test", "test", "test", 1, null);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", user);
        when(wishlistService.isLoggedIn(session)).thenReturn(true);

        mockMvc.perform(post("/wish/save").session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist/0"));
    }

    @Test
    void testUpdateWish() throws Exception {
        User user = new User(1, "test", "test", "test", 1, null);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", user);
        when(wishlistService.isLoggedIn(session)).thenReturn(true);
        when(wishlistService.checkWishIdWithUserId(1, user.getUserId())).thenReturn(true);

        Wish wish = new Wish();
        wish.setWishId(1);
        wish.setWishlistId(10);
        wish.setName("cykel");

        mockMvc.perform(post("/wish/update").session(session)
                        .param("wishId", String.valueOf(wish.getWishId()))
                        .param("wishlistId", String.valueOf(wish.getWishlistId()))
                        .param("wishName", wish.getName()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist/10"));
    }

    @Test
    void testDeleteWish() throws Exception {

        User user = wishlistService.getUserById(1);
        when(wishlistService.getWishById(anyInt())).thenReturn(new Wish());

        mockMvc.perform(post("/wish/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void testAdminRegister() throws Exception {

        when(wishlistService.adminRegisterUser(any())).thenReturn(new User());
        when(wishlistService.isUsernameFree(any())).thenReturn(true);

        mockMvc.perform(post("/admin/register"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));
    }

    @Test
    void testAdminDeleteUser() throws Exception {

        when(wishlistService.isLoggedIn(any(HttpSession.class))).thenReturn(true);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", testUser);

        mockMvc.perform(post("/admin/delete/1").session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));
    }

    @Test
    void testAdminUpdateUserPost() throws Exception {
        when(wishlistService.isLoggedIn(any(HttpSession.class))).thenReturn(true);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", testUser);
        mockMvc.perform(post("/admin/update").session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));

    }

}
