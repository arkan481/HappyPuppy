package id.wuff.happypuppy.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * User Session Manager Class, is a shared preference to store any user information.
 */
public class AppSessionManager {
    private static AppSessionManager instance;
    private static Context context;
    private static final String TAG = "UserSession";
    private static final String KEY_FIRSTNAME = "FirstNameKey";
    private static final String KEY_LASTNAME = "LastNameKey";
    private static final String KEY_GENDER = "GenderKey";
    private static final String KEY_PHOTO = "PhotoKey";

    private AppSessionManager(Context context) {
        this.context = context;
    }

    public static synchronized AppSessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new AppSessionManager(context);
        }
        return instance;
    }

    public boolean setFirstname(String firstName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_FIRSTNAME,firstName);
        editor.apply();
        return true;
    }

    public boolean setGender(String gender) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_GENDER,gender);
        editor.apply();
        return true;
    }

    public boolean setPhoto(String photo) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_PHOTO, photo);
        editor.apply();
        return true;
    }

    public boolean setLastName(String lastName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_LASTNAME, lastName);
        editor.apply();
        return true;
    }

    public String getFirstName() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG,context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_FIRSTNAME,null);
    }

    public String getLastName() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG,context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_LASTNAME,null);
    }

    public String getPhoto() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG,context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_PHOTO,null);
    }

    public String getGender() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG,context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_GENDER,null);
    }
}
