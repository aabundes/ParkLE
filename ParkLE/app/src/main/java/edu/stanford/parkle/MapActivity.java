package edu.stanford.parkle;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

public class MapActivity extends Activity {

    Button logoutButton;
    GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        logoutButton = (Button)findViewById(R.id.logoutButton);


        FragmentManager FM = getFragmentManager();
        FragmentTransaction FT = FM.beginTransaction();

        MapFragmentClass MF = new MapFragmentClass();
        FT.add(R.id.mapLayout, MF);
        FT.commit();

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog popUpDialog = new AlertDialog.Builder(MapActivity.this).create();
                popUpDialog.setTitle("Logout?");
                popUpDialog.setMessage("Are you sure you want to logout?");
                popUpDialog.setButton(DialogInterface.BUTTON_POSITIVE,"Logout",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        // destroy the stored PW and login in the shared preferences
                        SharedPreferences.Editor editor = ParkLE.sharedPreferences.edit();
                        editor.clear();
                        editor.commit();

                        // take the user back to the login screen
                        finish();
                        Intent nextIntent = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(nextIntent);

                        Toast.makeText(getApplicationContext(),"Logged out successfully",Toast.LENGTH_SHORT).show();
                    }

                });

                popUpDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"Cancel",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        // close the dialog
                    }
                });

                popUpDialog.show();
            }
        });




    }
}
