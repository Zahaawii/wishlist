package apiassignment.wishlist.rowmappers;

import apiassignment.wishlist.model.Wish;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WishRowmapper implements RowMapper<Wish> {
    @Override
    public Wish mapRow(ResultSet rs, int rowNum) throws SQLException {

        Wish wish = new Wish();

        wish.setWishlistId(rs.getInt("wishlistID"));
        wish.setWishId(rs.getInt("wishID"));
        wish.setName(rs.getString("wishName"));
        wish.setDescription(rs.getString("description"));
        wish.setPrice(rs.getDouble("price"));
        if(rs.getString("link") == null){
            wish.setLink("intet link");
        } else {
            wish.setLink(rs.getString("link"));
        }
        //lige nu er den null hele tiden, men en boolean kan ikke modtage null, så sætter den bare til false
        //wish.setReserved(rs.getBoolean("isReserved"));
        if(rs.getInt("isReserved") == 0) {
            wish.setReserved(false);
        } else {
            wish.setReserved(true);
        }

        return wish;
    }
}
