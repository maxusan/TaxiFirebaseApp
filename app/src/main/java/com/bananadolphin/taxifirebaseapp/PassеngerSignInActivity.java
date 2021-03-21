package com.bananadolphin.taxifirebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PassеngerSignInActivity extends AppCompatActivity {

    private static final String TAG = "myLogs";

    private TextInputLayout textInputEmail;
    private TextInputLayout textInputName;
    private TextInputLayout textInputPassword;
    private TextInputLayout textInputConfirmPassword;

    private Button loginSignUpButton;

    private TextView toggleLoginSignUpTextView;

    private boolean isLoginModeActive = false;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_sign_in);

        textInputEmail = findViewById(R.id.textInputEmail);
        textInputName = findViewById(R.id.textInputName);
        textInputPassword = findViewById(R.id.textInputPassword);
        textInputConfirmPassword = findViewById(R.id.textInputConfirmPassword);

        loginSignUpButton = findViewById(R.id.loginSighUpButton);

        toggleLoginSignUpTextView = findViewById(R.id.toggleLoginSignUpTextView);

        auth = FirebaseAuth.getInstance();
    }

    private boolean validateEmail(){
        String emailInput = textInputEmail.getEditText().getText().toString().trim();
        if(emailInput.isEmpty()){
            textInputEmail.setError("Please input ur email");
            return false;
        }else{
            textInputEmail.setError("");
            return true;
        }
    }

    private boolean validateName(){
        String nameInput = textInputName.getEditText().getText().toString().trim();
        if(nameInput.isEmpty()){
            textInputName.setError("Please input ur name");
            return false;
        } else if(nameInput.length() > 15){
            textInputName.setError("Name length have to be less than 15");
            return false;
        }else{
            textInputName.setError("");
            return true;
        }
    }

    private boolean validatePassword(){
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();

        if(passwordInput.isEmpty()){
            textInputPassword.setError("Please input ur password");
            return false;
        } else if(passwordInput.length() < 7){
            textInputPassword.setError("Password length have to be more than 6");
            return false;
        } else {
            textInputPassword.setError("");
            return true;
        }
    }

    private boolean validateConfirmPassword(){
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();
        String passwordConfirmInput = textInputConfirmPassword.getEditText().getText().toString().trim();

        if(!passwordInput.equals(passwordConfirmInput)){
            textInputPassword.setError("Passwords have to match");
            return false;
        }
        else{
            textInputPassword.setError("");
            return true;
        }
    }


    public void loginSignUpUser(View view) {
        if(!validateEmail() | !validateName() | !validatePassword()){
            return;
        }

        String email = textInputEmail.getEditText().getText().toString().trim();;
        String password =  textInputPassword.getEditText().getText().toString().trim();


        if (isLoginModeActive){
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = auth.getCurrentUser();
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(PassеngerSignInActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                        }
                    });
        }else {
            if(!validateEmail() | !validateName() | !validatePassword() | !validateConfirmPassword()){
                return;
            }
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = auth.getCurrentUser();
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(PassеngerSignInActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                        }
                    });
        }

    }

    public void toggleLoginSignUp(View view) {

        if(isLoginModeActive){
            isLoginModeActive = false;
            loginSignUpButton.setText(R.string.sign_up_text);
            toggleLoginSignUpTextView.setText(R.string.or_log_in_text);
            textInputConfirmPassword.setVisibility(View.VISIBLE);
        }else {
            isLoginModeActive = true;
            loginSignUpButton.setText(R.string.log_in_text);
            toggleLoginSignUpTextView.setText(R.string.or_sign_up_text);
            textInputConfirmPassword.setVisibility(View.GONE);
        }
    }
}