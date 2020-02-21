package com.cenfotec.pokedex;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cenfotec.pokedex.models.Pokemon;

import java.util.ArrayList;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class PokemonRecyclerViewAdapter extends RecyclerView.Adapter<PokemonCardViewHolder> {
    private static final String TAG = "Pokemon_Recycler_View";
    private ArrayList<Pokemon> dataset;
    private Context context;

    public PokemonRecyclerViewAdapter(Context context){
        this.context = context;
        dataset = new ArrayList<>();
    }

    @NonNull
    @Override
    public PokemonCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_card, parent, false);
        return new PokemonCardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonCardViewHolder holder, int position) {

        Pokemon p = dataset.get(position);
        holder.pokemonName.setText(p.getName());

        Glide.with(context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + p.getNumber() + ".png")
                .centerCrop()
                .transition(withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.pokemonImage);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void addPokemonList(ArrayList<Pokemon> pokemonList){
        dataset.addAll(pokemonList);
        notifyDataSetChanged();
    }
}
