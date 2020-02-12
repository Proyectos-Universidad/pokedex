package com.cenfotec.pokedex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cenfotec.pokedex.models.Pokemon;

import java.util.ArrayList;

public class ListPokemonAdapter extends RecyclerView.Adapter<ListPokemonAdapter.ViewHolder> {

    private ArrayList<Pokemon> dataset;
    private Context context;

    public ListPokemonAdapter(Context context) {
        this.context = context;
        dataset = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Pokemon p = dataset.get(position);
        holder.nameTextView.setText(p.getName());

        Glide.
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void addPokemonList(ArrayList<Pokemon> pokemonList){
        dataset.addAll(pokemonList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView pictureImageView;
        private TextView nameTextView;

        public ViewHolder (View itemView){
            super(itemView);

            pictureImageView = (ImageView) itemView.findViewById(R.id.pictureImageView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
        }
    }

}
