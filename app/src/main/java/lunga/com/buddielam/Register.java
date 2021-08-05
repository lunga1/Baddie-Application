package lunga.com.buddielam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Register extends AppCompatActivity {

    //authentication
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser fbUser = mAuth.getCurrentUser();
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        overridePendingTransition(R.anim.from_top, R.anim.from_botton); //(transition out, transition in)

        //writing info
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("BaddieUsers");

        //myRef.setValue("Hello world two");

        //editTexts declarations
        EditText reg_name = findViewById(R.id.full_name);
        EditText reg_email = findViewById(R.id.email);
        EditText reg_gender = findViewById(R.id.gender);
        EditText reg_age = findViewById(R.id.age);
        EditText reg_dob = findViewById(R.id.date_of_birth);
        EditText reg_phone_number = findViewById(R.id.phone_number);
        EditText reg_location = findViewById(R.id.location);
        EditText reg_password = findViewById(R.id.password);
        EditText reg_confirm_password = findViewById(R.id.confirm_password);
        ProgressBar loadingBar = findViewById(R.id.loading);

        loadingBar.setVisibility(View.GONE);

        Button reg_btn = findViewById(R.id.register);

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get strings from editTexts
                String regName = reg_name.getText().toString();
                String regEmail = reg_email.getText().toString();
                String regGender = reg_gender.getText().toString();
                String regAge = reg_age.getText().toString();
                String regDob= reg_dob.getText().toString();
                String regPhoneNumber= reg_phone_number.getText().toString();
                String regLocation = reg_location.getText().toString();
                String regPassword = reg_password.getText().toString();
                String regConfirmPassword = reg_confirm_password.getText().toString();
                String NextAppointment = "N/A", Medication = "N/A";

                //editTexts error handling
                if (regName.isEmpty()){
                    reg_name.setError("Don't leave this field empty.");
                }
                else if (regEmail.isEmpty() || regEmail.contains(" ")){
                    reg_email.setError("This might contain a white space or empty. Please don't leave this field empty.");
                }
                else if (regGender.isEmpty()){
                    reg_gender.setError("Don't leave this field empty.");
                }
                else if (regAge.isEmpty()){
                    reg_age.setError("Don't leave this field empty.");
                }
                else if (regDob.isEmpty()){
                    reg_dob.setError("Don't leave this field empty.");
                }
                else if (regPhoneNumber.isEmpty()){
                    reg_phone_number.setError("Don't leave this field empty.");
                }
                else if (regLocation.isEmpty()){
                    reg_location.setError("Don't leave this field empty.");
                }
                else if (regPassword.isEmpty()){
                    reg_password.setError("Don't leave this field empty.");
                }
                else if (regConfirmPassword.isEmpty()){
                    reg_confirm_password.setError("Don't  leave this field empty.");
                }
                else if (!regPassword.equals(regConfirmPassword)){
                    reg_confirm_password.setError("Passwords don't match.");
                    reg_password.setError("Passwords don't match.");
                }
                else
                    {
                    //authentication code
                    mAuth.createUserWithEmailAndPassword(regEmail, regPassword).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            loadingBar.setVisibility(View.VISIBLE);
                            if(task.isSuccessful()){
                                //created in successfully
                                //if user created successfully the send authentication email
                                mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            //write to firebase...
                                            //todo: add extra fiends that will be needed on the profile
                                            User user = new User(regName, regEmail, regGender, regAge, regDob, regPhoneNumber, regLocation, Medication, NextAppointment);
                                            myRef.child(replaceDotWithComa(regEmail)).setValue(user); //<correctly working

                                            Toast.makeText(Register.this, "registered successful, please check your email inbox for email verification",
                                                    Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(Register.this, Login.class));
                                        }else{
                                            Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }else{
                                Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    //authentication code end...
                }
            }
        });
    }

    public void back_to_home(View view) { startActivity(new Intent(this, Login.class)); }

    public static String replaceDotWithComa(String word) {
        return word.replaceAll("[.]", ",");
    }

    private static String replaceComaWithDot(String word) {
        return word.replaceAll("[,]", ".");
    }
}