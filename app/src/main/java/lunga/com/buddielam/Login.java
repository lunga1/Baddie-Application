package lunga.com.buddielam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        overridePendingTransition(R.anim.from_top, R.anim.from_botton);

        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        Button login_btn = findViewById(R.id.login);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //login code goes here...

                if (email.getText().toString().isEmpty()){
                    email.setError("Don't leave this field empty.");
                }
                else if (password.getText().toString().isEmpty()){
                    password.setError("Don't leave this field empty.");
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
                                    startActivity(new Intent(Login.this, Profile.class));
                                }else {
                                    Toast.makeText(Login.this, "Please verify your email address.", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    //login code ends here...
                }
            }
        });
        //button ends here
    }

    public void goToRegister(View view) {
        //go to the register activity...
        startActivity(new Intent(this, Register.class));
    }

    public void toResetPassword(View view) {
        startActivity(new Intent(Login.this, ResetPassword.class));
    }
}