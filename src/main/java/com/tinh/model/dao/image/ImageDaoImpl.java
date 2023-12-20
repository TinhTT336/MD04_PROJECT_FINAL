package com.tinh.model.dao.image;

import com.tinh.model.entity.Image;
import com.tinh.util.ConnectionDatabase;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ImageDaoImpl implements ImageDao {

    @Override
    public List<Image> findAll() {
        Connection connection = null;
        List<Image> imageList = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_IMAGE_FIND_ALL()}");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Image image = new Image();
                image.setImageId(resultSet.getInt("id"));
                image.setProductId(resultSet.getInt("product_id"));
                image.setSrc(resultSet.getString(resultSet.getString("src")));
                imageList.add(image);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return imageList;
    }

    @Override
    public List<Image> findByProductId(int productId) {
        Connection connection = null;
        List<Image> imageList = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_IMAGE_FIND_BY_PRODUCT_ID(?)}");
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Image image = new Image();
                image.setImageId(resultSet.getInt("id"));
                image.setProductId(resultSet.getInt("product_id"));
                image.setSrc(resultSet.getString("src"));
                imageList.add(image);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return imageList;
    }

    @Override
    public boolean saveOrUpdate(Image image) {
        Connection connection = null;
        int check;
        try {
            connection = ConnectionDatabase.openConnection();
            if (image.getImageId() == 0) {
                CallableStatement statement = connection.prepareCall("{CALL PROC_IMAGE_ADD(?,?)}");
                statement.setInt(1, image.getProductId());
                statement.setString(2, image.getSrc());
                check = statement.executeUpdate();
            } else {
                CallableStatement statement = connection.prepareCall("{CALL PROC_IMAGE_EDIT(?,?)}");
                statement.setString(1, image.getSrc());
                statement.setInt(2, image.getImageId());
                check = statement.executeUpdate();
            }
            if (check > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public void delete(Integer productId) {
        Connection connection = null;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_IMAGE_DELETE(?)}");
            statement.setInt(1,productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }
}
