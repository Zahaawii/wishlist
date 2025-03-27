package apiassignment.wishlist.rowmappers;

import apiassignment.wishlist.model.Wishlist;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WishlistRowmapper implements RowMapper<Wishlist> {
    @Override
    public Wishlist mapRow(ResultSet rs, int rowNum) throws SQLException {
        Wishlist wishlist = new Wishlist();

        wishlist.setWishlistId(rs.getInt("wishlistID"));
        wishlist.setWishlistName(rs.getString("wishlistName"));


        return wishlist;
    }
}
