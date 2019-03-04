package com.example.user.alpha3;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signin extends AppCompatActivity implements View.OnClickListener{
    FirebaseAuth mAuth;
    EditText editTextEmailS, PasswordS;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        editTextEmailS = (EditText)findViewById(R.id.editTextEmailS);
        PasswordS = (EditText)findViewById(R.id.PasswordS);
        progressBar = (ProgressBar)findViewById(R.id.pogressbar);
        findViewById(R.id.textViewSignup).setOnClickListener(this);
        findViewById(R.id.buttonLogin).setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
    }

    private void userLogin(){
        String email = editTextEmailS.getText().toString().trim();
        String password = PasswordS.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmailS.setError("Email is required");
            editTextEmailS.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmailS.setError("Please enter a valid email");
            editTextEmailS.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            PasswordS.setError("Password is required");
            PasswordS.requestFocus();
            return;
        }

        if(password.length()<6){
            PasswordS.setError("Minimum length of password should be 6");
            PasswordS.requestFocus();
            return;

        }


        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Signin.this, Search.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }
                else{
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.textViewSignup:
                finish();
                startActivity(new Intent(this, Register.class));
                break;
            case R.id.buttonLogin:
                userLogin();
                break;


        }

    }

}
