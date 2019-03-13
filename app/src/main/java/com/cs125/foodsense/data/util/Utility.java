package com.cs125.foodsense.data.util;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class Utility {
    public static LocalDateTime getCurrentDateTime(){
        //Date current = Calendar.getInstance().getTime();
        LocalDateTime current = LocalDateTime.now();
        return current;
    }

    public static String getFormattedDateString(LocalDateTime date){
        try{
            SimpleDateFormat spf= new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
            return spf.format(date);
        }
        catch (Exception e){
            Log.d("Format", "Failed to format date to string");
        }
        return null;
    }

    public static int toInt(String str){
        return Integer.parseInt(str.trim());
    }
}
