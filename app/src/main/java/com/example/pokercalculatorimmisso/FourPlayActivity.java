package com.example.pokercalculatorimmisso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.example.pokercalculatorimmisso.adapter.CardAdapter;
import com.example.pokercalculatorimmisso.databinding.ActivityFourPlayBinding;
import com.example.pokercalculatorimmisso.model.CardModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class FourPlayActivity extends AppCompatActivity {
    ActivityFourPlayBinding binding;
    CardAdapter adapter;
    BottomSheetDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFourPlayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        dialog = new BottomSheetDialog(this);
        adapter = new CardAdapter(getCard(), this);
        binding.centerList.setAdapter(adapter);


        binding.selectCardClub.setOnClickListener(view -> {
            showBottomDialog();
        });
    }

    private void showBottomDialog() {

    }

    private ArrayList<CardModel> getCard() {

        ArrayList<CardModel> cards = new ArrayList<>();
        cards.add(new CardModel("", 0, R.drawable.ace_clubs));
        cards.add(new CardModel("", 0, R.drawable.ace_diamonds));
        cards.add(new CardModel("", 0, R.drawable.ace_hearts));
        cards.add(new CardModel("", 0, R.drawable.ace_spades));
        cards.add(new CardModel("", 0, R.drawable.queen_clubs));
        cards.add(new CardModel("", 0, R.drawable.queen_diamonds));
        cards.add(new CardModel("", 0, R.drawable.queen_hearts));
        cards.add(new CardModel("", 0, R.drawable.queen_spades));

        return cards;
    }
}