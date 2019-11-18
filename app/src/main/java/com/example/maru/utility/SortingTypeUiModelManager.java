package com.example.maru.utility;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.maru.service.model.MeetingJava;
import com.example.maru.view.ui.model.SortingTypeUiModel;

import java.util.ArrayList;
import java.util.List;

/**
 * SortingTypeUiModelManager is a Singleton
 */
public class SortingTypeUiModelManager {

    /**
     * Instance unique non préinitialisée
     */
    private static SortingTypeUiModelManager INSTANCE = new SortingTypeUiModelManager();
    private List<String> listSortingType = new ArrayList<>();
    private int selectedIndex = 0;
    private MutableLiveData<List<String>> listSortingTypeLiveData = new MutableLiveData<>();

    /*public static SortingTypeUiModelManager getInstance() {
        return INSTANCE;
    }*/

    /*public SortingTypeUiModelManager(List<SortingTypeUiModel> listSortingType, int selectedIndex) {
        this.listSortingType = listSortingType;
        this.selectedIndex = selectedIndex;
    }*/

    public static SortingTypeUiModelManager getINSTANCE() {
        return INSTANCE;
    }

    public static void setINSTANCE(SortingTypeUiModelManager INSTANCE) {
        SortingTypeUiModelManager.INSTANCE = INSTANCE;
    }

    /**
     * Constructeur privé
     */
    private SortingTypeUiModelManager() {}

    /**
     * Point d'accès pour l'instance unique du singleton
     */
    public static SortingTypeUiModelManager getInstance() {
        if (INSTANCE == null) {
            synchronized (SortingTypeUiModelManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SortingTypeUiModelManager();
                }
            }
        }
        return INSTANCE;
    }

    public void addSortingChoice(String sortingTypeUiModel) {
        listSortingType.add(sortingTypeUiModel);
        listSortingTypeLiveData.postValue(listSortingType);
    }

    public LiveData<List<String>> getSortingTypeLiveData(){
        return listSortingTypeLiveData;
    }

    public List<String> getSortingType(){
        return listSortingType;
    }
}
