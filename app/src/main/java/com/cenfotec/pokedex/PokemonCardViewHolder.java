package com.cenfotec.pokedex;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PokemonCardViewHolder extends RecyclerView.ViewHolder {

    public TextView pokemonName;
    public TextView pokemonType;
    public ImageView pokemonImage;

    public PokemonCardViewHolder(@NonNull View itemView){

        super(itemView);

        pokemonName = itemView.findViewById(R.id.pokemon_name);
        pokemonType = itemView.findViewById(R.id.pokemon_type);
        pokemonImage = itemView.findViewById(R.id.pokemon_image);
    }
}
