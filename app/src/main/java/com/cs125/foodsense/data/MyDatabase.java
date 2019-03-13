package com.cs125.foodsense.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.cs125.foodsense.data.dao.BodyConstitutionDAO;
import com.cs125.foodsense.data.dao.HeartRateDAO;
import com.cs125.foodsense.data.entity.BodyConstitution;
import com.cs125.foodsense.data.entity.UserConstitution;
import com.cs125.foodsense.data.util.Converters;
import com.cs125.foodsense.data.dao.FoodJournalDAO;
import com.cs125.foodsense.data.dao.FoodRegimenDAO;
import com.cs125.foodsense.data.dao.UserDAO;
import com.cs125.foodsense.data.entity.HeartRate;
import com.cs125.foodsense.data.entity.User;
import com.cs125.foodsense.data.entity.FoodRegimen;
import com.cs125.foodsense.data.entity.FoodJournal;
/*
Database holder that serves as main access point for connection
to your database
 */

/*
   1. static - only one copy, no matter how many objects of class created
         if 2 threads accessing same object/updating variable,
         each thread make own local copy of same object in respective cache
   2.  volatile - not stored in local cache of thread,
               each thread access variable in main memory
               other threads able to access update value
   3. static volatile - singleton
*/

@Database(entities = {User.class,
                        FoodRegimen.class,
                        FoodJournal.class,
                        HeartRate.class,
                        BodyConstitution.class,
                        UserConstitution.class},
         version = 1)
@TypeConverters({Converters.class})
public abstract class MyDatabase extends RoomDatabase {
    // Database name to be used
    public static final String DB_NAME = "app_database";
    private static MyDatabase DB_INSTANCE;

    // Declare data access objects (DAO) as abstract
    // Note: the builder in getDatabase generates necessary code for the methods below
    public abstract UserDAO getUserDAO();
    public abstract FoodRegimenDAO getFoodRegimenDAO();
    public abstract FoodJournalDAO getFoodJournalDAO();
    public abstract BodyConstitutionDAO getBodyConstitutionDAO();
    public abstract HeartRateDAO getHeartRateDAO();


    public static synchronized MyDatabase getDatabase(final Context context){
        if (DB_INSTANCE == null) {      // only instantiate database if not already have instance
            DB_INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyDatabase.class, DB_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)
                            .build();
        }
        return DB_INSTANCE;
    }

    /* NOTE JESSICA: might take out*/
    // Prepopulate tables
    // need to access onCreate method in SQLiteOpenHelper
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(DB_INSTANCE).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private BodyConstitutionDAO bcDao;

        private PopulateDbAsyncTask(MyDatabase db){
            this.bcDao = db.getBodyConstitutionDAO();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("MyDatabase", "Prepopulating body constitutions...");
            bcDao.insert(new BodyConstitution("Hepatonia", "HEP"));
            bcDao.insert(new BodyConstitution("Cholecystonia", "CHO"));
            bcDao.insert(new BodyConstitution("Pancreotonia", "PAN"));
            bcDao.insert(new BodyConstitution("Gastrotonia", "HEP"));
            bcDao.insert(new BodyConstitution("Pulmotonia", "PUL"));
            bcDao.insert(new BodyConstitution("Colonotonia", "COL"));
            bcDao.insert(new BodyConstitution("Renotonia", "REN"));
            bcDao.insert(new BodyConstitution("Vesicotonia", "VES"));
            Log.d("MyDatabase", "Pre-populating body constitutions - DONE");
            return null;
        }
    }


};

