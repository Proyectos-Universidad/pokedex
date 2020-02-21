package com.cenfotec.pokedex;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cenfotec.pokedex.models.Pokemon;
import com.cenfotec.pokedex.models.PokemonResponse;
import com.cenfotec.pokedex.service.PokeService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokeGridFragment extends Fragment {

    private PokemonRecyclerViewAdapter pokemonAdapter;
    private static final String TAG = "PokeGridFragment";

    private Retrofit retrofit;
    private RecyclerView recyclerView;

    private int offset;
    private boolean clearedToLoad;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment with the ProductGrid theme
        View view = inflater.inflate(R.layout.poke_grid_fragment, container, false);
        // Set up the toolbar
        setUpToolbar(view);

        // Set up the RecyclerView
        pokemonAdapter = new PokemonRecyclerViewAdapter(getContext());
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

        recyclerView = view.findViewById(R.id.recycler_View);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(pokemonAdapter);

        int largePadding = getResources().getDimensionPixelSize(R.dimen.poke_grid_spacing);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.poke_grid_spacing_small);

        recyclerView.addItemDecoration(new PokeGridItemDecoration(largePadding, smallPadding));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > 0){
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if(clearedToLoad){
                        if((visibleItemCount + pastVisibleItems) >= totalItemCount){
                            clearedToLoad = false;
                            offset += 20;
                            getData(offset);
                        }
                    }
                }
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        clearedToLoad = true;
        offset = 0;
        getData(offset);

        return view;
    }

    private void setUpToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.app_bar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    private void getData(int offset){

        PokeService service = retrofit.create(PokeService.class);
        Call<PokemonResponse> pokemonResponseCall = service.getPokemonList(20, offset);

        pokemonResponseCall.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                clearedToLoad = true;
                if(response.isSuccessful()){

                    PokemonResponse pokemonResponse = response.body();
                    ArrayList<Pokemon> pokemonList = pokemonResponse.getResults();

                    pokemonAdapter.addPokemonList(pokemonList);
                }else{
                    Log.e(TAG, "onResponse: " + response.errorBody() );
                }
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                clearedToLoad = true;
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

}
