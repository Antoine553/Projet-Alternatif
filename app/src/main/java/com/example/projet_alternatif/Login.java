package com.example.projet_alternatif;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText cemail,cmdp;
    Button cconnexion_login;
    TextView ccreer_compte,cmdpoublier;
    ProgressBar loadbtn_login;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Connexion");

        cemail = findViewById(R.id.email_login);
        cmdp = findViewById(R.id.mdp_login);
        loadbtn_login = findViewById(R.id.progressBar_login);
        fAuth = FirebaseAuth.getInstance();
        cconnexion_login = findViewById(R.id.connexion_login);
        ccreer_compte = findViewById(R.id.text_login2);
        cmdpoublier = findViewById(R.id.text_login3);

        cconnexion_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = cemail.getText().toString().trim();
                String mdp = cmdp.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    cemail.setError("Email incorrecte");
                    return;
                }

                if(TextUtils.isEmpty(mdp)){
                    cmdp.setError("mot de passe incorrecte");
                    return;
                }

                loadbtn_login.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(email,mdp).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Login.this, "Vous êtes connecté.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else{
                            Toast.makeText(Login.this, "Error ! " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            loadbtn_login.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        ccreer_compte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });

        cmdpoublier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Réinitialiser le mdp ?");
                passwordResetDialog.setMessage("Entrer votre Email pour recevoir un lien");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mail = resetMail.getText().toString().trim();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Login.this, "Un lien a été envoyé à votre email", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this, "Une erreur est survenus : " + e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });

                passwordResetDialog.create().show();
            }
        });

    }
}
