package com.example.healthgo;



import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginDoctor extends AppCompatActivity {
    EditText userEmail,password,hosid;
    Button login;
    FirebaseAuth Auth;
    TextView signup,resetpassword;

    FirebaseUser user;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_doctor);
        userEmail=findViewById(R.id.user_email);
        password=findViewById(R.id.password);
        hosid=findViewById(R.id.hosid);
        login=findViewById(R.id.loginbtn);
        signup=findViewById(R.id.signup);
        resetpassword=findViewById(R.id.forgetpassword);
        Auth=FirebaseAuth.getInstance();
        user=Auth.getCurrentUser();

        try{
            login.setOnClickListener(v -> {
                String id=hosid.getText().toString();
                String e=userEmail.getEditableText().toString();
                String p=password.getEditableText().toString();
                Auth.signInWithEmailAndPassword(e,p).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        if(id.length()!=0 ){
                            char[] k=id.toCharArray();
                            int ctr=0;
                            for(int i=0;i<id.length();i++){
                                if(k[i]=='$'){
                                    ctr++;
                                }
                            }
                            if(ctr==2){

                                    startActivity(new Intent(LoginDoctor.this,DoctorDashboard.class));


                                finish();
                            }
                            else{
                                Toast.makeText(LoginDoctor.this, "You are not a part of Hospital", Toast.LENGTH_SHORT).show();
                            }

                        }

                    }
                    else{
                        Toast.makeText(LoginDoctor.this, "Check Email or Password!", Toast.LENGTH_SHORT).show();
                    }
                });

            });
            signup.setOnClickListener(v -> {
                        if(userEmail!=null && password!=null){
                            String e=userEmail.getEditableText().toString();
                            String p=password.getEditableText().toString();
                            Auth.createUserWithEmailAndPassword(e,p).addOnCompleteListener(task -> {
                                if(task.isSuccessful()){
                                    Toast.makeText(LoginDoctor.this, "Account is created successfully", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(LoginDoctor.this, "Already signed up..", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else{
                            Toast.makeText(this, "Fill the credentials before signup...", Toast.LENGTH_SHORT).show();
                        }
                    }
            );
        }catch (Exception e){

        }
        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getEmail().equals(userEmail.getText().toString())){
                    Auth.sendPasswordResetEmail(user.getEmail()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Dialog resetpass=new Dialog(LoginDoctor.this);
                            resetpass.setContentView(R.layout.gmail_dialog_box);
                            resetpass.show();
                        }
                    });
                }

            }
        });


    }

    }


