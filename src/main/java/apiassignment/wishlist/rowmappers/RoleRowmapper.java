package apiassignment.wishlist.rowmappers;

import apiassignment.wishlist.model.Role;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRowmapper implements RowMapper<Role> {
    @Override
    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {

        Role role = new Role();

        role.setRoleId(rs.getInt("roleID"));
        role.setRoleName(rs.getString("roleName"));

        return role;
    }
}
