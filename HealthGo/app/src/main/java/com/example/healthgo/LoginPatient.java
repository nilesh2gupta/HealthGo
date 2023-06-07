package com.example.healthgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginPatient extends AppCompatActivity {
    FirebaseAuth auth;

    TextView resetpassword;
    FirebaseUser user;
    EditText useremail,password;
    Button login;
    TextView signup,forgetpassword;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_patient);
        auth=FirebaseAuth.getInstance();
        user= auth.getCurrentUser();
        resetpassword=findViewById(R.id.forgetpassword);
        useremail=findViewById(R.id.Useremail);
        password=findViewById(R.id.Password);
        login=findViewById(R.id.Loginbtn);
        signup=findViewById(R.id.Signup);
        forgetpassword=findViewById(R.id.forgetpassword);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e=useremail.getText().toString();
                String p=password.getText().toString();
                if(e!=null && p!=null){

                    auth.signInWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                startActivity(new Intent(LoginPatient.this,PatientDashboard.class));
                            }
                            else{

                                Toast.makeText(LoginPatient.this, "Check your credentials", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(LoginPatient.this, "Enter the credentials..", Toast.LENGTH_SHORT).show();
                }


                }

        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e=useremail.getText().toString();
                String p=password.getText().toString();
                if(e!=null && p!=null){

                   auth.createUserWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(LoginPatient.this, "Account is created successfully!!", Toast.LENGTH_SHORT).show();
                            }

                            else{
                                Toast.makeText(LoginPatient.this, "Already signed up!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else{
                    Toast.makeText(LoginPatient.this, "Fill the credentials before signup", Toast.LENGTH_SHORT).show();
                }

            }
        });
        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getEmail().equals(useremail.getText().toString())){
                    auth.sendPasswordResetEmail(user.getEmail()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Dialog resetpass=new Dialog(LoginPatient.this);
                            resetpass.setContentView(R.layout.gmail_dialog_box);
                            resetpass.show();
                        }
                    });
                }

            }
        });

    }

}