package com.example.bookapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {

    EditText title_input, author_input, pages_input;
    Button update_button, delete_button;

    String id, author,title,pages;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        //get input values from activity update xml vals
        title_input=findViewById(R.id.title_input2);
        author_input=findViewById(R.id.author_input2);
        pages_input=findViewById(R.id.pages_input2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById((R.id.delete_button));


        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.updateData(id,title_input.getText().toString(),author_input.getText().toString(),pages_input.getText().toString());
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //confirm choice
                confirmDialog();
            }
        });

        //retrieve new data and call update
        getAndSetIntentData();

    }

    void getAndSetIntentData(){
        // get new values to update table values
        if(getIntent().hasExtra("id")&&getIntent().hasExtra("title")&&
            getIntent().hasExtra("author")&& getIntent().hasExtra("pages")){

            // get data from intent to update table with
            id = getIntent().getStringExtra("id");
            author = getIntent().getStringExtra("author");
            title = getIntent().getStringExtra("title");
            pages = getIntent().getStringExtra("pages");

            //set intent data- text views will contain data that already exists in the row
            title_input.setText(title);
            author_input.setText(author);
            pages_input.setText(pages);

        }else {
            Toast.makeText(this,"Incomplete Input",Toast.LENGTH_SHORT).show();
        }
    }

    // method to confirm user intent on action
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete "+title+" ?");
        builder.setMessage("Are you sure you want to delete this record?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing
            }
        });
        builder.create().show();
    }
}
