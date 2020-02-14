package com.cenfotec.pokedex;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cenfotec.pokedex.models.Pokemon;
import com.cenfotec.pokedex.models.PokemonResponse;
import com.cenfotec.pokedex.service.PokeService;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity {

    public static final String GOOGLE_ACCOUNT = "google_account";
    private static final String TAG = "ProfileActivity";

    private Retrofit retrofit;

    private RecyclerView recyclerView;
    private ListPokemonAdapter listPokemonAdapter;

    private int offset;
    private boolean clearedToLoad;

    private TextView profileName, profileEmail;
    private ImageView profileImage;

    private Button signOut;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//        profileName = findViewById(R.id.profile_text);
//        profileEmail = findViewById(R.id.profile_email);
//        profileImage = findViewById(R.id.profile_image);
//        signOut = findViewById(R.id.sign_out);
//
//        setDataOnView();
//
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
//
//        googleSignInClient = GoogleSignIn.getClient(this, gso);
//
//        signOut.setOnClickListener( v -> {
//
//            googleSignInClient.signOut().addOnCompleteListener(task -> {
//                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//            });
//        });

        recyclerView = findViewById(R.id.recyclerView);
        listPokemonAdapter = new ListPokemonAdapter(this);
        recyclerView.setAdapter(listPokemonAdapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
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
    }

//    private void setDataOnView() {
//
//        GoogleSignInAccount googleSignInAccount = getIntent().getParcelableExtra(GOOGLE_ACCOUNT);
//
//        Picasso.get().load(googleSignInAccount.getPhotoUrl()).centerInside().fit().into(profileImage);
//        profileName.setText(googleSignInAccount.getDisplayName());
//        profileEmail.setText(googleSignInAccount.getEmail());
//
//
//    }

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

                    listPokemonAdapter.addPokemonList(pokemonList);
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
