package com.example.bookapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;

    MyDatabaseHelper myDB;
    ArrayList<String> book_id,book_title,book_author,book_pages;
    CustomAdapter customAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // initialization of main screen layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Books");

        // add elements on to the main screen
        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener((view) -> {

            // define behavior in the event of an add button press
            //@Override
            //public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
            recreate();
            //}
        });
        // Create a new SQLite database and variables to hold input to be added to the database
        myDB = new MyDatabaseHelper(MainActivity.this);
        book_id = new ArrayList<>();
        book_title = new ArrayList<>();
        book_author = new ArrayList<>();
        book_pages = new ArrayList<>();

        // call store data in array method
        storeDataInArrays();
        customAdapter =new CustomAdapter(MainActivity.this,this,book_id,book_title,book_author,book_pages);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1){
            recreate();
        }
    }


        // define behavior when add button is pressed
        // if empty-do nothing and output failed message to user
        // otherwise store input data in arrays
        void storeDataInArrays(){
            Cursor cursor= myDB.readAllData();
            if(cursor.getCount()==0){
                Toast.makeText(this,"No data Present",Toast.LENGTH_SHORT).show();
            }else{
                while (cursor.moveToNext()){
                    book_id.add((cursor.getString(0)));
                    book_title.add((cursor.getString(1)));
                    book_author.add((cursor.getString(2)));
                    book_pages.add((cursor.getString(3)));
                }
            }
        }
    }