package com.a6studios.cvr_waterpumpcontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditPump extends AppCompatActivity {
    EditText et_title ,et_content;
    String pos;

    public void onBackPressed(){
        Intent i= new Intent();
        setResult(-2,i);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pump);

        et_content = findViewById(R.id.edit_content);
        et_title = findViewById(R.id.edit_title);
        Intent i = getIntent();
        pos = i.getStringExtra("POS");
        et_title.setText(i.getStringExtra("TITLE"));
        et_content.setText(i.getStringExtra("CONTENT"));


    }

    public void edit(View view) {
        String title = et_title.getText().toString();
        String content = et_content.getText().toString();

        if(title.isEmpty()){
            Toast.makeText(getApplicationContext(),"Title should be filled", Toast.LENGTH_SHORT).show();
        }
        if(content.isEmpty()){
            Toast.makeText(getApplicationContext(),"Content should be filled", Toast.LENGTH_SHORT).show();
        }
        if(!title.isEmpty()&&!content.isEmpty()){
            Intent i= new Intent();
            i.putExtra("TITLE",title);
            i.putExtra("CONTENT",content);
            i.putExtra("POS",pos);
            setResult(2,i);
            finish();
        }

    }
}
