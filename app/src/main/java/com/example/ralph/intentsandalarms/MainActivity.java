package com.example.ralph.intentsandalarms;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("ActivityLifecycle","On Create");
        if(savedInstanceState != null){
            savedInstanceState.getString("a");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("ActivityLifecycle","On Start");
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("Receiver","Battery Low in Activity");
            }
        };

        IntentFilter intentFilter = new IntentFilter("abcd");
        registerReceiver(broadcastReceiver,intentFilter);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("a","abcd");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("ActivityLifecycle","On Restart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ActivityLifecycle","On Resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("ActivityLifecycle","On pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("ActivityLifecycle","On Stop");
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ActivityLifecycle","On Destroy");
    }

    public void shareText(View view){

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,"ABCD");
        startActivity(Intent.createChooser(shareIntent,"Share text abcd"));

    }

    public void openURL(View view){

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_VIEW);
        String url = "http://gdgnd.org";
        shareIntent.setData(Uri.parse(url));
        startActivity(Intent.createChooser(shareIntent,"Share text abcd"));

    }

    public void openMail(View view){

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SENDTO);
        shareIntent.setData(Uri.parse("mailto:rohanraarora@gmail.com"));

        shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Custom Subject");
        shareIntent.putExtra(Intent.EXTRA_TEXT,"ABCD");

        startActivity(Intent.createChooser(shareIntent,"Open Mail"));

    }

    public void call(View view){

        int permissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        if(permissionGranted == PackageManager.PERMISSION_GRANTED){
            callUs();
        }
        else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},1);
        }



    }

    private void callUs(){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_CALL);
        shareIntent.setData(Uri.parse("tel:99897979"));
        startActivity(shareIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1){
            int grantResult = grantResults[0];
            if(grantResult == PackageManager.PERMISSION_GRANTED){
                callUs();;
            }else {
                Toast.makeText(this,"Permission Denied. ",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void broadcast(View view){
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent intent = new Intent(this, MyReceiver.class);
        intent.putExtra("a","abcd");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis() + 5000,60000,pendingIntent);
    }
}
