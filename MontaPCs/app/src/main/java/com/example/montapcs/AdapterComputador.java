package com.example.montapcs;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AdapterComputador extends RecyclerView.Adapter<AdapterComputador.ComputadorViewHolder> {

    private List<Computador> listaComputadores;
    private Context context;

    public AdapterComputador(Context context, List<Computador> listaComputadores) {
        this.context = context;
        this.listaComputadores = listaComputadores;
    }

    @NonNull
    @Override
    public ComputadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_computador, parent, false);
        return new ComputadorViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ComputadorViewHolder holder, int position) {
        Computador computador = listaComputadores.get(position);
        holder.textView.setText(computador.getNome());
        holder.textView.setText(computador.getNome() + "\nCusto: R$" + computador.getNumero().toString()); // Corrigido para exibir o número

        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirDetalhesDoComputador(computador.getNome());
            }
        });

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirDetalhesDoComputador(computador.getNome());
            }
        });

        holder.trashCan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirDialogoExclusao(computador.getNome());
            }
        });
    }

    private void abrirDetalhesDoComputador(String nomeComputador) {
        Intent intent = new Intent(context, ActivityComputador.class);
        intent.putExtra("nomeComputador", nomeComputador);
        context.startActivity(intent);
    }

    private void abrirDialogoExclusao(final String nomeComputador) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Tem certeza que deseja excluir este item?");
        builder.setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                removerItem(nomeComputador);
                Toast.makeText(context, "Projeto excluído", Toast.LENGTH_SHORT).show();
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

    @Override
    public int getItemCount() {
        return listaComputadores.size();
    }

    public class ComputadorViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView icon;
        ImageView trashCan;

        public ComputadorViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            icon = itemView.findViewById(R.id.icon);
            trashCan = itemView.findViewById(R.id.trashCan);
        }
    }

    public void atualizarLista(List<Computador> novaLista) {
        this.listaComputadores = novaLista;
        notifyDataSetChanged();
    }

    public void removerItem(String nomeComputador) {
        excluirProjeto(nomeComputador);
        for (int i = 0; i < listaComputadores.size(); i++) {
            if (listaComputadores.get(i).getNome().equals(nomeComputador)) {
                listaComputadores.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }

    public void excluirProjeto(String nomeComputador) {
        try {
            SQLiteDatabase BancoDados = context.openOrCreateDatabase("montapc", MODE_PRIVATE, null);
            String query = "DELETE FROM computadores WHERE nome = ?";
            BancoDados.execSQL(query, new Object[]{nomeComputador});
            BancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
