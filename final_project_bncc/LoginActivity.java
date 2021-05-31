package com.midproject.final_project_bncc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.midproject.final_project_bncc.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth auth;
    private ActivityLoginBinding binding;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private  String name = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference( "students");

        binding.btnLogin.setOnClickListener(this);
        binding.btnCreateAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() ==  binding.btnLogin.getId()){
            String email = binding.etEmail.getText().toString();
            String password = binding.etPass.getText().toString();

            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Authentication success", Toast.LENGTH_SHORT).show();

                                reference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                            Student student = postSnapshot.getValue(student.class);
                                            if(student.getEmail().equals(email)){
                                                name = student.getName();
                                                Intent toMain = new Intent(LoginActivity.this, MainActivity.class);
                                                toMain.putExtra("NAME", name);
                                                startActivity(toMain);
                                                finish();
                                                break;
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            } else
                                Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    });
    }
        else if (view.getId() == binding.btnCreateAccount.getId()){
            Intent toRegister = new Intent(this, RegisterActivity.class);
            startActivity(toRegister);
        }
    }
}