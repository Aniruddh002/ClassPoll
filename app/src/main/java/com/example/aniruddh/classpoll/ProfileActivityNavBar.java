package com.example.aniruddh.classpoll;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivityNavBar extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private TextView textViewUserEmail;
    private Button LogoutButton;
    private DatabaseReference mDatabaseReference;
    private EditText editTextName, editTextAddress;
    private Button buttonSave;



    ArrayList<String> myArrayList = new ArrayList<>();
    ListView myListView;
    Firebase myFirebase;
    List<PollInformation> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_nav_bar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Toast.makeText(ProfileActivityNavBar.this,"Fetching Polls", Toast.LENGTH_LONG).show();


        myListView = (ListView) findViewById(R.id.ListView);


        questionList = new ArrayList<>();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ProfileActivityNavBar.this, NewPoll.class);
                startActivity(intent);


            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {

            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        //editTextAddress=(EditText) findViewById(R.id.editTextAddress);
        //editTextName=(EditText) findViewById(R.id.editTextName);
        //buttonSave=(Button) findViewById(R.id.buttonSave);
       // LogoutButton = (Button) findViewById(R.id.LogoutButton);


        FirebaseUser user = firebaseAuth.getCurrentUser();


        //////////////SAVING INFORMATION CODE

        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        textViewUserEmail.setText("Welcome " + user.getEmail());

        /*
        buttonLogout.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
        */
        mDatabaseReference.addChildEventListener(new com.google.firebase.database.ChildEventListener() {
            @Override
            public void onChildAdded(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                  questionList.clear();

            for (com.google.firebase.database.DataSnapshot pollInformationSnapshot : dataSnapshot.getChildren()) {
                PollInformation pollInformation = pollInformationSnapshot.getValue(PollInformation.class);
                questionList.add(pollInformation);

                    QuestionList adapter = new QuestionList(ProfileActivityNavBar.this,questionList);
                    myListView.setAdapter(adapter);
        }
        }

            @Override
            public void onChildChanged(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(com.google.firebase.database.DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

  /* @Override
    protected void onStart() {
        super.onStart();

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

            //    questionList.clear();

                for(com.google.firebase.database.DataSnapshot pollInformationSnapshot : dataSnapshot.getChildren() ){
                    PollInformation pollInformation = pollInformationSnapshot.getValue(PollInformation.class);
                    Toast.makeText(ProfileActivityNavBar.this,pollInformation.getQuestion(), Toast.LENGTH_LONG).show();
         //           questionList.add(pollInformation);

                }
        //        QuestionList adapter = new QuestionList(ProfileActivityNavBar.this,questionList);
      //          myListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
*/

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile_activity_nav_bar, menu);
        return true;
    }

  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Fragment fragment=null;

        int id = item.getItemId();

        if (id == R.id.dashboard) {
            startActivity(new Intent(this,ProfileActivityNavBar.class));

            // / Handle the camera action
        } else if (id == R.id.createpoll) {
            startActivity(new Intent(this,NewPoll.class));

        } else if (id == R.id.joinpoll) {


        } else if (id == R.id.aboutus) {

            startActivity(new Intent(ProfileActivityNavBar.this, AboutUs.class));

        } else if (id == R.id.Logout) {
            firebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(ProfileActivityNavBar.this, LoginActivity.class));

        }else if (id == R.id.nav_share) {

            Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            String shareBody = "Create Your Own Poll NOW!";
            String shareSub = "CLASSPOLL";
            myIntent.putExtra(Intent.EXTRA_SUBJECT,shareBody);
            myIntent.putExtra(Intent.EXTRA_TEXT,shareSub);
            startActivity(Intent.createChooser(myIntent," Share Via "));

        }

        if(fragment!=null){

            FragmentManager fragmentManager= getSupportFragmentManager();
            FragmentTransaction ft=  fragmentManager.beginTransaction();
             ft.replace(R.id.frame_profile_nav_bar,fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //SAVE BUTTON CODE
    /*private void saveUserInformation(){
        String name= editTextName.getText().toString().trim();
        String address= editTextAddress.getText().toString().trim();

        UserInformation userInformation = new UserInformation(name,address);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(userInformation);

        Toast.makeText(this, "Information saved ...", Toast.LENGTH_LONG).show();
    }
    */

    @Override
    public void onClick(View view) {


        /////SAVE BUTTON CODE

        /*if(view==buttonSave){
            saveUserInformation();
        }
        */

    }
}
