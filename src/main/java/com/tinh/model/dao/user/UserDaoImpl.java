package com.tinh.model.dao.user;

import com.tinh.model.dto.user.UserCheckoutDTO;
import com.tinh.model.dto.user.UserLoginDTO;
import com.tinh.model.dto.user.UserRegisterDTO;
import com.tinh.model.entity.User;
import com.tinh.util.ConnectionDatabase;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    public static int totalPageSearch;
    public static int totalPagePagination;

    //public static int totalUserSearch;
    @Override
    public List<User> findAll() {
        Connection connection = null;
        List<User> users = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_USER_FIND_ALL()}");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setAddress(resultSet.getString("address"));
                user.setPhone(resultSet.getString("phone"));
                user.setRole(resultSet.getBoolean("role"));
                user.setUserStatus(resultSet.getBoolean("status"));
                user.setAvatar(resultSet.getString("avatar"));
                user.setPassword(resultSet.getString("password"));

                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return users;
    }

    @Override
    public boolean saveOrUpdate(User user) {
        Connection connection = null;
        int check;
        try {
            connection = ConnectionDatabase.openConnection();
            if (user.getUserId() == 0) {
                CallableStatement statement = connection.prepareCall("{CALL PROC_USER_ADD(?,?,?,?,?)}");
                statement.setString(1, user.getUsername());
                statement.setString(2, user.getEmail());
                statement.setString(3, user.getAddress());
                statement.setString(4, user.getPhone());
                statement.setString(5, user.getPassword());
                check = statement.executeUpdate();
            } else {
                CallableStatement statement = connection.prepareCall("{CALL PROC_USER_EDIT(?,?,?,?,?,?)}");
                statement.setString(1, user.getUsername());
                statement.setString(2, user.getEmail());
                statement.setString(3, user.getAddress());
                statement.setString(4, user.getPhone());
                statement.setString(5, user.getAvatar());
                statement.setInt(6, user.getUserId());
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
    public User findById(Integer id) {
        Connection connection = null;
        User user = new User();

        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_USER_FIND_BY_ID(?)}");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user.setUserId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setAddress(resultSet.getString("address"));
                user.setPhone(resultSet.getString("phone"));
                user.setRole(resultSet.getBoolean("role"));
                user.setUserStatus(resultSet.getBoolean("status"));
                user.setAvatar(resultSet.getString("avatar"));
                user.setPassword(resultSet.getString("password"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return user;
    }


    @Override
    public List<User> pagination(Integer a, Integer b) {
        Connection connection = null;
        List<User> users = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_USER_PAGINATION(?,?,?)}");
            statement.setInt(1, a);
            statement.setInt(2, b);
            statement.registerOutParameter(3, java.sql.Types.INTEGER);
            ResultSet resultSet = statement.executeQuery();
            totalPagePagination = statement.getInt(3);
            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setAddress(resultSet.getString("address"));
                user.setPhone(resultSet.getString("phone"));
                user.setRole(resultSet.getBoolean("role"));
                user.setUserStatus(resultSet.getBoolean("status"));
                user.setAvatar(resultSet.getString("avatar"));
                user.setPassword(resultSet.getString("password"));

                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return users;
    }

    @Override
    public void changeStatus(Integer id) {
        Connection connection = null;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_USER_CHANGE_STATUS(?)}");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public void setRole(Integer id) {
        Connection connection = null;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_USER_SET_ROLE(?)}");
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }

    }

    @Override
    public User findByMail(String mail) {
        Connection connection = null;
        User user = null;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_USER_FIND_BY_MAIL(?)}");
            statement.setString(1, mail);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user = new User();
                user.setUserId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setAddress(resultSet.getString("address"));
                user.setPhone(resultSet.getString("phone"));
                user.setRole(resultSet.getBoolean("role"));
                user.setUserStatus(resultSet.getBoolean("status"));
                user.setAvatar(resultSet.getString("avatar"));
                user.setPassword(resultSet.getString("password"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return user;
    }

    @Override
    public Integer countUserByStatus(Boolean b) {
        Connection connection = null;
        int countUser = 0;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_USER_COUNT_BY_STATUS(?,?)}");
            statement.setBoolean(1, b);
            statement.registerOutParameter(2, java.sql.Types.INTEGER);
            statement.executeUpdate();
            countUser = statement.getInt(2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return countUser;
    }

    @Override
    public List<User> search(String searchName) {
        Connection connection = null;
        List<User> users = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_USER_FIND(?,?,?)}");
            statement.setString(1, searchName);
            statement.registerOutParameter(2, java.sql.Types.INTEGER);
            statement.registerOutParameter(3, java.sql.Types.INTEGER);

            ResultSet resultSet = statement.executeQuery();
//            totalUserSearch=statement.getInt(2);
            totalPageSearch = statement.getInt(3);
            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setAddress(resultSet.getString("address"));
                user.setPhone(resultSet.getString("phone"));
                user.setRole(resultSet.getBoolean("role"));
                user.setUserStatus(resultSet.getBoolean("status"));
                user.setAvatar(resultSet.getString("avatar"));
                user.setPassword(resultSet.getString("password"));

                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
//        totalUserSearch=users.size();
        return users;
    }

    @Override
    public Boolean register(UserRegisterDTO user) {
        Connection connection = null;
        int check;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement statement = connection.prepareCall("{CALL PROC_USER_ADD(?,?,?,?,?)}");
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getAddress());
            statement.setString(4, user.getPhone());
            statement.setString(5, user.getPassword());
            check = statement.executeUpdate();

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
    public void changePassword(String password, Integer userId) {
        Connection connection = ConnectionDatabase.openConnection();
        try {
            CallableStatement statement = connection.prepareCall("{CALL PROC_USER_CHANGE_PASSWORD(?,?)}");
            statement.setString(1, password);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }


}
