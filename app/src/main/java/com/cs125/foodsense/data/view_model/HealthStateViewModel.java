package com.cs125.foodsense.data.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.cs125.foodsense.data.MyRepository;
import com.cs125.foodsense.data.dao.BodyConstitutionDAO;
import com.cs125.foodsense.data.dao.UserConstitutionDAO;
import com.cs125.foodsense.data.entity.BodyConstitution;
import com.cs125.foodsense.data.entity.UserConstitution;

import java.util.List;

public class HealthStateViewModel extends AndroidViewModel {
    private MyRepository repository;
    private BodyConstitutionDAO bcDao;
    private UserConstitutionDAO ucDao;

    private LiveData<UserConstitution> myConstitution;

    public HealthStateViewModel(@NonNull Application application) {
        super(application);
        repository = new MyRepository(application);
    }

    public void insertIfNotExist(UserConstitution uc) {
        repository.insertUserConstitution(uc);
    }

    public void updateUserHitChanges(UserConstitution uc){
        repository.updateUserHits(uc);
    }

    public void updateConstitution(UserConstitution uc) {
        repository.updateConstitution(uc);
    }

    public UserConstitution getUserConst(String userEmail){
        return repository.getUserConst(userEmail);
    }
/*
    public List<BodyConstitution> getBodyConstitutionLOV(){
        return repository.getConstitutionLOV();
    }
    */

}
