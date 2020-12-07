package com.example.mostosapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mostosapp.dialogs.UpdateDialog;
import com.example.mostosapp.models.MotoModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UpdateActivity extends AppCompatActivity {
    private MotoModel model;
    public TextView home;
    protected FirebaseFirestore db;
    private String TAG = "LFNOT";
    private String id;
    private Button btn_registro;
    final private String collection = "motorcycle";
    private EditText marca, modelo, km, price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        id = getIntent().getStringExtra("id");
        marca = (EditText)findViewById(R.id.marca);
        modelo = (EditText)findViewById(R.id.model);
        km  = (EditText)findViewById(R.id.km);
        price = (EditText)findViewById(R.id.price);
        home = (TextView) findViewById(R.id.home);
        btn_registro  = (Button) findViewById(R.id.registro);

        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection(collection).document(id);
        docRef.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            model = document.toObject(MotoModel.class);
                            marca.setText(model.getMarca());
                            modelo.setText(model.getModelo());
                            km.setText(model.getRecorrido());
                            price.setText(model.getPrecio());
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Volver();
            }
        });
        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Update(id);
            }
        });
    }

    private void Update(String id){
        String marc, mod, kms, pri;
        marc = marca.getText().toString();
        mod = modelo.getText().toString();
        kms = km.getText().toString();
        pri = price.getText().toString();
        Map<String,Object> map = new HashMap<>();
        map.put("marca", marc);
        map.put("modelo", mod);
        map.put("precio", pri);
        map.put("recorrido", kms);
        db.collection(collection).document(id).update(map);
        final Handler handler = new Handler();
        final UpdateDialog dialog = new UpdateDialog(this);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Volver();
            }
        },2000);

    }

    public void Volver(){
        Intent sig = new Intent(this,ViewList.class);
        startActivity(sig);
        finish();
    }
}