package com.a6studios.cvr_waterpumpcontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewPump extends AppCompatActivity {
    EditText et1 ;
    EditText et2;
    Button bt ;
    public void onBackPressed(){
        Intent i= new Intent();
        setResult(-2,i);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pump);
        et1 = findViewById(R.id.new_title);
        et2 = findViewById(R.id.new_content);
        bt = findViewById(R.id.btn);



    }

    public void post(View view) {
        String title = et1.getText().toString();
        String content = et2.getText().toString();

        if(title.isEmpty()){
            Toast.makeText(this,"Title should be filled", Toast.LENGTH_SHORT).show();
        }
        if(content.isEmpty()){
            Toast.makeText(this,"Content should be filled", Toast.LENGTH_SHORT).show();
        }
        if(!title.isEmpty()&&!content.isEmpty()){
            Intent i= new Intent();
            i.putExtra("TITLE",title);
            i.putExtra("CONTENT",content);
            setResult(1,i);
            finish();
        }
    }

}
