package com.example.montapcs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogComponente {
    public interface OnSelectListener {
        void onSelect();
    }

    public static void showDialog(Context context, String componentInfo, final OnSelectListener onSelectListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Informações do Componente");
        builder.setMessage(componentInfo);

        builder.setPositiveButton("Selecionar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (onSelectListener != null) {
                    onSelectListener.onSelect();
                }
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

