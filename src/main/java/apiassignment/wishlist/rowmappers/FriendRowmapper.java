package apiassignment.wishlist.rowmappers;

import apiassignment.wishlist.model.Friend;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FriendRowmapper implements RowMapper<Friend> {
    @Override
    public Friend mapRow(ResultSet rs, int rowNum) throws SQLException {
        Friend friend = new Friend();

        friend.setFriendshipId(rs.getInt("friendshipId"));
        friend.setFriendOne(rs.getInt("friendOne"));
        friend.setFriendTwo(rs.getInt("friendTwo"));
        friend.setFriendStatus(rs.getString("friendStatus"));

        return friend;
    }
}
