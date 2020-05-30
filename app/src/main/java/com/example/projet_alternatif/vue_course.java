package com.example.projet_alternatif;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class vue_course extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth;
    String userId;
    int compte = 0;
    int nbToursgl;
    String temps;
    String courseId;
    String etat;
    int tourp1 = 0, tourp2 = 0, tourp3 = 0, tourp4 = 0, tourp5 = 0, tourp6 = 0, tourp7 = 0, tourp8 = 0;
    String tempsp1, tempsp2, tempsp3, tempsp4, tempsp5, tempsp6, tempsp7, tempsp8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vue_course);
        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        courseId = getIntent().getExtras().getString("courseId");

        DocumentReference docRef = db.collection("courses").document(courseId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull final Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        final String nom = document.getData().get("nom").toString();
                        etat = document.getData().get("etat").toString();
                        String distance = document.getData().get("distance").toString();
                        String createur = document.getData().get("createur").toString();
                        String id_createur = document.getData().get("id_createur").toString();
                        String nbTours = document.getData().get("nb_tour").toString();
                        String date = document.getData().get("date_depart").toString();

                        setTitle(nom);
                        nbToursgl = Integer.parseInt(nbTours);

                        final TextView nomCourse = (TextView) findViewById(R.id.course_nom);
                        TextView distanceCourse = (TextView) findViewById(R.id.course_distance);
                        TextView tourCourse = (TextView) findViewById(R.id.course_tour);
                        TextView dateCourse = (TextView) findViewById(R.id.course_date);
                        TextView createurCourse = (TextView) findViewById(R.id.course_createur);

                        final TextView participant1 = (TextView) findViewById(R.id.participant1);TextView tvp1 = (TextView) findViewById(R.id.textView1);
                        final TextView participant2 = (TextView) findViewById(R.id.participant2);TextView tvp2 = (TextView) findViewById(R.id.textView2);
                        final TextView participant3 = (TextView) findViewById(R.id.participant3);TextView tvp3 = (TextView) findViewById(R.id.textView3);
                        final TextView participant4 = (TextView) findViewById(R.id.participant4);TextView tvp4 = (TextView) findViewById(R.id.textView4);
                        final TextView participant5 = (TextView) findViewById(R.id.participant5);TextView tvp5 = (TextView) findViewById(R.id.textView5);
                        final TextView participant6 = (TextView) findViewById(R.id.participant6);TextView tvp6 = (TextView) findViewById(R.id.textView6);
                        final TextView participant7 = (TextView) findViewById(R.id.participant7);TextView tvp7 = (TextView) findViewById(R.id.textView7);
                        final TextView participant8 = (TextView) findViewById(R.id.participant8);TextView tvp8 = (TextView) findViewById(R.id.textView8);

                        nomCourse.setText(nom);
                        distanceCourse.setText("Distance : " + distance);
                        tourCourse.setText("Nb de tours : " + nbTours);
                        dateCourse.setText("Date : " + date);
                        createurCourse.setText("Créateur : " + createur);


                        String p1 = document.getData().get("participant1").toString();String s1 = document.getData().get("resultatp1").toString();
                        String p2 = document.getData().get("participant2").toString();String s2 = document.getData().get("resultatp2").toString();
                        String p3 = document.getData().get("participant3").toString();String s3 = document.getData().get("resultatp3").toString();
                        String p4 = document.getData().get("participant4").toString();String s4 = document.getData().get("resultatp4").toString();
                        String p5 = document.getData().get("participant5").toString();String s5 = document.getData().get("resultatp5").toString();
                        String p6 = document.getData().get("participant6").toString();String s6 = document.getData().get("resultatp6").toString();
                        String p7 = document.getData().get("participant7").toString();String s7 = document.getData().get("resultatp7").toString();
                        String p8 = document.getData().get("participant8").toString();String s8 = document.getData().get("resultatp8").toString();

                        if (etat == "1" || etat == "2"){
                            tvp1.setText(s1);tvp2.setText(s2);tvp3.setText(s3);tvp4.setText(s4);
                            tvp5.setText(s5);tvp6.setText(s6);tvp7.setText(s7);tvp8.setText(s8);
                            compte = 0;
                        }

                        DocumentReference docRefp1 = db.collection("users").document(p1);
                        docRefp1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task2) {
                                if (task2.isSuccessful()){DocumentSnapshot document = task2.getResult();
                                    if (document.exists()) {
                                        String idp1 = document.getId();
                                        String pp1 = document.getData().get("Nom_complet").toString();
                                        participant1.setText(pp1);
                                        if (idp1.equals(userId)){checkAuth();}
                                    }
                                }
                            }
                        });
                        if (!p2.equals("")){
                            DocumentReference docRefp2 = db.collection("users").document(p2);
                            docRefp2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task3) {
                                    if (task3.isSuccessful()){DocumentSnapshot document = task3.getResult();
                                        if (document.exists()) {
                                            String idp2 = document.getId();
                                            String pp2 = document.getData().get("Nom_complet").toString();
                                            participant2.setText(pp2);
                                            if (idp2.equals(userId)){checkAuth();}
                                        }
                                    }
                                }
                            });
                        }
                        if (!p3.equals("")){
                            DocumentReference docRefp3 = db.collection("users").document(p3);
                            docRefp3.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task4) {
                                    if (task4.isSuccessful()){DocumentSnapshot document = task4.getResult();
                                        if (document.exists()) {
                                            String idp3 = document.getId();
                                            String pp3 = document.getData().get("Nom_complet").toString();
                                            participant3.setText(pp3);
                                            if (idp3.equals(userId)){checkAuth();}
                                        }
                                    }
                                }
                            });
                        }
                        if (!p4.equals("")){
                            DocumentReference docRefp4 = db.collection("users").document(p4);
                            docRefp4.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task5) {
                                    if (task5.isSuccessful()){DocumentSnapshot document = task5.getResult();
                                        if (document.exists()) {
                                            String idp4 = document.getId();
                                            String pp4 = document.getData().get("Nom_complet").toString();
                                            participant4.setText(pp4);
                                            if (idp4.equals(userId)){checkAuth();}
                                        }
                                    }
                                }
                            });
                        }
                        if (!p5.equals("")){
                            DocumentReference docRefp5 = db.collection("users").document(p5);
                            docRefp5.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task6) {
                                    if (task6.isSuccessful()){DocumentSnapshot document = task6.getResult();
                                        if (document.exists()) {
                                            String idp5 = document.getId();
                                            String pp5 = document.getData().get("Nom_complet").toString();
                                            participant5.setText(pp5);
                                            if (idp5.equals(userId)){checkAuth();}
                                        }
                                    }
                                }
                            });
                        }
                        if (!p6.equals("")){
                            DocumentReference docRefp6 = db.collection("users").document(p6);
                            docRefp6.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task7) {
                                    if (task7.isSuccessful()){DocumentSnapshot document = task7.getResult();
                                        if (document.exists()) {
                                            String idp6 = document.getId();
                                            String pp6 = document.getData().get("Nom_complet").toString();
                                            participant6.setText(pp6);
                                            if (idp6.equals(userId)){checkAuth();}
                                        }
                                    }
                                }
                            });
                        }
                        if (!p7.equals("")){
                            DocumentReference docRefp7 = db.collection("users").document(p7);
                            docRefp7.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task8) {
                                    if (task8.isSuccessful()){DocumentSnapshot document = task8.getResult();
                                        if (document.exists()) {
                                            String idp7 = document.getId();
                                            String pp7 = document.getData().get("Nom_complet").toString();
                                            participant7.setText(pp7);
                                            if (idp7.equals(userId)){checkAuth();}
                                        }
                                    }
                                }
                            });
                        }
                        if (!p8.equals("")){
                            DocumentReference docRefp8 = db.collection("users").document(p8);
                            docRefp8.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task9) {
                                    if (task9.isSuccessful()){DocumentSnapshot document = task9.getResult();
                                        if (document.exists()) {
                                            String idp8 = document.getId();
                                            String pp8 = document.getData().get("Nom_complet").toString();
                                            participant8.setText(pp8);
                                            if (idp8.equals(userId)){checkAuth();}
                                        }
                                    }
                                }
                            });
                        }
                        if (id_createur.equals(userId)){checkAuth();}
                    }
                }

            }
        });
    }

    public void checkAuth(){
        if (etat == "0"){
            Button stc = (Button) findViewById(R.id.button_start_course);stc.setVisibility(View.VISIBLE);
        }else if (etat == "1"){
            Button stc = (Button) findViewById(R.id.button_restart_course);stc.setVisibility(View.VISIBLE);
            Button valbtn = (Button) findViewById(R.id.button_valider_course);valbtn.setVisibility(View.VISIBLE);
        }
    }


    public void StartCourse(View view){
        Button bp1 = (Button) findViewById(R.id.button_participant1);TextView tp1 = (TextView) findViewById(R.id.textView1);
        Button bp2 = (Button) findViewById(R.id.button_participant2);TextView tp2 = (TextView) findViewById(R.id.textView2);
        Button bp3 = (Button) findViewById(R.id.button_participant3);TextView tp3 = (TextView) findViewById(R.id.textView3);
        Button bp4 = (Button) findViewById(R.id.button_participant4);TextView tp4 = (TextView) findViewById(R.id.textView4);
        Button bp5 = (Button) findViewById(R.id.button_participant5);TextView tp5 = (TextView) findViewById(R.id.textView5);
        Button bp6 = (Button) findViewById(R.id.button_participant6);TextView tp6 = (TextView) findViewById(R.id.textView6);
        Button bp7 = (Button) findViewById(R.id.button_participant7);TextView tp7 = (TextView) findViewById(R.id.textView7);
        Button bp8 = (Button) findViewById(R.id.button_participant8);TextView tp8 = (TextView) findViewById(R.id.textView8);
        Button stc = (Button) findViewById(R.id.button_start_course);final Chronometer chr = (Chronometer) findViewById(R.id.simpleChronometer);

        bp1.setVisibility(View.VISIBLE);tp1.setVisibility(View.GONE);bp2.setVisibility(View.VISIBLE);tp2.setVisibility(View.GONE);
        bp3.setVisibility(View.VISIBLE);tp3.setVisibility(View.GONE);bp4.setVisibility(View.VISIBLE);tp4.setVisibility(View.GONE);
        bp5.setVisibility(View.VISIBLE);tp5.setVisibility(View.GONE);bp6.setVisibility(View.VISIBLE);tp6.setVisibility(View.GONE);
        bp7.setVisibility(View.VISIBLE);tp7.setVisibility(View.GONE);bp8.setVisibility(View.VISIBLE);tp8.setVisibility(View.GONE);
        stc.setVisibility(View.GONE);chr.setVisibility(View.VISIBLE);


        chr.setBase(SystemClock.elapsedRealtime());
        chr.start();
        chr.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                temps = chr.getText().toString();
                if(compte == 8*nbToursgl){
                    chr.setOnChronometerTickListener(null);

                    TextView tv1 = (TextView) findViewById(R.id.textView1);TextView tv2 = (TextView) findViewById(R.id.textView2);
                    TextView tv3 = (TextView) findViewById(R.id.textView3);TextView tv4 = (TextView) findViewById(R.id.textView4);
                    TextView tv5 = (TextView) findViewById(R.id.textView5);TextView tv6 = (TextView) findViewById(R.id.textView6);
                    TextView tv7 = (TextView) findViewById(R.id.textView7);TextView tv8 = (TextView) findViewById(R.id.textView8);

                    tempsp1 = tv1.getText().toString();tempsp2 = tv2.getText().toString();
                    tempsp3 = tv3.getText().toString();tempsp4 = tv4.getText().toString();
                    tempsp5 = tv5.getText().toString();tempsp6 = tv6.getText().toString();
                    tempsp7 = tv7.getText().toString();tempsp8 = tv8.getText().toString();

                    Map<String, Object> docData = new HashMap<>();
                    docData.put("resultatp1", tempsp1);docData.put("resultatp2", tempsp2);
                    docData.put("resultatp3", tempsp3);docData.put("resultatp4", tempsp4);
                    docData.put("resultatp5", tempsp5);docData.put("resultatp6", tempsp6);
                    docData.put("resultatp7", tempsp7);docData.put("resultatp8", tempsp8);
                    docData.put("etat", 1);
                    db.collection("courses").document(courseId).set(docData, SetOptions.merge());

                    chr.stop();
                    chr.setBase(SystemClock.elapsedRealtime());
                    chr.setVisibility(View.GONE);
                    Button rstbtn = (Button) findViewById(R.id.button_restart_course);rstbtn.setVisibility(View.VISIBLE);
                    Button valbtn = (Button) findViewById(R.id.button_valider_course);valbtn.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void btnp(View view) {
        compte++;
        int tag = Integer.parseInt(view.getTag().toString());
        if (tag == 1){tourp1++;}else if(tag == 2){tourp2++;}else if(tag == 3){tourp3++;}else if(tag == 4){tourp4++;}
        else if (tag == 5){tourp5++;}else if(tag == 6){tourp6++;}else if(tag == 7){tourp7++;}else if(tag == 8){tourp8++;}

        Button bp1 = (Button) findViewById(R.id.button_participant1);Button bp2 = (Button) findViewById(R.id.button_participant2);
        Button bp3 = (Button) findViewById(R.id.button_participant3);Button bp4 = (Button) findViewById(R.id.button_participant4);
        Button bp5 = (Button) findViewById(R.id.button_participant5);Button bp6 = (Button) findViewById(R.id.button_participant6);
        Button bp7 = (Button) findViewById(R.id.button_participant7);Button bp8 = (Button) findViewById(R.id.button_participant8);

        TextView tv1 = (TextView) findViewById(R.id.textView1);TextView tv2 = (TextView) findViewById(R.id.textView2);
        TextView tv3 = (TextView) findViewById(R.id.textView3);TextView tv4 = (TextView) findViewById(R.id.textView4);
        TextView tv5 = (TextView) findViewById(R.id.textView5);TextView tv6 = (TextView) findViewById(R.id.textView6);
        TextView tv7 = (TextView) findViewById(R.id.textView7);TextView tv8 = (TextView) findViewById(R.id.textView8);

        if (tourp1 == nbToursgl && bp1.getVisibility() == view.VISIBLE){bp1.setVisibility(View.GONE);tv1.setText(temps);tv1.setVisibility(View.VISIBLE);}
        if (tourp2 == nbToursgl && bp2.getVisibility() == view.VISIBLE){bp2.setVisibility(View.GONE);tv2.setText(temps);tv2.setVisibility(View.VISIBLE);}
        if (tourp3 == nbToursgl && bp3.getVisibility() == view.VISIBLE){bp3.setVisibility(View.GONE);tv3.setText(temps);tv3.setVisibility(View.VISIBLE);}
        if (tourp4 == nbToursgl && bp4.getVisibility() == view.VISIBLE){bp4.setVisibility(View.GONE);tv4.setText(temps);tv4.setVisibility(View.VISIBLE);}
        if (tourp5 == nbToursgl && bp5.getVisibility() == view.VISIBLE){bp5.setVisibility(View.GONE);tv5.setText(temps);tv5.setVisibility(View.VISIBLE);}
        if (tourp6 == nbToursgl && bp6.getVisibility() == view.VISIBLE){bp6.setVisibility(View.GONE);tv6.setText(temps);tv6.setVisibility(View.VISIBLE);}
        if (tourp7 == nbToursgl && bp7.getVisibility() == view.VISIBLE){bp7.setVisibility(View.GONE);tv7.setText(temps);tv7.setVisibility(View.VISIBLE);}
        if (tourp8 == nbToursgl && bp8.getVisibility() == view.VISIBLE){bp8.setVisibility(View.GONE);tv8.setText(temps);tv8.setVisibility(View.VISIBLE);}
    }

    public void RestartCourse(View view) {
        compte = 0;
        Button rstbtn = (Button) findViewById(R.id.button_restart_course);rstbtn.setVisibility(View.GONE);
        Button valbtn = (Button) findViewById(R.id.button_valider_course);valbtn.setVisibility(View.GONE);
        StartCourse(view);
        tourp1 = 0;tourp2 = 0;tourp3 = 0;tourp4 = 0;tourp5 = 0;tourp6 = 0;tourp7 = 0;tourp8 = 0;
        tempsp1 = "00:00";tempsp2 = "00:00";tempsp3 = "00:00";tempsp4 = "00:00";tempsp5 = "00:00";tempsp6 = "00:00";tempsp7 = "00:00";tempsp8 = "00:00";
    }

    public void ValideCourse(View view){
        Map<String, Object> docData = new HashMap<>();
        docData.put("etat", 2);
        db.collection("courses").document(courseId).set(docData, SetOptions.merge());
        Button rstbtn = (Button) findViewById(R.id.button_restart_course);rstbtn.setVisibility(View.GONE);
        Button valbtn = (Button) findViewById(R.id.button_valider_course);valbtn.setVisibility(View.GONE);
    }
}
