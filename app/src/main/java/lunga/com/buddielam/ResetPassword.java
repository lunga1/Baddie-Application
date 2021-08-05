package lunga.com.buddielam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ResetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        Button resetButton = findViewById(R.id.reset_password);
        TextInputEditText email = findViewById(R.id.email_to_be_reset);
        ProgressBar progressBar = findViewById(R.id.reset_load);
        progressBar.setVisibility(View.GONE);

        FirebaseAuth auth = FirebaseAuth.getInstance();

         /* call button action

         TODO: below call code works, just replace it in the call button on the drawer...
         Intent intent = new Intent(Intent.ACTION_CALL);
         intent.setData(Uri.parse("tel:" + phoneNumber.getText().toString()));
         startActivity(intent);

         call action end code */

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Objects.requireNonNull(email.getText()).toString().isEmpty()){
                    email.setError("Don't leave this field empty.");
                    email.requestFocus();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()){
                    email.setError("Please enter a valid email address.");
                }else{

                    progressBar.setVisibility(View.VISIBLE);
                    auth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ResetPassword.this, "Please check your email for the reset link."
                                        , Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                email.setText("");
                                startActivity(new Intent(ResetPassword.this, Login.class));
                            }else{
                                Toast.makeText(ResetPassword.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        });
    }
}