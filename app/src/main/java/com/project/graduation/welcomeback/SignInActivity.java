package com.project.graduation.welcomeback;

/**
 * created by: Ayyad Shenouda
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.firebase.client.Firebase;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.ProviderQueryResult;


/*
 *this screen for sign in if process fall show toast.
 */

public class SignInActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1;

    private EditText mEmail;            //email edit text
    private EditText mPassword;         //password edit text

    private Button mSignInButton;       //sign in button
    private SignInButton mGoogleSignIn;    //google sign in method
    private TextView mForgetPassword;  // for send forget password email

    private ProgressDialog mProgressDialog; // show information to user.

    private FirebaseAuth mFirebaseAuth; // firebase authintication.

    private GoogleApiClient mGoogleApiClient;

    TextView mSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // initialize our instance variables.
        mEmail = (EditText) findViewById(R.id.sign_in_email);
        mPassword = (EditText) findViewById(R.id.sign_in_password);
        mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mGoogleSignIn = (SignInButton) findViewById(R.id.google_login_button);
        mForgetPassword = (TextView) findViewById(R.id.forget_password);

        mSignUp = (TextView) findViewById(R.id.go_to_sign_up);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.sign_in_dialog));

        mFirebaseAuth = FirebaseAuth.getInstance();

        // add listener to mSignIp text view to go to sign up activity.
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });


        // add listener to sign in button to complete the process
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check intener connection
                if (!isNetworkConnected()) {
                    Toast.makeText(SignInActivity.this, R.string.no_internet, Toast.LENGTH_SHORT).show();
                    return;
                }
                //check the validation of the email, password.
                if (!validate()) {
                    return;
                }
                //show dialog to user.
                mProgressDialog.show();

                signIn();
            }
        });

        mForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNetworkConnected()) {
                    Toast.makeText(SignInActivity.this, R.string.no_internet, Toast.LENGTH_SHORT).show();
                    return;
                }

                final EditText input = new EditText(SignInActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setInputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS |
                        InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

                AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
                builder.setMessage(R.string.enter_email_here)
                        .setTitle(R.string.reset_password_title);
                builder.setPositiveButton(R.string.reset_password_button,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String email = input.getText().toString().trim();

                                mFirebaseAuth.sendPasswordResetEmail(email)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(SignInActivity.this, R.string.send_Email_success
                                                            , Toast.LENGTH_LONG).show();

                                                } else {
                                                    Toast.makeText(SignInActivity.this, R.string.send_email_failed
                                                            , Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                            }
                        });
                builder.setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });

                builder.setView(input);
                builder.create();
                builder.show();

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

                        Toast.makeText(SignInActivity.this, R.string.conncetion_failed, Toast.LENGTH_SHORT);
                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isNetworkConnected()) {
                    Toast.makeText(SignInActivity.this, R.string.no_internet, Toast.LENGTH_SHORT).show();
                    return;
                }

                mProgressDialog.show();
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

    }

    // this method check the validation of the username, password, and email of the edit texts.
    private boolean validate() {
        boolean valid = true;

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

    private void signIn() {
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mProgressDialog.dismiss();
                        if (task.isSuccessful()) {
                            if (mFirebaseAuth.getCurrentUser().isEmailVerified()) {
                                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
                                builder.setMessage(R.string.email_not_verified)
                                        .setTitle(R.string.email_verification_message_title);
                                builder.setPositiveButton(R.string.email_verification_message_button,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                mFirebaseAuth.signOut();
                                            }
                                        });
                                builder.setNegativeButton(R.string.resend_email,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                mFirebaseAuth.getCurrentUser().sendEmailVerification();
                                                mFirebaseAuth.signOut();
                                            }
                                        });

                                builder.create();
                                builder.show();
                            }
                        } else {
                            Toast.makeText(SignInActivity.this, R.string.sign_in_failed, Toast.LENGTH_SHORT).show();
                            mProgressDialog.dismiss();
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
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
                                        firebaseAuthWithGoogle(account);
                                    } else {
                                        Toast.makeText(SignInActivity.this, "Email not found",
                                                Toast.LENGTH_SHORT).show();
                                        mProgressDialog.dismiss();
                                    }
                                } else {

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
                            mProgressDialog.dismiss();
                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignInActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                            mProgressDialog.dismiss();
                        }
                        // ...
                    }
                });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}