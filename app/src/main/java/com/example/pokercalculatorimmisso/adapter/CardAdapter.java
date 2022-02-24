package com.example.pokercalculatorimmisso.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokercalculatorimmisso.databinding.ItemCardBinding;
import com.example.pokercalculatorimmisso.model.CardModel;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private ArrayList<CardModel> cards = new ArrayList<>();
    private Context context;

    public CardAdapter(ArrayList<CardModel> cards, Context context) {
        this.cards = cards;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemCardBinding.inflate(LayoutInflater.from(context)));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.binding.cardIv.setImageResource(cards.get(position).getResId());

    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        ItemCardBinding binding;

        public ViewHolder(ItemCardBinding b) {
            super(b.getRoot());
            binding = b;
        }
    }
}
