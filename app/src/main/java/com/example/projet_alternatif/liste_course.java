package com.example.projet_alternatif;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class liste_course extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste);
        setTitle("Courses disponibles");
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        final ListView listView = (ListView)findViewById(R.id.listview);
        final ArrayList<detailCourse> arrayList=new ArrayList<>();
        final detailCourseAdapter adapter = new detailCourseAdapter(this, R.layout.detail_liste, arrayList);

        db.collection("courses").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        detailCourse actu_course = new detailCourse(document.get("nom").toString().trim(), document.get("date_creation").toString().trim(), document.get("distance").toString().trim(),document.getId().toString().trim());
                        arrayList.add(actu_course);
                    }
                    listView.setAdapter(adapter);
                } else {
                    Log.w("TAG", "Error getting documents.", task.getException());
                }
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(liste_course.this, vue_course.class);
                TextView CourseIdView = (TextView) findViewById(R.id.textViewDetail4);
                String CourseId = CourseIdView.getText().toString();
                intent.putExtra("courseId", CourseId);
                startActivity(intent);
            }
        });

    }

    public void add_course(View view) {
        startActivity(new Intent(getApplicationContext(),liste_course.class));
        finish();
    }
}
