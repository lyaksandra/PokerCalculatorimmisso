package com.example.pokercalculatorimmisso;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.pokercalculatorimmisso.adapter.CardAdapter;
import com.example.pokercalculatorimmisso.databinding.ActivityFourPlayBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class FourPlayActivity extends Activity {
    ActivityFourPlayBinding binding;
    CardAdapter adapter;
    BottomSheetDialog dialog;

    private ViewGroup mainLayout;

    private int xDelta;
    private int yDelta;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four_play);
        mainLayout = findViewById(R.id.four_play);
        MyCardView aceDiamonds = findViewById(R.id.ace_diamonds);
        MyCardView aceClubs = findViewById(R.id.ace_clubs);
        MyCardView aceSpades = findViewById(R.id.ace_spades);
        MyCardView aceHearts = findViewById(R.id.ace_hearts);

        aceDiamonds.setOnTouchListener(onTouchListener());
        aceClubs.setOnTouchListener(onTouchListener());
        aceSpades.setOnTouchListener(onTouchListener());
        aceHearts.setOnTouchListener(onTouchListener());
    }

    /*
      должны быть в onCreate
            binding = ActivityFourPlayBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            dialog = new BottomSheetDialog(this);
            adapter = new CardAdapter(getCard(), this);
            binding.centerList.setAdapter(adapter);
            binding.selectCardClub.setOnClickListener(view -> {
                showBottomDialog();
            });
    */


    private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();

                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        ConstraintLayout.LayoutParams lParams = (ConstraintLayout.LayoutParams)
                                view.getLayoutParams();

                        if (view.getVisibility() == View.VISIBLE ) {
                            xDelta = x - lParams.leftMargin;
                            yDelta = y - lParams.topMargin;
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        break;

                    case MotionEvent.ACTION_MOVE:

                        if (view.getVisibility() == View.VISIBLE ) {
                            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view
                                    .getLayoutParams();
                            layoutParams.leftMargin = x - xDelta;
                            layoutParams.topMargin = y - yDelta;
                            layoutParams.rightMargin = 0;
                            layoutParams.bottomMargin = 0;
                            view.setLayoutParams(layoutParams);
                        }
                        break;
                }

                mainLayout.invalidate();
                return true;
            }
        };
    }
}

/*
    должно быть внутри программы
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
*/