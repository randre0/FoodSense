package com.cs125.foodsense.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.cs125.foodsense.data.MyDatabase;
import com.cs125.foodsense.data.dao.FoodJournalDAO;
import com.cs125.foodsense.data.dao.FoodRegimenDAO;
import com.cs125.foodsense.data.dao.HeartRateDAO;
import com.cs125.foodsense.data.dao.UserDAO;
import com.cs125.foodsense.data.entity.FoodJournal;
import com.cs125.foodsense.data.entity.FoodRegimen;
import com.cs125.foodsense.data.entity.HeartRate;
import com.cs125.foodsense.data.entity.User;
import com.cs125.foodsense.data.entity.UserConstitution;

import java.util.List;

//Repository - use to
//1. abstracts access to multiple data sources
//2. handles data operations such as insert, update, delete, get
//- manages query threads and allows use of multiple back-ends
//
//NOTE:
//- DO NOT PERFORM OPERATION ON MAIN THREAD AS APP WILL CRASH
//- To use database, need to create instance of it in application
//- Room is resource-intensive (BTS: Room doing extra work to manage/map/generate SQL)
//    -> use singleton pattern
//       Create instance of database when app loaded
//       Then reference it at Application level

public class MyRepository {

    private String DB_NAME = "app_database";
    private static volatile MyDatabase DB_INSTANCE;

    // Date access objects
    private FoodRegimenDAO foodRegDAO;
    private FoodJournalDAO foodJournalDAO;
    private UserDAO userDAO;
    private HeartRateDAO heartRateDAO;

    // LiveData
    public LiveData<User> userProfile;
    private User userProfileStatic;
    private LiveData<List<FoodRegimen>> allRegimen;
    private LiveData<List<FoodJournal>> myFoodJournal;


    // Repository
    public MyRepository(@NonNull Application application) {
        // application is a context
        MyDatabase db = MyDatabase.getDatabase(application);

        foodRegDAO = db.getFoodRegimenDAO();
        userDAO = db.getUserDAO();
        heartRateDAO = db.getHeartRateDAO();
        foodJournalDAO = db.getFoodJournalDAO();
        allRegimen = foodRegDAO.getFoodReg();
    }

    /*------------------------- USER ----------------------------------------------*/
    public LiveData<User> getUser(String email) {
        return userDAO.getUserInfo(email);
    }

    public User getUserStatic(String email) {
        return userDAO.getUserInfoStatic(email);
    }

    public void insertUser(@NonNull User user) {
        try {
            new InsertUserAsyncTask(userDAO).execute(user);
            Log.d("MyRepository (User)", "Completed InsertUserAsyncTask");
        } catch (Exception e) {
            Log.d("MyRepository (User)", "Failed to insert user");
        }
    }

    public void updateUser(User user) {
        try {
            new UpdateUserAsyncTask(userDAO).execute(user);
        } catch (Exception e) {
            Log.d("MyRepository (User)", "Failed to update user");
        }
    }

    private static class InsertUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDAO userDao;
        private User user;

        private InsertUserAsyncTask(UserDAO userDao) {
            this.userDao = userDao;
        }

        @Override   // must have this override function
        protected Void doInBackground(User... users) {
            user = users[0];
            try {
                if (userDao.isInTable(user.getEmail()) == 0) {
                    userDao.insert(users[0]);
                }

                Log.d("MyRepository", "Inserted " + user);
            } catch (Exception e) {
                Log.d("MyRepository", "Failed to insert " + user);
            }

            return null;
        }
    }

    private static class UpdateUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDAO userDao;

        private UpdateUserAsyncTask(UserDAO userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... user) {
            try {
                userDao.update(user[0]);
                Log.d("MyRepository", "Updated ");
            } catch (Exception e) {
                Log.d("MyRepository", "Failed to update ");
            }
            return null;
        }
    }
