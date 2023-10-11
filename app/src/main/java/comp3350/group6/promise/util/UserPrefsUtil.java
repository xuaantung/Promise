package comp3350.group6.promise.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import comp3350.group6.promise.R;

public class UserPrefsUtil {

    public static void saveUserCredentials(String email, String password, Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(activity.getString(R.string.saved_email), email);
        editor.putString(activity.getString(R.string.saved_password), password);
        editor.apply();
    }

    public static UserCredentials getSavedUserCredentials(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        String storedEmail = sharedPref.getString(activity.getString(R.string.saved_email), null);
        String storedPassword = sharedPref.getString(activity.getString(R.string.saved_password), null);
        return new UserCredentials(storedEmail, storedPassword);
    }

    public static void clearSavedUserCredentials(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(activity.getString(R.string.saved_email));
        editor.remove(activity.getString(R.string.saved_password));
        editor.apply();
    }

    public static class UserCredentials {
        private String email;
        private String password;
        public UserCredentials(String email, String password) {
            this.email = email;
            this.password = password;
        }
        public String getEmail() { return email; }
        public String getPassword() { return password; }
    }

}
