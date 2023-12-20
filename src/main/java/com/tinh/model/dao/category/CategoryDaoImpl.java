package com.tinh.model.dao.category;

import com.tinh.model.entity.Category;
import com.tinh.util.ConnectionDatabase;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryDaoImpl implements CategoryDao {
    public static int totalPagePagination;
    public static int totalPageSearch;

    @Override
    public List<Category> findAll() {
        Connection connection = null;
        List<Category> categories = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_CATEGORY_FIND_ALL()}");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setCategoryId(resultSet.getInt("id"));
                category.setCategoryName(resultSet.getString("name"));
                category.setCategoryStatus(resultSet.getBoolean("status"));
                category.setParentId(resultSet.getInt("parent_id"));
                category.setImage(resultSet.getString("image"));
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return categories;
    }

    @Override
    public boolean saveOrUpdate(Category category) {
        Connection connection = null;
        int check;
        try {
            connection = ConnectionDatabase.openConnection();
            if (category.getCategoryId() == 0) {
                CallableStatement statement = connection.prepareCall("{CALL PROC_CATEGORY_ADD(?,?,?)}");
                statement.setString(1, category.getCategoryName());
                statement.setInt(2, category.getParentId());
                statement.setString(3, category.getImage());
                check = statement.executeUpdate();
            } else {
                CallableStatement statement = connection.prepareCall("{CALL PROC_CATEGORY_EDIT(?,?,?,?)}");
                statement.setString(1, category.getCategoryName());
                statement.setInt(2, category.getParentId());
                statement.setString(3, category.getImage());
                statement.setInt(4, category.getCategoryId());
                check = statement.executeUpdate();
            }
            if (check > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return false;
    }

    @Override
    public Category findById(Integer id) {
        Connection connection = null;
        Category category = new Category();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_CATEGORY_FIND_BY_ID(?)}");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                category.setCategoryId(resultSet.getInt("id"));
                category.setCategoryName(resultSet.getString("name"));
                category.setCategoryStatus(resultSet.getBoolean("status"));
                category.setParentId(resultSet.getInt("parent_id"));
                category.setImage(resultSet.getString("image"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return category;
    }

    @Override
    public List<Category> search(String s) {
        Connection connection = null;
        List<Category> categories = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_CATEGORY_FIND(?,?)}");
            statement.setString(1, s);
            statement.registerOutParameter(2, java.sql.Types.INTEGER);
            ResultSet resultSet = statement.executeQuery();
            totalPageSearch = statement.getInt(2);
            while (resultSet.next()) {
                Category category = new Category();
                category.setCategoryId(resultSet.getInt("id"));
                category.setCategoryName(resultSet.getString("name"));
                category.setCategoryStatus(resultSet.getBoolean("status"));
                category.setParentId(resultSet.getInt("parent_id"));
                category.setImage(resultSet.getString("image"));
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return categories;
    }

    @Override
    public List<Category> pagination(Integer limit, Integer currentPage) {
        Connection connection = null;
        List<Category> categories = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_CATEGORY_PAGINATION(?,?,?)}");
            statement.setInt(1, limit);
            statement.setInt(2, currentPage);
            statement.registerOutParameter(3, java.sql.Types.INTEGER);
            ResultSet resultSet = statement.executeQuery();
            totalPagePagination = statement.getInt(3);
            while (resultSet.next()) {
                Category category = new Category();
                category.setCategoryId(resultSet.getInt("id"));
                category.setCategoryName(resultSet.getString("name"));
                category.setCategoryStatus(resultSet.getBoolean("status"));
                category.setParentId(resultSet.getInt("parent_id"));
                category.setImage(resultSet.getString("image"));
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return categories;
    }

    public Integer getTotalPagePagination(Integer limit, Integer currentPage) {
        Connection connection = null;
        int totalPage;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_CATEGORY_PAGINATION(?,?,?)}");
            statement.setInt(1, limit);
            statement.setInt(2, currentPage);
            statement.registerOutParameter(3, java.sql.Types.INTEGER);
            ResultSet resultSet = statement.executeQuery();
            totalPage = statement.getInt(3);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return totalPage;
    }

    public Integer getTotalPageSearch(String searchName) {
        Connection connection = null;
        int totalPage;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_CATEGORY_FIND(?,?)}");
            statement.setString(1, searchName);
            statement.registerOutParameter(2, java.sql.Types.INTEGER);
            ResultSet resultSet = statement.executeQuery();
            totalPage = statement.getInt(2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return totalPage;
    }

    @Override
    public Integer countCategory(Boolean b) {
        Connection connection = null;
        int countCategory;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_CATEGORY_COUNT_BY_STATUS(?,?)}");
            statement.setBoolean(1, b);
            statement.registerOutParameter(2, java.sql.Types.INTEGER);
            ResultSet resultSet = statement.executeQuery();
            countCategory = statement.getInt(2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return countCategory;
    }

    @Override
    public Boolean findByName(String name) {
        Connection connection = null;
        List<Category> categories = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_CATEGORY_FIND_BY_NAME(?)}");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
               return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return false;
    }

    @Override
    public Boolean checkCategoryNameExist(String categoryName) {
        Connection connection = null;
        List<Category> categories = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            PreparedStatement preparedStatement= connection.prepareStatement("SELECT * FROM category WHERE name=?");
            preparedStatement.setString(1, categoryName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return false;
    }

    @Override
    public List<Category> findByParentId(Integer parentId) {
        Connection connection = null;
        List<Category> categories = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_CATEGORY_FIND_BY_PARENT_ID(?)}");
            statement.setInt(1, parentId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setCategoryId(resultSet.getInt("id"));
                category.setCategoryName(resultSet.getString("name"));
                category.setCategoryStatus(resultSet.getBoolean("status"));
                category.setParentId(resultSet.getInt("parent_id"));
                category.setImage(resultSet.getString("image"));
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return categories;
    }


    @Override
    public void changeStatus(Integer id) {
        Connection connection = null;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_CATEGORY_CHANGE_STATUS(?)}");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public List<Category> findParent() {
        Connection connection = null;
        List<Category> categories = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_CATEGORY_FIND_PARENT()}");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setCategoryId(resultSet.getInt("id"));
                category.setCategoryName(resultSet.getString("name"));
                category.setCategoryStatus(resultSet.getBoolean("status"));
                category.setParentId(resultSet.getInt("parent_id"));
                category.setImage(resultSet.getString("image"));
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return categories;
    }
}
