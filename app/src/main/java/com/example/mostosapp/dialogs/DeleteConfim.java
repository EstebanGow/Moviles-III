package com.example.mostosapp.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.mostosapp.R;

public class DeleteConfim {

    public interface Respuesta {
        void resultado(Boolean delete);
    }
    private Respuesta Finalizar;
    public DeleteConfim(final Context context, Respuesta accion) {
        final Dialog Objdialogo = new Dialog(context);
        Finalizar = accion;
        Objdialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objdialogo.setCancelable(false);
        Objdialogo.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Objdialogo.setContentView(R.layout.delete_confirmacion);
        Button delete = (Button) Objdialogo.findViewById(R.id.delete);
        Button cancel = (Button) Objdialogo.findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objdialogo.dismiss();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean delete = true;
                Finalizar.resultado(delete);
                Objdialogo.dismiss();
            }
        });

        Objdialogo.show();
    }

}

