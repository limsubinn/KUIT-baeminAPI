package com.example.baemin.dao;

import com.example.baemin.dto.store.GetCategoryResponse;
import com.example.baemin.dto.store.GetStoreResponse;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class StoreDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public StoreDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<GetStoreResponse> getStoresByTipAndPrice(int deliveryTip, int leastOrderPrice) {
        String sql = "select store.store_id, store.name, store.category, store_delivery.delivery_tip_max, store_delivery.least_order_price " +
                "from store, store_delivery " +
                "where store.store_id = store_delivery.store_id " +
                "and delivery_tip_max <= :deliveryTip and least_order_price <= :leastOrderPrice";

        Map<String, Object> param = Map.of(
                "deliveryTip", deliveryTip,
                "leastOrderPrice", leastOrderPrice);

        return jdbcTemplate.query(sql, param,
                (rs, rowNum) -> new GetStoreResponse(
                        rs.getLong("store_id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getInt("delivery_tip_max"),
                        rs.getInt("least_order_price"))
        );
    }

    public List<GetStoreResponse> getStoresByTip(int deliveryTip) {
        String sql = "select store.store_id, store.name, store.category, store_delivery.delivery_tip_max, store_delivery.least_order_price " +
                "from store, store_delivery " +
                "where store.store_id = store_delivery.store_id " +
                "and delivery_tip_max <= :deliveryTip";

        Map<String, Object> param = Map.of("deliveryTip", deliveryTip);

        return jdbcTemplate.query(sql, param,
                (rs, rowNum) -> new GetStoreResponse(
                        rs.getLong("store_id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getInt("delivery_tip_max"),
                        rs.getInt("least_order_price"))
        );
    }

    public List<GetStoreResponse> getStoresByPrice(int leastOrderPrice) {
        String sql = "select store.store_id, store.name, store.category, store_delivery.delivery_tip_max, store_delivery.least_order_price " +
                "from store, store_delivery " +
                "where store.store_id = store_delivery.store_id " +
                "and least_order_price <= :leastOrderPrice";

        Map<String, Object> param = Map.of("leastOrderPrice", leastOrderPrice);

        return jdbcTemplate.query(sql, param,
                (rs, rowNum) -> new GetStoreResponse(
                        rs.getLong("store_id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getInt("delivery_tip_max"),
                        rs.getInt("least_order_price"))
        );
    }

    public List<GetStoreResponse> getStores() {
        String sql = "select store.store_id, store.name, store.category, store_delivery.delivery_tip_max, store_delivery.least_order_price " +
                "from store, store_delivery " +
                "where store.store_id = store_delivery.store_id";


        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new GetStoreResponse(
                        rs.getLong("store_id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getInt("delivery_tip_max"),
                        rs.getInt("least_order_price"))
        );
    }

    public List<GetCategoryResponse> getCategories() {
        String sql = "select distinct category from store";

        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new GetCategoryResponse(
                        rs.getString("category"))
        );
    }

    public boolean hasStore(Long storeId) {
        String sql = "select exists(select store_id from store where store_id=:storeId)";
        Map<String, Object> param = Map.of("storeId", storeId);
        return Boolean.FALSE.equals(jdbcTemplate.queryForObject(sql, param, boolean.class));
    }

}
