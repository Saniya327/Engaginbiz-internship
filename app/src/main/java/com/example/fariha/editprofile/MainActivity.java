    package com.example.fariha.editprofile;



    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.graphics.BitmapFactory;
    import android.graphics.Color;
    import android.graphics.Typeface;
    import android.net.Uri;
    import android.support.annotation.NonNull;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.View;
    import android.widget.Button;
    import android.widget.CompoundButton;
    import android.widget.DatePicker;
    import android.widget.EditText;
    import android.widget.ImageButton;
    import android.widget.ImageView;
    import android.widget.RadioButton;
    import android.widget.Switch;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.facebook.AccessToken;
    import com.facebook.CallbackManager;
    import com.facebook.FacebookCallback;
    import com.facebook.FacebookException;
    import com.facebook.FacebookSdk;
    import com.facebook.login.LoginManager;
    import com.facebook.login.LoginResult;
    import com.facebook.login.widget.LoginButton;
    import com.google.android.gms.auth.api.Auth;
    import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
    import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
    import com.google.android.gms.auth.api.signin.GoogleSignInResult;
    import com.google.android.gms.common.ConnectionResult;
    import com.google.android.gms.common.SignInButton;
    import com.google.android.gms.common.api.GoogleApiClient;
    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.auth.AuthCredential;
    import com.google.firebase.auth.AuthResult;
    import com.google.firebase.auth.FacebookAuthProvider;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.auth.GoogleAuthProvider;

    import java.util.ArrayList;


    public class MainActivity extends AppCompatActivity {

        static String TAG="Logging";


        TextView editProfile,aboutTV,detailsTV,titleTV,accountsTV,genderTV,removefb;
        ImageView myImage,connectFb,addPicture;
        ImageButton thumb1, thumb2, thumb3, thumb4, thumb5;
        Button googleSignOut;
        EditText profile, about;
        RadioButton male, female, none;
        LoginButton fbButton;

        private static final int fb_id=64206;
        private static final int RC_SIGN_IN=1234;   //used for google sign in
        private static final int upload_id=0;
        private static final int work_id=1;
        private static final int industry_id=2;
        private static final int sponsor_id=3;
        private static final int volunteer_id=4;
        private static final int invest_id=5;

        Switch switch1, switch2, switch3, switch4, switch5;
        UploadPicture uploadPicture;    //Uploading the picture is done by an object of type UploadPicture
        private CallbackManager callbackManager;
        private FirebaseAuth mAuth;
        private FirebaseAuth.AuthStateListener mauthListener;
        SignInButton googleButton;
        GoogleApiClient mGoogleApiClient;

        //used for Additional details
        ArrayList<String> workList = new ArrayList<>();
        ArrayList<String> industryList = new ArrayList<>();
        ArrayList<String> sponsorList = new ArrayList<>();
        ArrayList<String> volunteerList = new ArrayList<>();
        ArrayList<String> investList = new ArrayList<>();



        //Checks if user is already logged in to facebook
        @Override
        public void onStart() {
            super.onStart();

            // Check if user is signed in (non-null) and update UI accordingly.
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if(currentUser != null)
                updateUI("Connected");
            else
                updateUI("Not connected");
            //mAuth.addAuthStateListener(mauthListener);
        }

       /* @Override
        public void onPause() {
            super.onPause();
            SharedPreferences settings = getSharedPreferences("UserInfo", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("Description",about.getText().toString());
            editor.putString("Profile",profile.getText().toString());
            editor.apply();
        }

        @Override
        protected void onPostResume() {
            super.onPostResume();
            SharedPreferences settings = getSharedPreferences("UserInfo", 0);
            about.setText(settings.getString("Description", ""));
            profile.setText(settings.getString("Profile", ""));
        }*/

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            FacebookSdk.sdkInitialize(this.getApplicationContext());
            setContentView(R.layout.activity_main);

            //Declare the fonts for UI
            Typeface semiBold=Typeface.createFromAsset(getAssets(),"Montserrat-SemiBold.otf");
            Typeface regular = Typeface.createFromAsset(getAssets(),"Montserrat-Regular.otf");
            Typeface medium = Typeface.createFromAsset(getAssets(),"Montserrat-Medium.otf");
            Typeface light = Typeface.createFromAsset(getAssets(), "Montserrat-Light.otf");

            //Set the fonts for UI
            editProfile=(TextView) findViewById(R.id.editProfile);
            editProfile.setTypeface(semiBold);
            aboutTV=(TextView)findViewById(R.id.about_tv);
            aboutTV.setTypeface(medium);
            detailsTV=(TextView)findViewById(R.id.details_tv);
            detailsTV.setTypeface(medium);
            accountsTV=(TextView)findViewById(R.id.accounts_tv);
            accountsTV.setTypeface(medium);
            genderTV=(TextView)findViewById(R.id.gender_tv);
            genderTV.setTypeface(medium);
            titleTV=(TextView)findViewById(R.id.title_tv);
            titleTV.setTypeface(medium);




            myImage = (ImageView) findViewById(R.id.myImage);
            addPicture = (ImageButton) findViewById(R.id.addButton);
            addPicture.setImageResource(R.drawable.iconpencil);
            profile = (EditText) findViewById(R.id.profile);
            profile.setTypeface(regular);
            about = (EditText) findViewById(R.id.about);
            about.setTypeface(regular);

            male = (RadioButton) findViewById(R.id.male);
            male.setTypeface(regular);

            female = (RadioButton) findViewById(R.id.female);
            female.setTypeface(regular);
            none = (RadioButton) findViewById(R.id.none);
            none.setTypeface(regular);


            thumb1 = (ImageButton) findViewById(R.id.Thumb1);
            thumb2 = (ImageButton) findViewById(R.id.Thumb2);
            thumb3 = (ImageButton) findViewById(R.id.Thumb3);
            thumb4 = (ImageButton) findViewById(R.id.Thumb4);
            thumb5 = (ImageButton) findViewById(R.id.Thumb5);

            switch5 = (Switch) findViewById(R.id.switch5);
            switch5.setTypeface(medium);
            switch4 = (Switch) findViewById(R.id.switch4);
            switch4.setTypeface(medium);
            switch3 = (Switch) findViewById(R.id.switch3);
            switch3.setTypeface(medium);
            switch2 = (Switch) findViewById(R.id.switch2);
            switch2.setTypeface(medium);
            switch1 = (Switch) findViewById(R.id.switch1);
            switch1.setTypeface(medium);

            callbackManager = CallbackManager.Factory.create();
            uploadPicture = new UploadPicture(this, R.id.myImage);
            fbButton=(LoginButton) findViewById(R.id.facebookButton);
            fbButton.setReadPermissions("email", "public_profile");

            mAuth = FirebaseAuth.getInstance();
            connectFb=(ImageView)findViewById(R.id.connectedFb);
            removefb=(TextView)findViewById(R.id.removeFb);
            googleButton = (SignInButton) findViewById(R.id.googleButton);
            googleSignOut=(Button)findViewById(R.id.googleSignOut);




            addPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //uploadPicture class takes care of askForPermissions()
                    uploadPicture.askForPermissions();
                    Intent i = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(i,upload_id );
                }
            });

            //Object for Google Sign In
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();

            //Google API client initialization
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                            Toast.makeText(MainActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();

            googleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("In intent","yess");
                    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                    startActivityForResult(signInIntent, RC_SIGN_IN);

                }
            });

            //Sign out for google
            googleSignOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                }
            });

            //The switches at the end
            switch5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        thumb5.setVisibility(View.VISIBLE);
                    else
                        thumb5.setVisibility(View.GONE);
                }
            });

            switch4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        thumb4.setVisibility(View.VISIBLE);
                    else
                        thumb4.setVisibility(View.GONE);
                }
            });

            switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        thumb3.setVisibility(View.VISIBLE);
                    else
                        thumb3.setVisibility(View.GONE);
                }
            });

            switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        thumb2.setVisibility(View.VISIBLE);
                    else
                        thumb2.setVisibility(View.GONE);
                }
            });

            switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        thumb1.setVisibility(View.VISIBLE);
                    }

                    else
                        thumb1.setVisibility(View.GONE);

                }
            });

            thumb1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent work_intent=new Intent(getApplicationContext(),Work_Activity.class);
                    startActivityForResult(work_intent,work_id);
                }
            });

            thumb2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent industry_intent=new Intent(getApplicationContext(),Industry_Activity.class);
                    startActivityForResult(industry_intent,industry_id);
                }
            });

            thumb3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sponsor_intent=new Intent(getApplicationContext(),Sponsor_Activity.class);
                    startActivityForResult(sponsor_intent,sponsor_id);
                }
            });

            thumb4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent volunteer_intent=new Intent(getApplicationContext(),Volunteer_Activity.class);
                    startActivityForResult(volunteer_intent,volunteer_id);
                }
            });

            thumb5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent invest_intent=new Intent(getApplicationContext(),Invest_Activity.class);
                    startActivityForResult(invest_intent,invest_id);
                }
            });




            //Authenticating with facebook
            fbButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>()
            {
                @Override
                public void onSuccess(LoginResult loginResult)
                {

                    //String userID=loginResult.getAccessToken().getUserId();
                    AccessToken tokenID=loginResult.getAccessToken();
                    handleFacebookAccessToken(tokenID);
                }

                @Override
                public void onCancel()
                {
                    //do nothing
                }

                @Override
                public void onError(FacebookException exception)
                {
                    Log.v("LoginActivity", exception.getCause().toString());
                }
            });


            //Disconnect from facebook
            removefb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth.getInstance().signOut();
                    LoginManager.getInstance().logOut();
                    updateUI("Not connected");
                }
            });

        }

        //Exchanged facebook access token for firebase token
        private void handleFacebookAccessToken(AccessToken token) {
            Log.d(TAG, "handleFacebookAccessToken:" + token);

            AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithCredential:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI("Connected");
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithCredential:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI("Not connected");
                            }
                        }
                    });
        }

        //Sign in with google
        private void handleGoogleSignInResult(GoogleSignInResult result) {
            Log.d(TAG, "handleSignInResult:" + result.isSuccess());
            if (result.isSuccess()) {
                // Signed in successfully, show authenticated UI.
                GoogleSignInAccount acct = result.getSignInAccount();
                firebaseAuthWithGoogle(acct);
                //update UI
            } else {
                // Signed out, show unauthenticated UI.
                //Update UI
            }
        }


        //Authenticate google with Firebase
        private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
            Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

            AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithCredential:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(MainActivity.this, "from auth"+user.getDisplayName(),
                                        Toast.LENGTH_LONG).show();
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithCredential:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }

                            // ...
                        }
                    });
        }





        //Updates the UI based on whether the user is logged in or not(Facebook auth)
        public void updateUI(String status){
            switch(status){
                case "Connected":
                    connectFb.setImageResource(R.drawable.tick);
                    removefb.setText("Disconnect");
                    removefb.setTextColor(Color.parseColor("#D0021B"));
                    break;
                case "Not connected":
                    connectFb.setImageDrawable(null);
                    removefb.setText("Connect");
                    removefb.setTextColor(Color.parseColor("#673AB7"));
                    break;

            }
        }


        //All start activity for result comes here after executing the activity
        //the case depends on from which activity control is coming
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            switch(requestCode) {
                case upload_id:
                    String picturePath = uploadPicture.ActivityResult(requestCode, resultCode, data);
                    if (!picturePath.equals(""))
                        myImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                case fb_id:
                    Log.v("requestcode",""+requestCode);
                    callbackManager.onActivityResult(requestCode, resultCode, data);
                    break;
                case RC_SIGN_IN:
                    GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                    handleGoogleSignInResult(result);
                    break;
                case work_id:
                    if(resultCode==RESULT_OK){
                        Bundle extras = data.getExtras();
                        if(extras!= null){
                            workList=extras.getStringArrayList("myList");

                        }

                        for(String s: workList){
                            Log.v("WORK LIST FROM MAIN ", s);
                        }

                    }
                    break;
                case industry_id:
                    if(resultCode==RESULT_OK){
                        Bundle extras = data.getExtras();

                        if(extras!= null){
                            industryList=extras.getStringArrayList("myList");

                        }

                        for(String s: industryList){
                            Log.v("WORK LIST FROM MAIN ", s);
                        }

                    }
                    break;
                case sponsor_id:
                    if(resultCode==RESULT_OK){
                        Bundle extras = data.getExtras();

                        if(extras!= null){
                            sponsorList=extras.getStringArrayList("myList");

                        }

                        for(String s: sponsorList){
                            Log.v("WORK LIST FROM MAIN ", s);
                        }

                    }
                    break;
                case volunteer_id:
                    if(resultCode==RESULT_OK){
                        Bundle extras = data.getExtras();

                        if(extras!= null){
                            volunteerList=extras.getStringArrayList("myList");

                        }

                        for(String s: volunteerList){
                            Log.v("WORK LIST FROM MAIN ", s);
                        }

                    }
                    break;
                case invest_id:
                    if(resultCode==RESULT_OK){
                        Bundle extras = data.getExtras();

                        if(extras!= null){
                            investList=extras.getStringArrayList("myList");

                        }

                        for(String s: investList){
                            Log.v("WORK LIST FROM MAIN ", s);
                        }

                    }
                    break;


            }

        }



        //For choosing gender
        public void genderButtonClicked(View view) {

            // Is the button now checked?
            boolean checked = ((RadioButton) view).isChecked();

            // Check which radio button was clicked
            switch (view.getId()) {
                case R.id.male:
                    if (checked)
                        // set gender as male
                        break;
                case R.id.female:
                    if (checked)
                        // set gender as female
                        break;
                case R.id.none:
                    if (checked)
                        // set gender as unspecified
                        break;

            }
        }
    }