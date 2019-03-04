package com.example.user.alpha3;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity implements AdapterView.OnItemClickListener {

    EditText GeneralName;
    DatabaseReference ref;
    String name;
    boolean bool;
    ListView list;
    List<String> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        GeneralName = findViewById(R.id.GeneralName);
        list = findViewById(R.id.list);
        list.setOnItemClickListener(this);
        ref = FirebaseDatabase.getInstance().getReference("Recipes");
        itemList = new ArrayList<>();

        name = "";
        bool=false;

        refresh();
    }

    private void refresh() {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    String n = ds.child("name").getValue(String.class);
                    if(bool==false) {
                        if (n.toLowerCase().contains(name.toLowerCase()))
                            itemList.add(n);
                    }
                    else{
                        DataSnapshot d=ds.child("ingredients");
                        for (DataSnapshot di : d.getChildren()){
                            String ing=di.child("name").getValue(String.class);
                            if(ing.toLowerCase().contains(name.toLowerCase())){
                                itemList.add(n);
                            }

                        }
                    }
                }
                ArrayAdapter<String> adp = new ArrayAdapter<String>(Search.this,R.layout.support_simple_spinner_dropdown_item , itemList);
                list.setAdapter(adp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void search(View view) {
        bool=false;
        name = GeneralName.getText().toString();
        refresh();

    }

    public void SearchI(View view) {
        bool=true;
        name = GeneralName.getText().toString();
        refresh();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent re = new Intent(this,RecipeActivty2.class);
        re.putExtra("Index", i);
        startActivity(re);

    }
}
