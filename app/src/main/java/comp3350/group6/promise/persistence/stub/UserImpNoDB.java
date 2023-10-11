package comp3350.group6.promise.persistence.stub;

import java.sql.Timestamp;
import java.util.ArrayList;

import comp3350.group6.promise.objects.FakeDB;
import comp3350.group6.promise.objects.Task;
import comp3350.group6.promise.objects.User;
import comp3350.group6.promise.persistence.UserDao;

public class UserImpNoDB implements UserDao {

    ArrayList<User> usersList;

    public UserImpNoDB() {
        this.usersList = new ArrayList<>();
        usersList.add(new User(0, "stub", "dummy"));
    }

    @Override
    public int addUser(String name, String introduction) {

        //add this user to the fake User database
        usersList.add(new User(usersList.size(), name, introduction));

        //return the generatedID to caller and increment
        return usersList.size() - 1;

    }

    @Override
    public int updateUserByUserId(int userId, String name, String introduction) {

        if (getUserByUserId(userId) != null) {
            getUserByUserId(userId).setName(name);
            getUserByUserId(userId).setIntro(introduction);
            return 1;
        } else {
            return 0;
        }

    }

    @Override
    public User getUserByUserId(int userId) {

        User getUser = null;
        boolean exists = false;

        for (int i = 0; i < usersList.size() && !exists; i++) {

            if (usersList.get(i).getUserID() == userId) {

                getUser = usersList.get(i);
                exists = true;

            }

        }

        return getUser;

    }

}
