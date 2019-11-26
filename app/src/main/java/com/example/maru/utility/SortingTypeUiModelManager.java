package com.example.maru.utility;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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
    private SortingTypeUiModel mSortingTypeUiModel = new SortingTypeUiModel();
    private List<String> mSortingTypeList = new ArrayList<>();
    private int selectedIndex = 0;
    private MutableLiveData<List<String>> mListSortingTypeLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> mIndexChoice = new MutableLiveData<>();

    /**
     * Constructeur privé
     */
    private SortingTypeUiModelManager() {
    }

    public static SortingTypeUiModelManager getINSTANCE() {
        return INSTANCE;
    }

    public static void setINSTANCE(SortingTypeUiModelManager INSTANCE) {
        SortingTypeUiModelManager.INSTANCE = INSTANCE;
    }

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
        mSortingTypeList.add(sortingTypeUiModel);
        mSortingTypeUiModel.setNames(mSortingTypeList);
        mListSortingTypeLiveData.postValue(mSortingTypeList);
    }

    public LiveData<List<String>> getSortingTypeLiveData() {
        return mListSortingTypeLiveData;
    }

    public List<String> getSortingTypeList() {
        return mSortingTypeList;
    }

    public void addSortingTypeList(List<String> list) {
        mSortingTypeList = list;
        mSortingTypeUiModel.setNames(list);
        mListSortingTypeLiveData.postValue(mSortingTypeList);
    }

    public int getChoiceIndex() {
        return selectedIndex;
    }

    public void setChoiceIndex(String checkedItemObject) {
        switch (checkedItemObject) {
            case "Croissant salle":
                selectedIndex = 0;
                break;
            case "Decroissant salle":
                selectedIndex = 1;
                break;
            case "Croissant date":
                selectedIndex = 2;
                break;
            case "Decroissant date":
                selectedIndex = 3;
                break;
        }
        mSortingTypeUiModel.setSelectedIndex(selectedIndex);
        mIndexChoice.postValue((selectedIndex));
    }

    public SortingTypeUiModel getSortingTypeUiModel() {
        return mSortingTypeUiModel;
    }
}