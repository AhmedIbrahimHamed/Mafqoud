package com.project.graduation.welcomeback;

/**
 * created by: Ayyad Shenouda
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.project.graduation.welcomeback.R.string.username;

/*
 *this screen for sign up if process fall show toast.
 * if success show activation popup.
 */
public class SignUpActivity extends AppCompatActivity {

    private static final int RC_SIGN_UP = 1;

    private EditText mUsername;         //username edit text
    private EditText mEmail;            //email edit text
    private EditText mPassword;         //password edit text
    private Button mSignUpButton;       //sign up button
    private ImageView mGoogleSignUp;    //google sign up method
    private TextView mSignIn;           //go to sign in page

    private ProgressDialog mProgressDialog; // show information to user.

    private FirebaseAuth mFirebaseAuth; // firebase authintication.
    private DatabaseReference mDatabaseRefrence; // firebase database refrence

    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        // initialize our instance variables.
        mUsername = (EditText) findViewById(R.id.sign_up_username);
        mEmail = (EditText) findViewById(R.id.sign_up_email);
        mPassword = (EditText) findViewById(R.id.sign_up_password);
        mSignUpButton = (Button) findViewById(R.id.sign_up_button);
        mGoogleSignUp = (ImageView) findViewById(R.id.google_signup_button);
        mSignIn = (TextView) findViewById(R.id.go_to_sign_in);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.register_dialog));

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRefrence = FirebaseDatabase.getInstance().getReference().child("Users");


        // add listener to mSignIn text view to go to sign in activity.
        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // add listener to sign up button to complete the process
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isNetworkConnected()) {
                    Toast.makeText(SignUpActivity.this, R.string.no_internet, Toast.LENGTH_SHORT).show();
                    return;
                }

                //check the validation of the username, email, password.
                if (!validate()) {
                    return;
                }
                //show dialog to user.
                mProgressDialog.show();

                signUp();
            }
        });

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                        Toast.makeText(SignUpActivity.this, R.string.conncetion_failed, Toast.LENGTH_SHORT);
                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mGoogleSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isNetworkConnected()) {
                    Toast.makeText(SignUpActivity.this, R.string.no_internet, Toast.LENGTH_SHORT).show();
                    return;
                }

                mProgressDialog.show();
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_UP);
            }
        });
    }


    // this method check the validation of the username, password, and email of the edit texts.
    private boolean validate() {
        boolean valid = true;

        //validate name
        String username = mUsername.getText().toString().trim();
        if (username.isEmpty()) {
            mUsername.setError(getString(R.string.required));
            valid = false;
        } else if (username.length() < 4) {
            mUsername.setError(getString(R.string.usernamr_valid));
            valid = false;
        }
        for (char ch : username.toCharArray()) {
            if (!((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || ch == ' ')) {
                mUsername.setError(getString(R.string.valid_username_characters));
                valid = false;
            }
        }

        //validate password
        String password = mPassword.getText().toString().trim();
        if (password.length() < 8) {
            mPassword.setError(getString(R.string.password_valid));
            valid = false;
        }

        //validate email
        String email = mEmail.getText().toString().trim();
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError(getString(R.string.vaild_email));
            valid = false;
        }

        return valid;
    }


    // this method do the work of sign up
    private void signUp() {

        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mProgressDialog.dismiss();
                        if (task.isSuccessful()) {
                            String username = mUsername.getText().toString().trim();
                            // if sign up successfull send email verification, sign out,
                            // show infromation to user, then go to sign in activity.
                            mFirebaseAuth.getCurrentUser().sendEmailVerification();
                            mFirebaseAuth.getCurrentUser().updateProfile(new UserProfileChangeRequest.Builder()
                                    .setDisplayName(username).build());
                            saveUserData(username);
                            mFirebaseAuth.signOut();

                            AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                            builder.setMessage(R.string.email_verification_message_body)
                                    .setTitle(R.string.email_verification_message_title);
                            builder.setPositiveButton(R.string.email_verification_message_button,
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                            builder.create();
                            builder.show();
                        } else {
                            // if sign up failed check if the email used before or not and tell the user the problem.
                            mFirebaseAuth.fetchProvidersForEmail(mEmail.getText().toString().trim())
                                    .addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                                            if (task.isSuccessful()) {
                                                if (task.getResult().getProviders().size() > 0) {
                                                    ///////// getProviders() will return size 1. if email ID is available.
                                                    task.getResult().getProviders();
                                                    mEmail.setError(getString(R.string.email_already_used));
                                                    Toast.makeText(SignUpActivity.this, R.string.email_already_used,
                                                            Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(SignUpActivity.this, "Sign Up failed.", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    //save user data in the database
    private void saveUserData(String username) {

        User currentUser = new User(username);
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        mDatabaseRefrence.child(user.getUid()).setValue(currentUser);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_UP) {
            final GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                final GoogleSignInAccount account = result.getSignInAccount();

                mFirebaseAuth.fetchProvidersForEmail(account.getEmail())
                        .addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
                            @Override
                            public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().getProviders().size() > 0) {

                                        ///////// getProviders() will return size 1. if email ID is available.
                                        Toast.makeText(SignUpActivity.this, R.string.email_already_used,
                                                Toast.LENGTH_SHORT).show();
                                        mProgressDialog.dismiss();
                                    } else {
                                        firebaseAuthWithGoogle(account);
                                    }
                                }
                            }
                        });

            } else {
                // Google Sign In failed, update UI appropriately
                // ...
                mProgressDialog.dismiss();
            }
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            saveUserData(account.getDisplayName());
                            mProgressDialog.dismiss();
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            mProgressDialog.dismiss();
                            Toast.makeText(SignUpActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}