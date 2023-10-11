package comp3350.group6.promise.persistence.hsqldb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import comp3350.group6.promise.objects.User;
import comp3350.group6.promise.persistence.UserDao;
import comp3350.group6.promise.util.DBConnectorUtil;

public class UserImp implements UserDao {

    private User createUserObject(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("userId");
        String name = resultSet.getString("name");
        String introduction = resultSet.getString("introduction");
        return new User(id, name, introduction);
    }

    @Override
    public int addUser(String name, String introduction) {
        try (final Connection cnn = DBConnectorUtil.getConnection()) {
            assert cnn != null;
            PreparedStatement preparedStatement = cnn.prepareStatement("insert into User (name,introduction) values (?,?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, introduction);
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            return generatedKeys.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int updateUserByUserId(int userId, String name, String introduction) {
        try (final Connection cnn = DBConnectorUtil.getConnection()) {
            assert cnn != null;
            PreparedStatement preparedStatement = cnn.prepareStatement("update User set name = ?, introduction = ? where userId = ?");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, introduction);
            preparedStatement.setInt(3, userId);
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public User getUserByUserId(int userId) {

        try (final Connection cnn = DBConnectorUtil.getConnection()) {
            assert cnn != null;
            PreparedStatement preparedStatement = cnn.prepareStatement("select userId, name, introduction from User where userId = ?");
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createUserObject(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new User(userId, null, null);
    }
}
