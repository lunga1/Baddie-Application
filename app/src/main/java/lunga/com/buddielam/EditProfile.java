package lunga.com.buddielam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    Button update, back_home;
    EditText full_name, gender, age, dob, phone, location, medication, nextAppointment;
    private DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //parsed data (email) from the login activity
        TextView tv = findViewById(R.id.e_title); //<-todo: to be deleted (used just for testing of the parsed information)
        Bundle extras = getIntent().getExtras();
        String email_id = extras.getString("user_email");
        tv.setText(email_id);//<for test todo: should be deleted
        //parsed data

        //editTexts
        full_name = findViewById(R.id.e_full_name);//<- todo: initialise all the components
        gender = findViewById(R.id.e_gender);
        age = findViewById(R.id.e_age);
        dob = findViewById(R.id.e_date_of_birth);
        phone = findViewById(R.id.e_phone_number);
        location = findViewById(R.id.e_location);
        medication = findViewById(R.id.e_medication);
        nextAppointment = findViewById(R.id.e_next_appointment);

        update = findViewById(R.id.update);
        back_home = findViewById(R.id.e_back_to_home);
        dbReference = FirebaseDatabase.getInstance().getReference().child("BaddieUsers");

        dbReference.child(replaceDotWithComa(email_id)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    Map<String, Object> map = (Map<String, Object>)dataSnapshot.getValue();

                    //get data from database
                    assert map != null;
                    String nameObject = (String) map.get("full_name");
                    String genderObject = (String) map.get("gender");
                    String ageObject = (String) map.get("age");
                    String dobObject = (String)map.get("dob");
                    String phoneObject = (String)map.get("phone");
                    String locationObject = (String)map.get("location");
                    String medicationObject = (String)map.get("medication");
                    String nextAppObject = (String) map.get("nextAppointment");
                    //set info
                    full_name.setText(nameObject);
                    gender.setText(genderObject);
                    age.setText(ageObject);
                    dob.setText(dobObject);
                    phone.setText(phoneObject);
                    location.setText(locationObject);
                    medication.setText(medicationObject);
                    nextAppointment.setText(nextAppObject);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //update button
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update code...
                //get data from edittexts
                String updateName = full_name.getText().toString();
                String updateGender = gender.getText().toString();
                String updateAge = age.getText().toString();
                String updateDob = dob  .getText().toString();
                 String updatePhone = phone.getText().toString();
                String updateLocation = location .getText().toString();
                String updateMedication = medication  .getText().toString();
                String updateNextAppointment = nextAppointment.getText().toString();



                HashMap hashMap = new HashMap();
                hashMap.put("full_name", updateName);
                hashMap.put("gender", updateGender);
                hashMap.put("age",updateAge );
                hashMap.put("dob", updateDob);
                hashMap.put("phone", updatePhone);
                hashMap.put("location", updateLocation);
                hashMap.put("medication", updateMedication);
                hashMap.put("nextAppointment", updateNextAppointment);

                dbReference.child(replaceDotWithComa(email_id)).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(EditProfile.this  , "Successfully updated user information.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditProfile.this, Profile.class));
                    }
                });
            }
        });

        //back button
        back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfile.this, Profile.class));
            }
        });

    }

    public static String replaceDotWithComa(String word) {
        return word.replaceAll("[.]", ",");
    }
}