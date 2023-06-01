package com.example.baemin.dao;

import com.example.baemin.dto.store.GetMenuResponse;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class MenuDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public MenuDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<GetMenuResponse> getMenus(Long storeId) {
        String sql = "select menu_id, category, name, price from menu " +
                "where store_id = :storeId";

        Map<String, Object> param = Map.of("storeId", storeId);

        return jdbcTemplate.query(sql, param,
                (rs, rowNum) -> new GetMenuResponse(
                        rs.getLong("menu_id"),
                        rs.getString("category"),
                        rs.getString("name"),
                        rs.getInt("price"))
        );
    }

}
