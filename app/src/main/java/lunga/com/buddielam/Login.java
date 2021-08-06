package lunga.com.buddielam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        overridePendingTransition(R.anim.from_top, R.anim.from_botton);

        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        Button login_btn = findViewById(R.id.login);
        ProgressBar loginProgress = findViewById(R.id.login_progress);
        CheckBox remember_me = (CheckBox) findViewById(R.id.remember_me);
        Button diff_user = findViewById(R.id.different_user);
        loginProgress.setVisibility(View.GONE);

        /*create a remember me session*/
        SessionManager sessionManager = new SessionManager(Login.this, SessionManager.SESSION_REMEMBERME);
        if (sessionManager.checkRememberMe())
        {
            HashMap <String, String> rememberMeDetails = sessionManager.getRememberMeDetailsFromSession();
            email.setText(rememberMeDetails.get(SessionManager.KEY_SESSIONEMAIL));
            password.setText(rememberMeDetails.get(SessionManager.KEY_PASSWORD));
        }

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //login code goes here...
                loginProgress.setVisibility(View.VISIBLE);

                if (email.getText().toString().isEmpty()){
                    email.setError("Don't leave this field empty.");
                    loginProgress.setVisibility(View.GONE);
                }
                else if (password.getText().toString().isEmpty()){
                    password.setError("Don't leave this field empty.");
                    loginProgress.setVisibility(View.GONE);
                }
                else {
                    //login code start
                    String uEmail = email.getText().toString();
                    String uPassword = password.getText().toString();

                    firebaseAuth.signInWithEmailAndPassword(uEmail, uPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //progress bar goes here progressBar.setVisibility(visibility.GONE);
                            if (task.isSuccessful()){

                                if (firebaseAuth.getCurrentUser().isEmailVerified()){

                                    if (remember_me.isChecked()){
                                        //todo: remember me code...
                                        //SessionManager sessionManager = new SessionManager(Login.this, SessionManager.SESSION_REMEMBERME);
                                        sessionManager.createRememberMeSession(uEmail, uPassword);
                                    }
                                    /*remember me ends*/

                                    startActivity(new Intent(Login.this, Profile.class));
                                    loginProgress.setVisibility(View.GONE);
                                    /*remember me code*/


                                }else {
                                    Toast.makeText(Login.this, "Please verify your email address.", Toast.LENGTH_SHORT).show();
                                    loginProgress.setVisibility(View.GONE);
                                }
                            }else {
                                Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                loginProgress.setVisibility(View.GONE);
                            }
                        }
                    });
                    //login code ends here...
                }
            }
        });
        //button ends here

        /*different user code*/
        diff_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.setText("");
                password.setText("");
                Toast.makeText(Login.this, "Fields Cleared...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void goToRegister(View view) {
        //go to the register activity...
        startActivity(new Intent(this, Register.class));
    }

    public void toResetPassword(View view) {
        startActivity(new Intent(Login.this, ResetPassword.class));
    }
}