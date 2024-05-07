package kitkatstudio.com.taskmanagement.prefManager;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String USER_EMAIL_ID = "UserEmailId";
    private static final String USER_NAME = "UserName";
    private static final String NAME = "Name";
    private static final String USER_DESIGNATION = "Designation";
    private static final String USER_CONTACT = "Contact";
//    private static final String USER_DOB = "UserDob";
//    private static final String USER_SECTION = "UserSection";

    private static final String ADMIN_EMAIL_ID = "AdminEmailId";
    private static final String ADMIN_NAME = "AdminName";

    //shared pref file name
    private static final String PREF_NAME = "task";

    public PrefManager(Context context)
    {
        int PRIVATE_MODE = 0;
        sharedPreferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    //public void setUserEmailId(String string, String name,String designation,String contact){

   public void setUserEmailId(String string, String name){
        editor.putString(USER_EMAIL_ID,string);
        editor.putString(NAME,name);
//        editor.putString(USER_DESIGNATION,designation);
//        editor.putString(USER_CONTACT,contact);
        editor.commit();
    }


    public void setAdminEmailId(String string, String name){
        editor.putString(ADMIN_EMAIL_ID,string);
        editor.putString(ADMIN_NAME,name);
//        editor.putString(USER_DESIGNATION,designation);
//        editor.putString(USER_CONTACT,contact);
        editor.commit();
    }

    public String getUserEmailId(){
        return sharedPreferences.getString(USER_EMAIL_ID,"");
    }


    public String getName(){
        return sharedPreferences.getString(NAME,"");
    }



    public String getAdminEmailId(){
        return sharedPreferences.getString(ADMIN_EMAIL_ID,"");
    }


    public String getAdminName(){
        return sharedPreferences.getString(ADMIN_NAME,"");
    }

    public String getUserDesignation(){
        return sharedPreferences.getString(USER_DESIGNATION,"");
    }

    public String getUserContact(){
        return sharedPreferences.getString(USER_CONTACT,"");
    }

}
