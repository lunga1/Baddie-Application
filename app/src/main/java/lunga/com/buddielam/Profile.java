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
import android.provider.CalendarContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

public class Profile extends AppCompatActivity {

    FirebaseUser user;
    DatabaseReference reference;

    //drwawer variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    //get current date
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        overridePendingTransition(R.anim.from_botton, R.anim.from_top);

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

        //reminder button action
        ImageView imgView  = findViewById(R.id.set_med_reminder);

        imgView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShortAlarm")
            @Override
            public void onClick(View v) {
                //TODO: SET REMINDER GOES HERE...
                //gets
                //Intent intent = new Intent(Profile.this, backgroundProcess.class);
                //intent.setAction("BackgroundProcess");
                //set repeated task...
                //PendingIntent pendingIntent = PendingIntent.getBroadcast(Profile.this, 0, intent,0);
                //AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, 0, 0, pendingIntent);

            }
        });
        //ends here


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

                       //NAVIGATION ITEMS CODE GOES HERE...
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
                                   //TODO: set alarm code here
                                   Intent intent = new Intent(Intent.ACTION_INSERT);
                                   intent.setData(CalendarContract.Events.CONTENT_URI);
                                   intent.putExtra(CalendarContract.Events.TITLE, "Set Appointment Title...");
                                   intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Set Appointment location...");
                                   intent.putExtra(CalendarContract.Events.DESCRIPTION, "set Appointment Description...");
                                   intent.putExtra(CalendarContract.Events.ALL_DAY, true);
                                   //intent.putExtra(Intent.EXTRA_EMAIL, "testdoctor@hotmail.com"); //<extra email invitation/s

                                   startActivity(intent); //<- start thr calender intent...

                               }
                               if (item.getItemId() == R.id.m_contact_doctor){
                                   Toast.makeText(Profile.this, "Calling Doctor...", Toast.LENGTH_SHORT).show();
                                   Intent intent = new Intent(Intent.ACTION_CALL);
                                   intent.setData(Uri.parse("tel:" + "0000000000")); //<- not the actual doctors number but mine...
                                   startActivity(intent);
                               }
                               if (item.getItemId() == R.id.m_edit){
                                   Intent edit_intent = new Intent(Profile.this, EditProfile.class);
                                   edit_intent.putExtra("user_email", email);
                                   startActivity(edit_intent);
                               }
                               if (item.getItemId() == R.id.m_request_appointment){
                                   //todo: request the next appointment (maybe via email)...
                                   String recipientList = "doctorTest@gmail.com";
                                   String [] recipients = recipientList.split(","); //<if theres more than one...

                                   Intent intent = new Intent(Intent.ACTION_SEND);
                                   intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                                   intent.putExtra(Intent.EXTRA_SUBJECT, "NEW APPOINTMENT REQUEST");
                                   intent.putExtra(Intent.EXTRA_TEXT, "Dear Doc, \n\nI would like to make an appointment on the following date... \n\nKind Regards\nClient Signature");
                                   intent.setType("message/rfc822");
                                   startActivity(Intent.createChooser(intent, "Choose an email client"));
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