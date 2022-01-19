package com.example.pokercalculatorimmisso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myMainContainer = findViewById(R.id.main_container);
        my9PlayersButton = findViewById(R.id.button_nine_players);

        Button my4PlayersButton;
        my4PlayersButton = findViewById(R.id.button_four_players);
        my4PlayersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fourPlay = new Intent(MainActivity.this,FourPlayActivity.class);
                startActivity(fourPlay);
            }
        });
    }


    ConstraintLayout myMainContainer;
    Button my9PlayersButton;
}