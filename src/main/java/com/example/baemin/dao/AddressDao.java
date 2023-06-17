package com.example.baemin.dao;

import com.example.baemin.dto.user.GetAddressResponse;
import com.example.baemin.dto.user.PostAddressRequest;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class AddressDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AddressDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public long createAddress(long userId, PostAddressRequest postAddressRequest) {
        String sql = "insert into address(user_id, default_address, detail_address, address_type, position_x, position_y) " +
                "values(:userId, :defaultAddress, :detailAddress, :type, :x, :y)";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("defaultAddress", postAddressRequest.getDefaultAddress())
                .addValue("detailAddress", postAddressRequest.getDetailAddress())
                .addValue("type", postAddressRequest.getType())
                .addValue("x", postAddressRequest.getX())
                .addValue("y", postAddressRequest.getY());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, param, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public List<GetAddressResponse> getAddress(long userId) {
        String sql = "select address_id, default_address, detail_address, address_type, position_x, position_y, status from address " +
                "where user_id = :userId";

        Map<String, Object> param = Map.of("userId", userId);

        return jdbcTemplate.query(sql, param,
                (rs, rowNum) -> new GetAddressResponse(
                        rs.getLong("address_id"),
                        rs.getString("default_address"),
                        rs.getString("detail_address"),
                        rs.getString("address_type"),
                        rs.getDouble("position_x"),
                        rs.getDouble("position_y"),
                        rs.getString("status"))
        );
    }

    public int updateStatus(long addressId, String status) {
        String sql = "update address set status=:status where address_id=:addressId";
        Map<String, Object> param = Map.of(
                "status", status,
                "addressId", addressId);
        return jdbcTemplate.update(sql, param);
    }

    public boolean hasAddress(long addressId) {
        String sql = "select exists(select address_id from address where address_id=:addressId)";
        Map<String, Object> param = Map.of("addressId", addressId);
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, param, boolean.class));
    }

    public boolean matchUser(long userId, long addressId) {
        String sql = "select exists" +
                "(select address_id from address " +
                "where address_id=:addressId and user_id=:userId)";

        Map<String, Object> param = Map.of(
                "addressId", addressId,
                "userId", userId);

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, param, boolean.class));
    }

}
