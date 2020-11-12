package com.trishasofttech.todosqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
RecyclerView recyclerView;
EditText etname,ettask, ettaskdesc;
Button btnsave;
SQLiteDatabase sd;
List<MyData> list;
    MyAdpater myAdapater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
         myAdapater= new MyAdpater();

        etname = findViewById(R.id.etname);
        ettask = findViewById(R.id.ettask);
        ettaskdesc = findViewById(R.id.ettaskdesc);
        btnsave = findViewById(R.id.btnsave);
        /*to create or open the sqlite database*/
        sd = openOrCreateDatabase("virudb", 0, null);
        /*to create the table if not exists*/
        sd.execSQL("create table if not exists virutable (task varchar(250), taskdesc varchar(255), name varchar(150))");
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*to insert the data into table*/
                sd.execSQL("insert into virutable values('"+ettask.getText().toString()+"', '"+ettaskdesc.getText().toString()+"', '"+etname.getText().toString()+"')");
                /*to clear the form*/
                etname.setText("");
                ettaskdesc.setText("");
                ettask.setText("");
                /*retrive the data from sqlitedatabase*/
                fetchdata();
            }
        });
    }

    private void fetchdata() {
        /*to fetch and store the data into cursor class from sqlite database*/
        Cursor cursor = sd.rawQuery("select * from virutable", null);
        cursor.moveToFirst();
        do {
            /*to pass the data record into the arraylist*/
            list.add(new MyData(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
        }
        while (cursor.moveToNext());

        recyclerView.setAdapter(myAdapater);

    }

    private class MyAdpater extends RecyclerView.Adapter<MyHolder> {
        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data, parent, false);
            MyHolder myHolder = new MyHolder(v);
            return myHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            MyData myData = list.get(position);
            holder.tvtaskdesc.setText(myData.getTaskdesc());
            holder.tvtask.setText(myData.getTask());
            holder.tvname.setText(myData.getName());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    private class MyHolder extends RecyclerView.ViewHolder{
        TextView tvname,tvtaskdesc,tvtask;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvname = itemView.findViewById(R.id.tvname);
            tvtask = itemView.findViewById(R.id.tvtask);
            tvtaskdesc = itemView.findViewById(R.id.tvtaskdesc);

        }
    }
}