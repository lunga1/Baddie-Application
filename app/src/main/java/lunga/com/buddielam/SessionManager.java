package lunga.com.buddielam;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashMap;

public class SessionManager {

    //variables
    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;

    //session Names
    public static final String SESSION_REMEMBERME = "rememberMe";

    /*user session variables*/
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";

    /*remember me variables*/
    public static final String IS_REMEMBERME = "IsRememberMe";
    public static final String KEY_SESSIONEMAIL = "email";
    public static final String KEY_SESSIONPASSWORD = "password";


    public SessionManager(Context _context, String sessionName)
    {
        context = _context;
        userSession = context.getSharedPreferences(sessionName, Context.MODE_PRIVATE);
        editor = userSession.edit();
    }

/*
* Remember me session functions
*
* */

    public void createRememberMeSession(String email, String password){
        editor.putBoolean(IS_REMEMBERME, true);
        editor.putString(KEY_SESSIONEMAIL, email);
        editor.putString(KEY_SESSIONPASSWORD, password);
        editor.commit();
    }

    public HashMap<String, String> getRememberMeDetailsFromSession()
    {
        HashMap<String, String> userData = new HashMap<>(); //~ in the arrows <- todo:

        userData.put(KEY_SESSIONEMAIL, userSession.getString(KEY_SESSIONEMAIL, null));
        userData.put(KEY_SESSIONPASSWORD, userSession.getString(KEY_SESSIONPASSWORD, null));

        return userData;
    }

    public boolean checkRememberMe(){
        if (userSession.getBoolean(IS_REMEMBERME, false)){
            return  true;
        }else {
            return false;
        }
    }
}
