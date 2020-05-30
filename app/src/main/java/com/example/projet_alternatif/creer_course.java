package com.example.projet_alternatif;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class creer_course extends AppCompatActivity {

    String createur;
    String date_creation;
    String date_depart;
    String distance;
    String id_createur;
    String nb_tour;
    String nom;
    String p1, p2, p3, p4, p5, p6, p7, p8;
    String rp1, rp2, rp3, rp4, rp5, rp6, rp7, rp8;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    Integer valid = 1;
    DocumentReference docRef;
    String[] parts;

    MultiAutoCompleteTextView sp1;
    EditText nom_crea_course;
    EditText tours_crea_course;
    EditText date_depart_crea_course;
    Spinner distance_crea_course;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_course);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        id_createur = fAuth.getCurrentUser().getUid();

        distance_crea_course = findViewById(R.id.distance_crea_course);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.distances_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);distance_crea_course.setAdapter(adapter);
        distance_crea_course.setSelection(0);

        final ArrayList<String> listUsers = new ArrayList<>();
        final ArrayList<String> listUsers_id = new ArrayList<>();

        date_creation = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        Log.w("Tag", date_creation);
        sp1 = findViewById(R.id.participant1);
        nom_crea_course = findViewById(R.id.nom_crea_course);
        tours_crea_course = findViewById(R.id.tours_crea_course);
        date_depart_crea_course = findViewById(R.id.date_depart_crea_course);
        tours_crea_course = findViewById(R.id.tours_crea_course);

        final ArrayAdapter<String> adapterUsers = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,listUsers);
        sp1.setAdapter(adapterUsers);
        sp1.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());


        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int i = 0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String actu_user = document.get("Nom_complet").toString().trim();
                        String actu_user_id = document.getId();
                        listUsers.add(i,actu_user);
                        listUsers_id.add(i,actu_user_id);
                        i++;
                    }
                    ((ArrayAdapter) adapterUsers).notifyDataSetChanged();
                } else {
                    Log.w("TAG", "Error getting documents.", task.getException());
                }
            }
        });
    }

    public void ValideCrea(View view){
        valid = 1;
        nom = nom_crea_course.getText().toString();
        nb_tour = tours_crea_course.getText().toString();
        distance = distance_crea_course.getSelectedItem().toString();
        date_depart = date_depart_crea_course.getText().toString();
        p1="";p2="";p3="";p4="";p5="";p6="";p7="";p8="";
        rp1="";rp2="";rp3="";rp4="";rp5="";rp6="";rp7="";rp8="";

        parts = sp1.getText().toString().split(",");

        p1 = parts[0].trim();
        if (parts.length > 1){p2 = parts[1].trim();}else{p2 = "";}
        if (parts.length > 2){p3 = parts[2].trim();}else{p3 = "";}
        if (parts.length > 3){p4 = parts[3].trim();}else{p4 = "";}
        if (parts.length > 4){p5 = parts[4].trim();}else{p5 = "";}
        if (parts.length > 5){p6 = parts[5].trim();}else{p6 = "";}
        if (parts.length > 6){p7 = parts[6].trim();}else{p7 = "";}
        if (parts.length > 7){p8 = parts[7].trim();}else{p8 = "";}

        if(p1.equals(p2) || p1.equals(p3) || p1.equals(p4) || p1.equals(p5) || p1.equals(p6) || p1.equals(p7) || p1.equals(p8)){
            sp1.setError("Un participant ne peut participer qu'une fois 1");valid = 0;
        }
        if((!p2.equals("")) && (p2.equals(p3) || p2.equals(p4) || p2.equals(p5) || p2.equals(p6) || p2.equals(p7) || p2.equals(p8))){
            sp1.setError("Un participant ne peut participer qu'une fois 2");valid = 0;
        }
        if((!p3.equals("")) && (p3.equals(p4) || p3.equals(p5) || p3.equals(p6) || p3.equals(p7) || p3.equals(p8))){
            sp1.setError("Un participant ne peut participer qu'une fois 3");valid = 0;
        }
        if((!p4.equals("")) && (p4.equals(p5) || p4.equals(p6) || p4.equals(p7) || p4.equals(p8))){
            sp1.setError("Un participant ne peut participer qu'une fois 4");valid = 0;
        }
        if((!p5.equals("")) && (p5.equals(p6) || p5.equals(p7) || p5.equals(p8))){
            sp1.setError("Un participant ne peut participer qu'une fois 5");valid = 0;
        }
        if((!p6.equals("")) && (p6.equals(p7) || p6.equals(p8))){
            sp1.setError("Un participant ne peut participer qu'une fois 6");valid = 0;
        }
        if((!p7.equals("")) && (p7.equals(p8))){
            sp1.setError("Un participant ne peut participer qu'une fois 7");valid = 0;
        }

        docRef = db.collection("users").document(id_createur);

        db.collection("users").whereEqualTo("Nom_complet", p1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {for (DocumentSnapshot document : task.getResult()) {p1 = document.getId();}}
                if (!p2.equals("")){
                    db.collection("users").whereEqualTo("Nom_complet", p2).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {for (DocumentSnapshot document : task.getResult()) {p2 = document.getId();}}
                            if (!p3.equals("")){
                                db.collection("users").whereEqualTo("Nom_complet", p3).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {for (DocumentSnapshot document : task.getResult()) {p3 = document.getId();}}
                                        if (!p4.equals("")){
                                            db.collection("users").whereEqualTo("Nom_complet", p4).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {for (DocumentSnapshot document : task.getResult()) {p4 = document.getId();}}
                                                    if (!p5.equals("")){
                                                        db.collection("users").whereEqualTo("Nom_complet", p5).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                if (task.isSuccessful()) {for (DocumentSnapshot document : task.getResult()) {p5 = document.getId();}}
                                                                if (!p6.equals("")){
                                                                    db.collection("users").whereEqualTo("Nom_complet", p6).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                            if (task.isSuccessful()) {for (DocumentSnapshot document : task.getResult()) {p6 = document.getId();}}
                                                                            if (!p7.equals("")){
                                                                                db.collection("users").whereEqualTo("Nom_complet", p7).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                        if (task.isSuccessful()) {for (DocumentSnapshot document : task.getResult()) {p7 = document.getId();}}
                                                                                        if (!p8.equals("")){
                                                                                            db.collection("users").whereEqualTo("Nom_complet", p8).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                                @Override
                                                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                                    if (task.isSuccessful()) {for (DocumentSnapshot document : task.getResult()) {p8 = document.getId();envoieGlobal();}}
                                                                                                }
                                                                                            });
                                                                                        }else{envoieGlobal();}
                                                                                    }
                                                                                });
                                                                            }else{envoieGlobal();}
                                                                        }
                                                                    });
                                                                }else{envoieGlobal();}
                                                            }
                                                        });
                                                    }else{envoieGlobal();}
                                                }
                                            });
                                        }else{envoieGlobal();}
                                    }
                                });
                            }else{envoieGlobal();}
                        }
                    });
                }else{envoieGlobal();}
            }
        });
    }

    public void envoieGlobal() {
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        createur = document.getData().get("Nom_complet").toString();
                        final UUID uuid = UUID.randomUUID();

                        if(TextUtils.isEmpty(nom)){nom_crea_course.setError("Email incorrecte");valid = 0;}
                        if(TextUtils.isEmpty(nb_tour)||Integer.parseInt(nb_tour) < 1){tours_crea_course.setError("Nb de tours incorrecte");valid = 0;}
                        if(TextUtils.isEmpty(date_depart)||date_depart.length()!=10){date_depart_crea_course.setError("Date dd/mm/yyyy requise");valid = 0;}
                        if(TextUtils.isEmpty(p1)){sp1.setError("Participant non valide");valid = 0;}
                        if(parts.length > 9 && !parts[9].equals("")){sp1.setError("8 participant maximum");valid = 0;}

                        if (valid == 1){
                            DocumentReference documentReference = fStore.collection("courses").document(uuid.toString());
                            Map<String,Object> course = new HashMap<>();
                            course.put("createur",createur);
                            course.put("date_creation",date_creation);
                            course.put("date_depart",date_depart);
                            course.put("distance",distance);
                            course.put("etat","0");
                            course.put("id_createur",id_createur);
                            course.put("nb_tour", nb_tour);
                            course.put("nom",nom);
                            course.put("participant1",p1);
                            course.put("participant2",p2);
                            course.put("participant3",p3);
                            course.put("participant4",p4);
                            course.put("participant5",p5);
                            course.put("participant6",p6);
                            course.put("participant7",p7);
                            course.put("participant8",p8);
                            course.put("resultatp1","");
                            course.put("resultatp2","");
                            course.put("resultatp3","");
                            course.put("resultatp4","");
                            course.put("resultatp5","");
                            course.put("resultatp6","");
                            course.put("resultatp7","");
                            course.put("resultatp8","");
                            documentReference.set(course).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Intent intent = new Intent(creer_course.this, vue_course.class);
                                    String CourseId = uuid.toString();
                                    intent.putExtra("courseId", CourseId);
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                }
            }
        });
    }

}
