package com.example.pokercalculatorimmisso;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pokercalculatorimmisso.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.buttonFourPlayers.setOnClickListener(v -> {
            Intent fourPlay = new Intent(MainActivity.this, FourPlayActivity.class);
            startActivity(fourPlay);
        });
    }
}