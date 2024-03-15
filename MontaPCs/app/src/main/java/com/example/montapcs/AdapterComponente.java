package com.example.montapcs;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterComponente extends RecyclerView.Adapter<AdapterComponente.ComputadorViewHolder> {
    private List<Componente> componentes;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(String nomeComponente);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public AdapterComponente(List<Componente> componentes) {
        this.componentes = componentes;
    }

    @NonNull
    @Override
    public ComputadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_componente, parent, false);
        return new ComputadorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComputadorViewHolder holder, int position) {
        Componente componente = componentes.get(position);
        holder.bind(componente);

        holder.buttonItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(componente.getNome());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return componentes.size();
    }

    public static class ComputadorViewHolder extends RecyclerView.ViewHolder {
        private Button buttonItem;

        public ComputadorViewHolder(@NonNull View itemView) {
            super(itemView);
            buttonItem = itemView.findViewById(R.id.buttonItem);
        }

        @SuppressLint("SetTextI18n")
        public void bind(Componente componente) {
            buttonItem.setText(componente.getNome() + "\nCusto: R$" + String.valueOf(componente.getCusto()));
        }
    }
}
