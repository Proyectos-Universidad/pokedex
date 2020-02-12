package com.cenfotec.pokedex;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Retrofit retrofit;

    private RecyclerView recyclerView;
    private ListPokemonAdapter listPokemonAdapter;

    private int offset;
    private boolean clearedToLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
