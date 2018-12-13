package com.a6studios.cvr_waterpumpcontrol;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.a6studios.cvr_waterpumpcontrol.database.DatabaseHelper;
import com.a6studios.cvr_waterpumpcontrol.database.Pump;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Pump> pumps = new ArrayList<Pump>();
    RecyclerView recyclerView;
    PumpAdapter mAdapter;
    private DatabaseHelper db ;
    MainActivity a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getPermissionToReadSMS();
        a =this;
        db = new DatabaseHelper(this);

        if(db.getAllPumps().size()>0){
            pumps = db.getAllPumps();
        }

        recyclerView = findViewById(R.id.rvBlogs);
        mAdapter = new PumpAdapter(this,a, pumps,db);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,NewPump.class);
                startActivityForResult(i,1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void delete(){

    }

    void edit(String t,String c,String p){
        Intent i = new Intent(MainActivity.this, EditPump.class);
        i.putExtra("TITLE",t);
        i.putExtra("CONTENT",c);
        i.putExtra("POS",p);
        startActivityForResult(i,2);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode>0) {
            String title = data.getStringExtra("TITLE");
            String content = data.getStringExtra("CONTENT");
            long id = db.insertPump(title, content);
            Pump b = db.getPump(id);
            if (b != null) {
                pumps.add(0,b);

                // refreshing the list
                mAdapter.notifyDataSetChanged();

            }

        }
        else if(requestCode==2 && resultCode>0){
            String etitle = data.getStringExtra("TITLE");
            String econtent = data.getStringExtra("CONTENT");
            int p = Integer.parseInt(data.getStringExtra("POS"));
            Pump eb = pumps.get(p);
            eb.setLabel(etitle);
            eb.setPhno(econtent);
            db.updatePump(eb);
            mAdapter.notifyItemChanged(p);
        }
    }

    public void send(String p,String m){
        SmsManager smsManager = SmsManager.getDefault();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            getPermissionToReadSMS();
        } else {
            smsManager.sendTextMessage(p, null, m, null, null);
            Toast.makeText(this, "Message sent!", Toast.LENGTH_SHORT).show();
        }

    }

    private static final int READ_SMS_PERMISSIONS_REQUEST = 1;


    public void getPermissionToReadSMS() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_SMS)) {
                    Toast.makeText(this, "Please allow permission!", Toast.LENGTH_SHORT).show();
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_SMS},
                        READ_SMS_PERMISSIONS_REQUEST);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == READ_SMS_PERMISSIONS_REQUEST) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Read SMS permission granted", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Read SMS permission denied", Toast.LENGTH_SHORT).show();
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    }

