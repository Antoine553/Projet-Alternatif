package com.example.projet_alternatif;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText cnom, cprenom, cdate, cemail, cmdp, cmdp2;
    Button cbtnco;
    TextView ctext_register;
    FirebaseAuth fAuth;
    ProgressBar loadbtn_register;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Créer un compte");

        cnom  = findViewById(R.id.nom_register);
        cprenom = findViewById(R.id.prenom_register);
        cdate = findViewById(R.id.date_register);
        cemail   = findViewById(R.id.email_register);
        cmdp     = findViewById(R.id.mdp_register);
        cmdp2    = findViewById(R.id.mdp_register2);
        cbtnco   = findViewById(R.id.creation_register);
        ctext_register = findViewById(R.id.text_register);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        loadbtn_register = findViewById(R.id.progressBar_register);

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        cbtnco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nom = cnom.getText().toString().trim();
                final String prenom = cprenom.getText().toString().trim();
                final String date = cdate.getText().toString().trim();
                final String email = cemail.getText().toString().trim();
                String mdp = cmdp.getText().toString().trim();
                String mdp2 = cmdp2.getText().toString().trim();

                if(TextUtils.isEmpty(email)){cemail.setError("Email incorrecte");return;}
                if(TextUtils.isEmpty(mdp)){cmdp.setError("mot de passe incorrecte");return;}
                if(TextUtils.isEmpty(mdp2)){cmdp.setError("mot de passe incorrecte");return;}
                if(mdp.length() < 6){cmdp.setError("Votre mot de passe doit être >= 6 characters");return;}
                if(nom.length() < 2){cnom.setError("Votre nom doit être >= 2 characters");return;}
                if(prenom.length() < 2){cnom.setError("Votre prenom doit être >= 2 characters");return;}
                if(date.length() < 2){cnom.setError("La date semble être incorrecte");return;}

                loadbtn_register.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email,mdp).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser fuser = fAuth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Register.this, "Lien de validation envoyé par email",Toast.LENGTH_SHORT).show();
                                }
                            });

                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("Nom",nom);
                            user.put("Prenom",prenom);
                            user.put("Date_de_naissance",date);
                            user.put("Email",email);
                            user.put("Nom_complet",prenom+" "+nom);
                            documentReference.set(user);
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else{
                            Toast.makeText(Register.this, "Error ! " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            loadbtn_register.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });


        ctext_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });


    }
}
