package com.cenfotec.pokedex;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cenfotec.pokedex.models.Team;
import com.cenfotec.pokedex.models.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TeamActivity extends AppCompatActivity {

    private String userEmail, userName;
    private DatabaseReference rootRef, teamsRef;
    private Button test;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);

        test = findViewById(R.id.buttonTest);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if( acct != null){
            userEmail = acct.getEmail();
            userName = acct.getDisplayName();
        }

        rootRef = FirebaseDatabase.getInstance().getReference();

        teamsRef = rootRef.child("teams");

        test.setOnClickListener((View v)-> {
            User newUser = new User("newUser@account.com", "Steph092");
            ArrayList<User> teamMembers = new ArrayList<>();
            teamMembers.add(newUser);
            Team testTeam = new Team("Saturn", teamMembers);

            teamsRef.child(testTeam.getName()).setValue(testTeam);
            //teamsRef.push().child("name").setValue("Test team");
            //teamsRef.push().child("email").setValue(userEmail);
        });
    }


}
