package com.charlie.repgps;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.charlie.location.GPSTracker;

public class MainActivity extends Activity {

	private double latitude  = 0;
	private double longitude = 0; 
	
	private TextView tvlat;
	private TextView tvlonj;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvlat = (TextView) findViewById(R.id.tv_lat);
        tvlonj = (TextView) findViewById(R.id.tv_lonj);
        check_gps();
    }
        
    private boolean check_gps() {
		GPSTracker gps = new GPSTracker(MainActivity.this);
		
        if(gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            tvlat.setText("latitud :"+latitude);
            tvlonj.setText("lonjitud :"+longitude);
            return true;
            //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }
        msg("GPS deshabilitado... habilitarlo o esperar un momento y reintentar");
        showSettingsAlert();
        return false;
	}
    
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);        
        alertDialog.setTitle("GPS settings");  
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");  
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });  
        alertDialog.show();
	}
    
    public void getCoords(View view){
    	check_gps();
    }
    
    private void log(String cad){
    	Log.d("charlie",cad);
    }
    
    private void msg(String cad){
    	Toast.makeText(this, cad, Toast.LENGTH_SHORT).show();
    }
}
