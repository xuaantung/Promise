package comp3350.group6.promise.persistence;

import comp3350.group6.promise.objects.User;

public interface UserDao {

    int addUser(String name, String introduction);

    int updateUserByUserId(int userId, String name, String introduction);

    User getUserByUserId(int userId) ;
}
