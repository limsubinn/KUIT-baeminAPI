package com.example.baemin.dao;

import com.example.baemin.dto.user.PostAddressRequest;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Objects;

@Repository
public class AddressDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AddressDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public long createAddress(PostAddressRequest postAddressRequest) {
        String sql = "insert into address(user_id, default_address, detail_address, address_type, position_x, position_y) " +
                "values(:userId, :defaultAddress, :detailAddress, :type, :x, :y)";

        SqlParameterSource param = new BeanPropertySqlParameterSource(postAddressRequest);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, param, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }
}
