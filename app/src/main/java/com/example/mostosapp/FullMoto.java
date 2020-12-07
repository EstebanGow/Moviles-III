package com.example.mostosapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mostosapp.dialogs.DeleteConfim;
import com.example.mostosapp.models.MotoModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;

public class FullMoto extends AppCompatActivity implements DeleteConfim.Respuesta{
    private MotoModel item;
    private TextView marca, model, km, price;
    private ImageView imgV, meg, compart, edit, delete;
    private String id;
    protected FirebaseFirestore db;
    final private String collection = "motorcycle";
    private Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_moto);

        item = (MotoModel) getIntent().getSerializableExtra("data");
        marca = (TextView)findViewById(R.id.marc);
        model = (TextView)findViewById(R.id.model);
        km = (TextView)findViewById(R.id.km);
        price = (TextView)findViewById(R.id.price);
        imgV = (ImageView) findViewById(R.id.img);
        delete = (ImageView) findViewById(R.id.delete);
        meg = (ImageView) findViewById(R.id.meg);
        compart = (ImageView) findViewById(R.id.compartir);
        db = FirebaseFirestore.getInstance();

        edit = (ImageView) findViewById(R.id.edit);
        delete = (ImageView) findViewById(R.id.delete);

        meg.setTag("uno");
        imgV.setImageResource(item.getImage());
        marca.setText(item.getMarca());
        model.setText(item.getModelo());
        km.setText(item.getRecorrido());
        price.setText(item.getPrecio());
        id = item.getId();
        meg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Megusta();
            }
        });

        compart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent compartir = new Intent(android.content.Intent.ACTION_SEND);
                compartir.setType("text/plain");
                String mensaje = "Te recomiendo este articulo que vi en MotoApp Motocicleta "+item.getMarca()+" \nPrecio: "+item.getPrecio();
                compartir.putExtra(android.content.Intent.EXTRA_SUBJECT, "MotoApp");
                compartir.putExtra(android.content.Intent.EXTRA_TEXT, mensaje);
                startActivity(Intent.createChooser(compartir, "Compartir vía"));
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Update(id);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteConfim dialogCOnfirmation = new DeleteConfim(context, FullMoto.this);
            }
        });


    }

    private void Update(String id){
        Intent sig = new Intent(getApplicationContext(),UpdateActivity.class);
        sig.putExtra("id", id);
        startActivity(sig);
    }

    private void Delete(String id){
        db.collection(collection).document(id).delete();
        Intent sig = new Intent(getApplicationContext(),ViewList.class);
        startActivity(sig);
    }

    public void resultado(Boolean delete) {
        if(delete = true){
            Delete(id);
        }
    }

    public void Megusta(){
        int uno = R.drawable.mc;
        int dos = R.drawable.mcbg;
        if(meg.getTag().equals("uno")){
            meg.setImageResource(dos);
            meg.setTag("dos");
            Toast.makeText(this,"Agregado a Favoritos",Toast.LENGTH_SHORT).show();
        }else if(meg.getTag().equals("dos")) {
            meg.setImageResource(uno);
            meg.setTag("uno");
            Toast.makeText(this,"Borrado de Favoritos",Toast.LENGTH_SHORT).show();
        }
    }
}