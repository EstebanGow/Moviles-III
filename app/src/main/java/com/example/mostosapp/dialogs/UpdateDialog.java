package com.example.mostosapp.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.mostosapp.R;
    public class UpdateDialog {

        public UpdateDialog(final Context context) {
            final Dialog Objdialogo = new Dialog(context);
            Objdialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
            Objdialogo.setCancelable(false);
            Objdialogo.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Objdialogo.setContentView(R.layout.update_dialog);
            Objdialogo.show();
        }

    }

