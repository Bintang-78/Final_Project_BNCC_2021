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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.midproject.final_project_bncc.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth auth;
    private ActivityRegisterBinding binding;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference( "students");
        binding.btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == binding.btnRegister.getId()){
            String id = binding.etId.getText().toString();
            String email = binding.etEmail.getText().toString();
            String password = binding.etPass.getText().toString();
            String name = binding.etName.getText().toString();
            String confirm = binding.etConfirm.getText().toString();

            Student student = new Student(id, email, name, password);

            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText( RegisterActivity.this,"Authentication success", Toast.LENGTH_SHORT).show();
                                reference.child(id).setValue(student);
                                finish();
                            } else {
                                Toast.makeText( RegisterActivity.this,"Authentication failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }
}