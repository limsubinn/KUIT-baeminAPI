package com.example.baemin.dao;

import com.example.baemin.dto.store.GetCategoryResponse;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class StoreDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public StoreDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<GetCategoryResponse> getCategories() {
        String sql = "select distinct category from store";

        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new GetCategoryResponse(
                        rs.getString("category"))
        );
    }

}
