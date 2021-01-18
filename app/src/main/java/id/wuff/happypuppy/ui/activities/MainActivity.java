package id.wuff.happypuppy.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;

import id.wuff.happypuppy.R;
import id.wuff.happypuppy.utils.AlarmReceiver;
import id.wuff.happypuppy.utils.AppSessionManager;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navController = Navigation.findNavController(this, R.id.fragment);
        drawerLayout = findViewById(R.id.main_drawer);
        navigationView = findViewById(R.id.home_drawer);

        NavigationUI.setupWithNavController(navigationView, navController);

        checkUsername();

    }

    /**
     * Method to check if the user has set their username in the app
     * If the user has, then the username should be displayed in the head of the navigation view
     */
    private void checkUsername() {
        if(AppSessionManager.getInstance(this).getFirstName() != null) {
            View headerView = navigationView.getHeaderView(0);
            TextView textView = headerView.findViewById(R.id.et_header_name);
            textView.setText(AppSessionManager.getInstance(this).getFirstName());
        }
    }
}