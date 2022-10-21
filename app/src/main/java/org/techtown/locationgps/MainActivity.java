package org.techtown.locationgps;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.PowerManager;
//import android.support.v7.app.AppCompatActivity;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
//import org.techtown.locationgps.ProfileActivity

public class MainActivity<findViewById> extends AppCompatActivity implements PermissionLauncher{
    private Intent serviceIntent;

    ActivityResultLauncher<String> permissionLauncher;
    TextView userName,userEmail,userId;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;




    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        findViewById(R.id.goprofile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.gosearchfriend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FindFriendActivity.class);
                startActivity(intent);
            }
        });



        userEmail=(TextView)findViewById(R.id.email);



//        private void requestDozeDisable() {
//            Intent intent = new Intent();
//            String packageName = getPackageName();
//            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
//            if (pm.isIgnoringBatteryOptimizations(packageName))
//                intent.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
//            else {//from   w  w w. ja  v  a  2 s  .  c o  m
//                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
//                intent.setData(Uri.parse("package:" + packageName));
//            }
//            startActivity(intent);
//        }





//        public static void BatteryOptimization(Context context){
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                Intent intent = new Intent();
//                String packageName = context.getPackageName();
//                PowerManager pm = (PowerManager) context.getSystemService(POWER_SERVICE);
//                if (!pm.isIgnoringBatteryOptimizations(packageName)) {
//                    intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
//                    intent.setData(Uri.parse("package:" + "YOUR_PACKAGE_NAME"));
//                    context.startActivity(intent);
//                }
//            }
//        }

        //requestDozeDisable();


        PowerManager pm = (PowerManager) getApplicationContext().getSystemService(POWER_SERVICE);
        boolean isWhiteListing = false;
        if (android.os.Build.VERSION.SDK_INT >= VERSION_CODES.M) {
            isWhiteListing = pm.isIgnoringBatteryOptimizations(getApplicationContext().getPackageName());
        }
        if (!isWhiteListing) {
            Intent intent = new Intent();
            intent.setAction(android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
            startActivity(intent);
        }
        permissionRegisterLauncher();

        if (ActivityCompat.checkSelfPermission(this.getApplication().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED

        ) {
          /*  if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Snackbar.make(this.findViewById(R.id.layout), "Permission needed for progress!", Snackbar.LENGTH_INDEFINITE).setAction("ALLOW", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Request permission
                        setLaunchPermission();

                    }
                }).show();
            } else*/
            {
                // Request permission
                setLaunchPermission();
                //PermissionLauncher.launchPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION);
            }
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       /* if (serviceIntent!=null) {
            stopService(serviceIntent);
            serviceIntent = null;
        }*/
    }
    @Override
    public void permissionRegisterLauncher() {
        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if(result) {
                    if  (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    &&  ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
                        //Permission granted

                        //startService(new Intent(MainActivity.this, LocationService.class));
                    }
                }
                else {
                    //Permission denied
                    setLaunchPermission();
                    //Toast.makeText(MainActivity.this, "PLEASE GIVE PERMISSION ON SETTINGS", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void requestDozeDisable() {
        Intent intent = new Intent();
        String packageName = getPackageName();
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (pm.isIgnoringBatteryOptimizations(packageName))
            intent.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
        else {//from   w  w w. ja  v  a  2 s  .  c o  m
            intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + packageName));
        }
        startActivity(intent);
    }

    @Override
    public void setLaunchPermission() {
        System.out.println("Permission requested.");
        this.permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        this.permissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION);
        this.permissionLauncher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION);
        this.permissionLauncher.launch(Manifest.permission.INTERNET);
        permissionRegisterLauncher();
        /*ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                        Manifest.permission.INTERNET}, 0);*/
    }
















}
