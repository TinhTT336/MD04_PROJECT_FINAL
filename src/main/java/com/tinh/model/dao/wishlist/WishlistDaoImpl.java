package com.tinh.model.dao.wishlist;

import com.tinh.model.dao.product.ProductDao;
import com.tinh.model.entity.Wishlist;
import com.tinh.util.ConnectionDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class WishlistDaoImpl implements WishlistDao {
    @Autowired
    private ProductDao productDao;

    @Override
    public List<Wishlist> findByUserId(Integer userId) {
        Connection connection = ConnectionDatabase.openConnection();
        List<Wishlist> wishlists = new ArrayList<>();
        try {
            CallableStatement statement = connection.prepareCall("{CALL PROC_WISHLIST_FIND_BY_USER_ID(?)}");
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Wishlist wishlist = new Wishlist();
                wishlist.setWishlistId(resultSet.getInt("id"));
                wishlist.setUserId(resultSet.getInt("user_id"));
                wishlist.setProduct(productDao.findById(resultSet.getInt("product_id")));
                wishlists.add(wishlist);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }

        return wishlists;
    }

    @Override
    public void save(Wishlist wishlist) {
        Connection connection = ConnectionDatabase.openConnection();
        try {
            CallableStatement statement = connection.prepareCall("{CALL PROC_WISHLIST_ADD(?,?)}");
            statement.setInt(1, wishlist.getUserId());
            statement.setInt(2, wishlist.getProduct().getProductId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public void delete(Integer productId) {
        Connection connection = ConnectionDatabase.openConnection();
        try {
            CallableStatement statement = connection.prepareCall("{CALL PROC_WISHLIST_DELETE(?)}");
            statement.setInt(1, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }

    }

    @Override
    public Wishlist findByProductId(Integer productId,Integer userId) {
        Connection connection = ConnectionDatabase.openConnection();
        Wishlist wishlist=new Wishlist();
        try {
            CallableStatement statement = connection.prepareCall("{CALL PROC_WISHLIST_FIND_BY_PRODUCT_ID(?,?)}");
            statement.setInt(1, productId);
            statement.setInt(2,userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                wishlist.setWishlistId(resultSet.getInt("id"));
                wishlist.setUserId(resultSet.getInt("user_id"));
                wishlist.setProduct(productDao.findById(resultSet.getInt("product_id")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }

        return wishlist;
    }
}