//    private static class FetchUserAsyncTask extends AsyncTask<String, Void, Void>{
//        private UserDAO userDao;
//        private LiveData<User> user;
//        private User userStatic;
//        private FetchUserAsyncTask(UserDAO userDao){
//            this.userDao = userDao;
//        }
//        @Override
//        protected Void doInBackground(String... params) {
//            String userEmail = params[0];
//            user = userDao.getUserInfo(userEmail);
//            return null;
//        }
//    }

    // need to execute ourselves b/c room database doesn't allow on main thread
    // --> need to create multiple asynctasks
    //      need to be static so no reference to repository itself or this will cause memory leak

    /* --------------------- HEART RATE ----------------------------------------------*/
    public LiveData<List<HeartRate>> getAllHeartRateByUser(String email) {
        return heartRateDAO.getAllHRByUser(email);
    }

    public void insertHeartRate(HeartRate hr) {
        new InsertHeartRateAsyncTask(heartRateDAO).execute(hr);
    }

    private static class InsertHeartRateAsyncTask extends AsyncTask<HeartRate, Void, Void> {
        private HeartRateDAO hrDao;

        private InsertHeartRateAsyncTask(HeartRateDAO hrDao) {
            this.hrDao = hrDao;
        }

        @Override   // must have this override function
        protected Void doInBackground(HeartRate... hr) {
            try {
                hrDao.insert(hr[0]);
                Log.d("MyRepository", "Inserted " + hr[0]);
            }
            catch (NullPointerException npe){
                Log.d("MyRepository", "Null Pointer Exception - Failed to insert " + hr[0]);
            }
            catch (Exception e) {
                Log.d("MyRepository", "Failed to insert " + hr[0]);
            }
            return null;
        }
    }


    /* ---------------------- FOOD REGIMEN -------------------------------------- */
    public LiveData<List<FoodRegimen>> getAllRegimen() {
        return allRegimen;
    }

    public void insertFoodRegimen(FoodRegimen fr) {
        new InsertFoodRegimenAsyncTask(foodRegDAO).execute(fr); // if not exist in table
    }

    private static class InsertFoodRegimenAsyncTask extends AsyncTask<FoodRegimen, Void, Void> {
        private FoodRegimenDAO frDao;

        private InsertFoodRegimenAsyncTask(FoodRegimenDAO frDao) {
            this.frDao = frDao;
        }

        @Override   // must have this override function
        protected Void doInBackground(FoodRegimen... fr) {
            FoodRegimen regimen = fr[0];

            try {
                if (frDao.isInTable(regimen.getFoodDesc()) == 0) {
                    frDao.insert(regimen);
                    Log.d("MyRepository", "Inserted " + regimen);
                }
                else{
                    Log.d("MyRepository", "Already exists " + regimen);
                }
            }
            catch (NullPointerException npe){
                Log.d("MyRepository", "Null Pointer Exception - Failed to insert " + regimen);
            }
            catch (Exception e) {
                Log.d("MyRepository", "Failed to insert  " + regimen);
            }
            return null;
        }
    }


    /* -------------------- FOOD JOURNAL ------------------------------------------*/
    public LiveData<List<FoodJournal>> getMyFoodJournal(String userEmail) {
        return foodJournalDAO.getFoodJournalByUser(userEmail);
    }

    public void insertIntoFoodJournal(FoodJournal fj) {
        new InsertFoodJournalAsyncTask(foodJournalDAO).execute(fj);
    }

    public void updateHRDiff(FoodJournal fj) {
        new UpdateHrDiffAsyncTask(foodJournalDAO).execute(fj);
    }

    private static class InsertFoodJournalAsyncTask extends AsyncTask<FoodJournal, Void, Void> {
        private FoodJournalDAO fjDao;

        private InsertFoodJournalAsyncTask(FoodJournalDAO fjDao) {
            this.fjDao = fjDao;
        }

        @Override
        protected Void doInBackground(FoodJournal... fj) {
            //Log.d("hello",fj[0].toString());
            //fjDao.insert(fj[0]);
            FoodJournal foodEntry = fj[0];
            try {
                fjDao.insert(foodEntry);
                Log.d("MyRepository", "Inserted " + foodEntry);
            }
            catch (NullPointerException npe){
                Log.d("MyRepository", "Null Pointer Exception - Failed to insert " + foodEntry);
            }
            catch (Exception e) {
                Log.d("MyRepository", "Failed to insert " + foodEntry);
            }

            return null;
        }
    }

    private static class UpdateHrDiffAsyncTask extends AsyncTask<FoodJournal, Void, Void> {
        private FoodJournalDAO fjDao;

        private UpdateHrDiffAsyncTask(FoodJournalDAO fjDao) {
            this.fjDao = fjDao;
        }

        @Override
        protected Void doInBackground(FoodJournal... fj) {
            FoodJournal foodEntry = fj[0];
            try {
                fjDao.updateHrDiff(foodEntry.getUserEmail(), foodEntry.getFood(),
                        foodEntry.getHrDiff());
                Log.d("MyRepository", "Updated " + foodEntry);
            } catch (Exception e) {
                Log.d("MyRepository", "Failed to update " + foodEntry);
            }
            return null;
        }
    }




}



