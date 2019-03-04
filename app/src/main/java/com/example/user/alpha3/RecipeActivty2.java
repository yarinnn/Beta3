package com.example.user.alpha3;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecipeActivty2 extends AppCompatActivity {
    TextView RecipeName;
    ListView ingr, steps;
    List<String> ingrlist;
    List<String> stepslist;
    ImageView imageView;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_activty2);

        int i=getIntent().getIntExtra("Index", 0);
        RecipeName= findViewById(R.id.RecipeName);
        ingr= findViewById(R.id.ingr);
        imageView= findViewById(R.id.imageView);
        steps=findViewById(R.id.steps);
        ref=FirebaseDatabase.getInstance().getReference("Recipes").child(""+i);
        ingrlist=new ArrayList<>();
        stepslist=new ArrayList<>();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String url=dataSnapshot.child("imageURL").getValue(String.class);
                Picasso.get().load(url).into(imageView);
                RecipeName.setText(dataSnapshot.child("name").getValue(String.class));
                DataSnapshot ing = dataSnapshot.child("ingredients");
                ingrlist.clear();
                for (DataSnapshot d: ing.getChildren()){
                    String amount=d.child("name").getValue(String.class)+", "+d.child("quantity").getValue(String.class);
                    ingrlist.add(amount);
                }
                ArrayAdapter<String> adp = new ArrayAdapter<String>(RecipeActivty2.this,R.layout.support_simple_spinner_dropdown_item , ingrlist);
                ingr.setAdapter(adp);
                DataSnapshot step = dataSnapshot.child("steps");
                stepslist.clear();
                for (DataSnapshot d: step.getChildren()){
                    String s=d.getValue(String.class);
                    stepslist.add(s);
                }
                ArrayAdapter<String> adp2 = new ArrayAdapter<String>(RecipeActivty2.this,R.layout.support_simple_spinner_dropdown_item , stepslist);
                steps.setAdapter(adp2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}
