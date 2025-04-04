package apiassignment.wishlist.systemIntegrationTest;

import apiassignment.wishlist.model.User;
import apiassignment.wishlist.model.Wish;
import apiassignment.wishlist.model.Wishlist;
import apiassignment.wishlist.repository.WishlistRepository;
import apiassignment.wishlist.service.WishlistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@Transactional
@Rollback(true)
public class WishlistSystemTest {


    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private WishlistService wishlistService;

    @BeforeEach
    void setUp() {
    }

    //Testing that we can find more than 0 users
    @Test
    void testGetAllUser() throws SQLException {
        List<User> allUsers = wishlistService.getAllUsers();

        assertNotNull(allUsers);
        assertTrue(!allUsers.isEmpty());
    }

    //Forstår ikke helt den her metode i repository - Afventer Hani
    @Test
    void testSearchFriends() throws Exception {


    }

    //Forstår ikke helt den her metode i repository - Afventer Hani

    @Test
    void testIsThereAlreadyARelation() throws SQLException {

    }

    //Testing method to find user by username
    @Test
    void testGetUserByUsername() throws SQLException {

        //first test fail
        String failname = "test";
        assertEquals(null, wishlistService.getUserByUsername(failname));

        String approvedName = "zahaa";
        assertEquals("zahaa", wishlistService.getUserByUsername(approvedName).getName());
    }

    //Testing method to find user by id
    @Test
    void testGetUserById() throws SQLException {
        int failID = 100;
        assertEquals(null, wishlistService.getUserById(failID));

        int passedID = 1;
        User user = wishlistService.getUserById(1);
        assertEquals(user.getUserId(), wishlistService.getUserById(passedID).getUserId());
    }


    @Test
    void testLogin() throws SQLException {
        String name = "test";
        String password = "test";

        //Testing non existing user
        User failedUser = wishlistService.login(name, password);
        assertEquals(null, failedUser);

        //Testing existing credentials
        User passedUser = wishlistService.login("zahaa", "1234");
        assertEquals(passedUser.getName(), wishlistService.login("zahaa", "1234").getName());
        assertEquals(passedUser.getPassword(), wishlistService.login("zahaa","1234").getPassword());
    }

    @Test
    void testGetAllWishesByUserId() throws SQLException {

        //Testing if wishlist does not equals the same
        Wishlist wishlist = wishlistService.getAllWishesByUserId(1);
        Wishlist wishlistFail = new Wishlist(2,"test");
        assertNotEquals(wishlist.getWishlistName(),wishlistFail.getWishlistName());

        //Testing if wishlist does exist
        wishlistService.createWishList(3,"hej");
        Wishlist test = wishlistService.getAllWishesByUserId(3);
        assertEquals(test, wishlistService.getAllWishesByUserId(3));

        //returnerer null = ???
    }

    @Test
    void testGetAllWishesFromWishlistiD() throws SQLException {
        List<Wish> wish = wishlistService.getAllWishesByWishlistId(1);

        //Testing if wishlist does not contain wishes
        assertNotEquals(wish, wishlistService.getAllWishesFromWishlistId(99));

        //Testing if wishlist does contain wishes
        assertEquals(!wish.isEmpty(),!wishlistService.getAllWishesFromWishlistId(1).isEmpty());
    }

    @Test
    void testGetAllWishlistByUserId() throws SQLException {
        List<Wishlist> wishlist = wishlistService.getAllWishlistsByUserId(1);

        //Testing if wishlist does not exist with user
        assertNotEquals(wishlist, wishlistService.getAllWishlistsByUserId(99));

        //Testing if wishlist does exist with user
        assertEquals(!wishlist.isEmpty(), !wishlistService.getAllWishlistsByUserId(1).isEmpty());

    }

    @Test
    void testIsUsernameFree() throws SQLException {

        //Testing if username is not free
        String username = "zahaa";
        String usernamePass = "test";
        assertFalse(wishlistService.isUsernameFree(username));

        //Testing if username is free
        assertTrue(wishlistService.isUsernameFree(usernamePass));

    }

    @Test
    void testRegisterUser() throws SQLException {

        //Creating a test user
        User user = new User();
        user.setName("Test User");
        user.setUsername("testuser123");
        user.setPassword("secretpassword");
        User savedUser = wishlistService.registerUser(user);

        //Testing if user is created with key generated id
        assertNotNull(savedUser.getUserId());
        assertTrue(savedUser.getUserId() > 0);

        //Testing if the user is in the database
        User dbUser = wishlistService.getUserById(savedUser.getUserId());
        assertEquals("Test User", dbUser.getName());
        assertEquals("testuser123", dbUser.getUsername());
        assertEquals("secretpassword", dbUser.getPassword());
        assertEquals(2, dbUser.getRoleId());
    }

    @Test
    void testCreateWishList() throws SQLException {

        //Creating a test wishlist
        User user = wishlistService.getUserById(1);
        Wishlist wishlist = wishlistService.createWishList(user.getUserId(), "hej");

        //Testing if the wishlist has been created with an auto generated id
        assertNotNull(wishlist.getWishlistId(), "ID cannot be null");
        assertTrue(wishlist.getWishlistId() > 0, "Auto generated cannot be 0");

        //Finding the saved wishlist in the databse and testing if it is correctly managed
        Wishlist DBtest = wishlistService.getWishlistById(wishlist.getWishlistId());
        assertNotNull(DBtest);
        assertEquals(wishlist.getWishlistId(), DBtest.getWishlistId());
        assertEquals("hej", DBtest.getWishlistName());
    }

}
