package lunga.com.buddielam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.internal.NavigationMenuItemView;
import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Objects;

public class Profile extends AppCompatActivity {

    FirebaseUser user;
    DatabaseReference reference;

    //drwawer variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        overridePendingTransition(R.anim.from_botton, R.anim.from_top);

        // Set the alarm to start at approximately 2:00 p.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.MINUTE, 45);
        //TODO: FIX THE TIME REMINDER CREATOR

        //------

         drawerLayout = findViewById(R.id.drawer_layout);
         navigationView = findViewById(R.id.nav_view);
         toolbar = findViewById(R.id.toolbar);

         //navigation code
        navigationView.bringToFront();
         ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(Profile.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close ) ;
         drawerLayout.addDrawerListener(toggle);
         toggle.syncState();

         navigationView.setCheckedItem(R.id.nav_view);
         MenuItem menu_name = navigationView.getMenu().findItem(R.id.m_name);
         MenuItem menu_request = navigationView.getMenu().findItem(R.id.m_request_appointment);
         MenuItem menu_phone = navigationView.getMenu().findItem(R.id.m_phone);
         MenuItem menu_email = navigationView.getMenu().findItem(R.id.m_email);

        TextView clocation = findViewById(R.id.location);
        TextView cmedication = findViewById(R.id.medication);
        TextView cnextAppointment = findViewById(R.id.nextAppointment);

        //read code start

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("BaddieUsers");
        String uid = replaceDotWithComa(Objects.requireNonNull(user.getEmail())); //<- "lungamalinga10@hotmail,com";

            reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userProfile = snapshot.getValue(User.class);

                   if (userProfile != null) {
                        String fullname = userProfile.full_name;
                        final String email = userProfile.email;
                        String gender = userProfile.gender;
                        String dob = userProfile.dob;
                        String location = userProfile.location;
                        String medication = userProfile.medication;
                        String nextAppointment = userProfile.nextAppointment;
                        String phone = userProfile.phone;
                        String age = userProfile.age;


                       //set mains
                        clocation.setText(location);
                        cmedication.setText(medication);
                        cnextAppointment.setText(nextAppointment);

                        //set menu
                       menu_name.setTitle(fullname);
                       menu_phone.setTitle(phone);
                       menu_email.setTitle(email);

                       //NAVIGATION ITEM CODE GOES HERE...
                       navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                           @Override
                           public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                               if (item.getItemId() == R.id.m_logout) {
                                   FirebaseAuth.getInstance().signOut();
                                   startActivity(new Intent(Profile.this, Login.class));
                                   Toast.makeText(Profile.this, "Logged out..", Toast.LENGTH_LONG).show();
                                   finish();
                               }
                               if (item.getItemId() == R.id.m_set_reminder){
                                   Toast.makeText(Profile.this, "Alarm set pressed.", Toast.LENGTH_SHORT).show();
                               }
                               if (item.getItemId() == R.id.m_contact_doctor){
                                   //TODO: CALL CODE GOES HERE...
                                   Toast.makeText(Profile.this, "Calling Doctor...", Toast.LENGTH_SHORT).show();
                                   Intent intent = new Intent(Intent.ACTION_CALL);
                                   intent.setData(Uri.parse("tel:" + "0783910030"));
                                   startActivity(intent);

                               }
                               if (item.getItemId() == R.id.m_edit){
                                   Intent edit_intent = new Intent(Profile.this, EditProfile.class);
                                   edit_intent.putExtra("user_email", email);
                                   startActivity(edit_intent);
                               }

                               drawerLayout.closeDrawer(GravityCompat.START);
                               return true;
                           }
                       });
                       //NAVIGATION ITEM CODE GOES ENDS HERE...

                   }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Profile.this, "error occurred", Toast.LENGTH_LONG).show();
                }
            });
        //read end...

    }

    //when back is pressed while drawer is open...
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    //functions
    public static String replaceDotWithComa(String word) {
        return word.replaceAll("[.]", ",");
    }
    private static String replaceComaWithDot(String word) {
        return word.replaceAll("[,]", ".");
    }

    //navigation item selections (old position)...

}