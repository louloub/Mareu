package com.example.mareu.view.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.mareu.service.model.Meeting;
import com.example.mareu.utility.MeetingManager;
import com.example.mareu.utility.SingleLiveEvent;
import com.example.mareu.view.ui.model.DateFilterUiModel;
import com.example.mareu.view.ui.model.MeetingUiModel;
import com.example.mareu.view.ui.model.RoomFilterTypeUiModel;
import com.example.mareu.view.ui.model.SortingTypeUiModel;

import org.jetbrains.annotations.NotNull;
import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import static com.example.mareu.view.ui.RoomFilterType.ALL_ROOM;
import static com.example.mareu.view.ui.RoomFilterType.ROOM_1;
import static com.example.mareu.view.ui.RoomFilterType.ROOM_10;
import static com.example.mareu.view.ui.RoomFilterType.ROOM_2;
import static com.example.mareu.view.ui.RoomFilterType.ROOM_3;
import static com.example.mareu.view.ui.RoomFilterType.ROOM_4;
import static com.example.mareu.view.ui.RoomFilterType.ROOM_5;
import static com.example.mareu.view.ui.RoomFilterType.ROOM_6;
import static com.example.mareu.view.ui.RoomFilterType.ROOM_7;
import static com.example.mareu.view.ui.RoomFilterType.ROOM_8;
import static com.example.mareu.view.ui.RoomFilterType.ROOM_9;
import static com.example.mareu.view.ui.SortingType.DATE_ASC;
import static com.example.mareu.view.ui.SortingType.DATE_DSC;
import static com.example.mareu.view.ui.SortingType.ROOM_ALPHABETICAL_ASC;
import static com.example.mareu.view.ui.SortingType.ROOM_ALPHABETICAL_DSC;

public class MainViewModel extends ViewModel {

    private static final Comparator<Meeting> ROOM_COMPARATOR_MEETING_ASC = new Comparator<Meeting>() {
        @Override
        public int compare(Meeting e1, Meeting e2) {
            return (e1.getRoom() - e2.getRoom());
        }
    };
    private static final Comparator<Meeting> ROOM_COMPARATOR_MEETING_DSC = new Comparator<Meeting>() {
        @Override
        public int compare(Meeting e1, Meeting e2) {
            return (e2.getRoom() - e1.getRoom());
        }
    };
    private static final Comparator<Meeting> DATE_COMPARATOR_ASC = new Comparator<Meeting>() {
        @Override
        public int compare(Meeting e1, Meeting e2) {
            return (e1.getDate().compareTo(e2.getDate()));
        }
    };
    private static final Comparator<Meeting> DATE_COMPARATOR_DSC = new Comparator<Meeting>() {
        @Override
        public int compare(Meeting e1, Meeting e2) {
            return (e2.getDate().compareTo(e1.getDate()));
        }
    };

    private static final String ROOM_ALPHABETICAL_ASC_STRING = "Croissant salle";
    private static final String ROOM_ALPHABETICAL_DSC_STRING = "Decroissant salle";
    private static final String DATE_ASC_STRING = "Croissant date";
    private static final String DATE_DSC_STRING = "Decroissant date";

    private static final String ALL_ROOM_STRING = "toutes les salles";
    private static final String ROOM_1_STRING = "salle 1";
    private static final String ROOM_2_STRING = "salle 2";
    private static final String ROOM_3_STRING = "salle 3";
    private static final String ROOM_4_STRING = "salle 4";
    private static final String ROOM_5_STRING = "salle 5";
    private static final String ROOM_6_STRING = "salle 6";
    private static final String ROOM_7_STRING = "salle 7";
    private static final String ROOM_8_STRING = "salle 8";
    private static final String ROOM_9_STRING = "salle 9";
    private static final String ROOM_10_STRING = "salle 10";

    private static final String CHOOSE_SORTING_TITLE = "Choisis le trie que tu souhaites" ;
    private static final String VALIDATE_CHOICE = "Valider" ;
    private static final String YOUR_SORTING_CHOICE_IS = "Tu as choisi : ";

    private static final String CHOOSE_ROOM_TO_FILTER = "Choisis la salle à filtrer";
    private static final String YOUR_FILTER_ROOM_CHOICE_IS = "Tu as choisi d'afficher les réunions : ";

    private final DateFilterUiModel mChoiceDateFilterUiModel = new DateFilterUiModel();
    private final MutableLiveData<String> mChoiceDateFilterUiModelLiveData = new MutableLiveData<>();
    private final LiveData<List<Meeting>> mMeetingListLiveData;
    private final MediatorLiveData<List<MeetingUiModel>> mMeetingUiModelsLiveData = new MediatorLiveData<>();
    private final SingleLiveEvent<SortingType> mSortingTypeLiveData = new SingleLiveEvent<>();
    private final SingleLiveEvent<SortingTypeUiModel> mSortingTypeUiModelLiveData = new SingleLiveEvent<>();
    private final MutableLiveData<RoomFilterType> mRoomFilterTypeLiveData = new MutableLiveData<>();
    private final SingleLiveEvent<RoomFilterTypeUiModel> mRoomFilterTypeUiModelLiveData = new SingleLiveEvent<>();
    private final MutableLiveData<Integer> mSelectedFilterRoomLiveData = new MutableLiveData<>();
    private final SingleLiveEvent<DateFilterUiModel> mChoiceDateFilterUiModelData = new SingleLiveEvent<>();
    private final SingleLiveEvent<String> mToastTextForChoiceDateFilterLiveData = new SingleLiveEvent<>();
    private int mSelectedSortingTypeIndex = 0;
    private int mSelectedFilterRoomIndex = 0;

    public MainViewModel(@NonNull MeetingManager meetingManager) {
        mMeetingListLiveData = meetingManager.getMeetingListLiveData();
        wireUpMediator();
    }

    LiveData<List<MeetingUiModel>> getMeetingUiModelsLiveData() {
        return mMeetingUiModelsLiveData;
    }

    LiveData<SortingTypeUiModel> getSortingTypeUiModelLiveData() {
        return mSortingTypeUiModelLiveData;
    }

    LiveData<RoomFilterTypeUiModel> getRoomFilterTypeUiModelLiveData() {
        return mRoomFilterTypeUiModelLiveData;
    }

    LiveData<DateFilterUiModel> getChoiceDateFilterUiModelLiveData() {
        return mChoiceDateFilterUiModelData;
    }

    LiveData<String> getToastTextForChoiceDateFilter(){
        return mToastTextForChoiceDateFilterLiveData;
    }

    private void wireUpMediator() {

        mMeetingUiModelsLiveData.addSource(mMeetingListLiveData, new Observer<List<Meeting>>() {
            @Override
            public void onChanged(List<Meeting> meetings) {
                mMeetingUiModelsLiveData.setValue(
                        combineMeeting(
                                meetings,
                                mSortingTypeLiveData.getValue(),
                                mSelectedFilterRoomLiveData.getValue(),
                                mChoiceDateFilterUiModelLiveData.getValue()
                        )
                );
            }
        });

        mMeetingUiModelsLiveData.addSource(mSortingTypeLiveData, new Observer<SortingType>() {
            @Override
            public void onChanged(SortingType sortingType) {
                mMeetingUiModelsLiveData.setValue(
                        combineMeeting(
                                mMeetingListLiveData.getValue(),
                                sortingType,
                                mSelectedFilterRoomLiveData.getValue(),
                                mChoiceDateFilterUiModelLiveData.getValue()
                        )
                );
            }
        });

        mMeetingUiModelsLiveData.addSource(mSelectedFilterRoomLiveData, new Observer<Integer>() {
            @Override
            public void onChanged(Integer selectedMeetingRoomNumber) {
                mMeetingUiModelsLiveData.setValue(
                        combineMeeting(
                                mMeetingListLiveData.getValue(),
                                mSortingTypeLiveData.getValue(),
                                selectedMeetingRoomNumber,
                                mChoiceDateFilterUiModelLiveData.getValue()
                        )
                );
            }
        });

        mMeetingUiModelsLiveData.addSource(mRoomFilterTypeLiveData, new Observer<RoomFilterType>() {
            @Override
            public void onChanged(RoomFilterType roomFilterType) {
                mMeetingUiModelsLiveData.setValue(
                        combineMeeting(
                                mMeetingListLiveData.getValue(),
                                mSortingTypeLiveData.getValue(),
                                mSelectedFilterRoomLiveData.getValue(),
                                mChoiceDateFilterUiModelLiveData.getValue()
                        )
                );
            }
        });

        mMeetingUiModelsLiveData.addSource(mChoiceDateFilterUiModelLiveData, new Observer<String>() {
            @Override
            public void onChanged(String dateToFilter) {
                mMeetingUiModelsLiveData.setValue(
                        combineMeeting(
                                mMeetingListLiveData.getValue(),
                                mSortingTypeLiveData.getValue(),
                                mSelectedFilterRoomLiveData.getValue(),
                                dateToFilter
                        )
                );
            }
        });
    }

    @Nullable
    private List<MeetingUiModel> combineMeeting(
            @Nullable List<Meeting> meetingList,
            @Nullable SortingType sortingType,
            @Nullable Integer selectedMeetingRoomNumber,
            @Nullable String dateToFilter) {

        int index = 0;

        List<MeetingUiModel> meetingUiModelToShow = new ArrayList<>();
        List<MeetingUiModel> meetingUiModelListWithValidDateFilter = new ArrayList<>();
        List<MeetingUiModel> allMeetingListUiModel = new ArrayList<>();

        // TODO 14/01/2020 : intégrer le filtre par room
        if (mChoiceDateFilterUiModelLiveData.getValue() == null) {
            dateToFilter = " ";
            mChoiceDateFilterUiModelLiveData.setValue(dateToFilter);
        }

        if (mSelectedFilterRoomLiveData.getValue() == null) {
            selectedMeetingRoomNumber = 0;
            mSelectedFilterRoomLiveData.setValue(selectedMeetingRoomNumber);
        }

        // SORTING TYPE
        if (sortingMeetingListWithSortingTypeInCombine(meetingList, sortingType)) return null;

        // DATE FILTER
        meetingUiModelToShow = filterMeetingListWithDateFilterInCombine(
                meetingList,
                dateToFilter,
                index,
                meetingUiModelToShow,
                meetingUiModelListWithValidDateFilter,
                allMeetingListUiModel,
                selectedMeetingRoomNumber);

        return meetingUiModelToShow;
    }

    private List<MeetingUiModel> filterMeetingListWithDateFilterInCombine(
            @NotNull List<Meeting> meetingList,
            @NotNull String dateToFilter,
            int index,
            List<MeetingUiModel> meetingUiModelToShow,
            List<MeetingUiModel> meetingUiModelListWithValidDateFilter,
            List<MeetingUiModel> allMeetingListUiModel,
            Integer selectedMeetingRoomNumber) {

        for (Meeting meeting : meetingList) {

            createMeetingUiModel(meeting,allMeetingListUiModel);

            // TODO 14/01/2020 : tester ici avec selectedMeetingRoomNumber
            if (meetingList.get(index).getDate().toString().equals(dateToFilter)) {

                createMeetingUiModel(meeting,meetingUiModelListWithValidDateFilter);

                meetingUiModelToShow = meetingUiModelListWithValidDateFilter;

                mMeetingUiModelsLiveData.setValue(meetingUiModelListWithValidDateFilter);

                setToastTextForChoiceDateFilter(mChoiceDateFilterUiModel.getToastForValideDate());

            } else {

                if (dateToFilter.equals(" ")) {

                    meetingUiModelToShow = allMeetingListUiModel;

                    mMeetingUiModelsLiveData.setValue(meetingUiModelToShow);

                    setToastTextForChoiceDateFilter(mChoiceDateFilterUiModel.getToastForDisplayAllMeeting());
                } else if (dateToFilter.length() != 10) {

                    mMeetingUiModelsLiveData.setValue(meetingUiModelToShow);

                    setToastTextForChoiceDateFilter(mChoiceDateFilterUiModel.getToastForInvalideDate());
                } else {

                    meetingUiModelToShow = meetingUiModelListWithValidDateFilter;

                    mMeetingUiModelsLiveData.setValue(meetingUiModelToShow);
                }
            }
            index++;
        }
        return meetingUiModelToShow;
    }

    private boolean sortingMeetingListWithSortingTypeInCombine(@Nullable List<Meeting> meetingList, @Nullable SortingType sortingType) {
        if (meetingList == null) {
            return true;
        }

        if (sortingType == null || sortingType == ROOM_ALPHABETICAL_ASC) {

            Collections.sort(meetingList, ROOM_COMPARATOR_MEETING_ASC);

        } else if (sortingType == ROOM_ALPHABETICAL_DSC) {

            Collections.sort(meetingList, ROOM_COMPARATOR_MEETING_DSC);

        } else if (sortingType == DATE_ASC) {

            Collections.sort(meetingList, DATE_COMPARATOR_ASC);

        } else if (sortingType == DATE_DSC) {

            Collections.sort(meetingList, DATE_COMPARATOR_DSC);
        }
        return false;
    }

    private String getActualDateStringForFilterDateWhenCreateFirstMeeting() {

        String yearsInStringFormat = String.format(Locale.FRANCE, "%02d", LocalDate.now().getYear());
        String dayInStringFormat = String.format(Locale.FRANCE, "%02d", LocalDate.now().getDayOfMonth());
        String monthInStringFormat = String.format(Locale.FRANCE, "%02d", LocalDate.now().getMonthValue());

        return yearsInStringFormat + "-" + monthInStringFormat + "-" + dayInStringFormat;
    }

    @NotNull
    private MeetingUiModel createMeetingUiModel(Meeting meeting, List<MeetingUiModel> result) {
        StringBuilder stringBuilder = new StringBuilder();

        List<String> listOfEmailOfParticipant = meeting.getListOfEmailOfParticipant();

        for (int i = 0; i < listOfEmailOfParticipant.size(); i++) {
            String participant = listOfEmailOfParticipant.get(i);
            stringBuilder.append(participant);
            if (i + 1 < listOfEmailOfParticipant.size()) {
                stringBuilder.append(", ");
            }
        }

        MeetingUiModel meetingUiModel = new MeetingUiModel(
                meeting.getId(),
                meeting.getDate().toString(),
                meeting.getHour(),
                Integer.toString(meeting.getRoom()),
                meeting.getSubject(),
                stringBuilder.toString());

        result.add(meetingUiModel);
        return meetingUiModel;
    }

    // SORTING TYPE
    void setSortingType(String sortChoice, SortingTypeUiModel sortingTypeUiModel) {
        switch (sortChoice) {
            case ROOM_ALPHABETICAL_ASC_STRING:
                mSortingTypeLiveData.setValue(ROOM_ALPHABETICAL_ASC);
                mSelectedSortingTypeIndex = 0;
                setValueSortingTypeUiModel(sortingTypeUiModel);
                break;
            case ROOM_ALPHABETICAL_DSC_STRING:
                mSortingTypeLiveData.setValue(ROOM_ALPHABETICAL_DSC);
                mSelectedSortingTypeIndex = 1;
                setValueSortingTypeUiModel(sortingTypeUiModel);
                break;
            case DATE_ASC_STRING:
                mSortingTypeLiveData.setValue(DATE_ASC);
                mSelectedSortingTypeIndex = 2;
                setValueSortingTypeUiModel(sortingTypeUiModel);
                break;
            case DATE_DSC_STRING:
                mSortingTypeLiveData.setValue(DATE_DSC);
                mSelectedSortingTypeIndex = 3;
                setValueSortingTypeUiModel(sortingTypeUiModel);
                break;
        }
    }

    private void setValueSortingTypeUiModel(SortingTypeUiModel sortingTypeUiModel) {
        sortingTypeUiModel.setSelectedIndex(mSelectedSortingTypeIndex);
    }

    private void setSortingTypeUiModel() {
        SortingTypeUiModel mSortingTypeUiModel = new SortingTypeUiModel();
        String[] listOfItemSortMenu = new String[4];

        mSortingTypeUiModel.setTitle(CHOOSE_SORTING_TITLE);
        mSortingTypeUiModel.setPositiveButtonText(VALIDATE_CHOICE);
        mSortingTypeUiModel.setToastChoiceSorting(YOUR_SORTING_CHOICE_IS);

        listOfItemSortMenu[0] = ROOM_ALPHABETICAL_ASC_STRING;
        listOfItemSortMenu[1] = ROOM_ALPHABETICAL_DSC_STRING;
        listOfItemSortMenu[2] = DATE_ASC_STRING;
        listOfItemSortMenu[3] = DATE_DSC_STRING;

        mSortingTypeUiModel.setNames(listOfItemSortMenu);
        mSortingTypeUiModel.setSelectedIndex(mSelectedSortingTypeIndex);

        mSortingTypeUiModelLiveData.setValue(mSortingTypeUiModel);
    }

    void displaySortingTypePopup() {

        if (mSortingTypeUiModelLiveData.getValue()==null) {
            setSortingTypeUiModel();
        } else {
            mSortingTypeUiModelLiveData.setValue(getSortingTypeUiModelLiveData().getValue());
        }
    }

    // ROOM FILTER
    void setRoomFilterType(String filterChoice, RoomFilterTypeUiModel roomFilterTypeUiModel) {
        switch (filterChoice) {
            case ALL_ROOM_STRING:
                mRoomFilterTypeLiveData.setValue(ALL_ROOM);
                mSelectedFilterRoomIndex = 0;
                setValueFilterUiModel(roomFilterTypeUiModel);
                break;
            case ROOM_1_STRING:
                mRoomFilterTypeLiveData.setValue(ROOM_1);
                mSelectedFilterRoomIndex = 1;
                setValueFilterUiModel(roomFilterTypeUiModel);
                break;
            case ROOM_2_STRING:
                mRoomFilterTypeLiveData.setValue(ROOM_2);
                mSelectedFilterRoomIndex = 2;
                setValueFilterUiModel(roomFilterTypeUiModel);
                break;
            case ROOM_3_STRING:
                mRoomFilterTypeLiveData.setValue(ROOM_3);
                mSelectedFilterRoomIndex = 3;
                setValueFilterUiModel(roomFilterTypeUiModel);
                break;
            case ROOM_4_STRING:
                mRoomFilterTypeLiveData.setValue(ROOM_4);
                mSelectedFilterRoomIndex = 4;
                setValueFilterUiModel(roomFilterTypeUiModel);
                break;
            case ROOM_5_STRING:
                mRoomFilterTypeLiveData.setValue(ROOM_5);
                mSelectedFilterRoomIndex = 5;
                setValueFilterUiModel(roomFilterTypeUiModel);
                break;
            case ROOM_6_STRING:
                mRoomFilterTypeLiveData.setValue(ROOM_6);
                mSelectedFilterRoomIndex = 6;
                setValueFilterUiModel(roomFilterTypeUiModel);
                break;
            case ROOM_7_STRING:
                mRoomFilterTypeLiveData.setValue(ROOM_7);
                mSelectedFilterRoomIndex = 7;
                setValueFilterUiModel(roomFilterTypeUiModel);
                break;
            case ROOM_8_STRING:
                mRoomFilterTypeLiveData.setValue(ROOM_8);
                mSelectedFilterRoomIndex = 8;
                setValueFilterUiModel(roomFilterTypeUiModel);
                break;
            case ROOM_9_STRING:
                mRoomFilterTypeLiveData.setValue(ROOM_9);
                mSelectedFilterRoomIndex = 9;
                setValueFilterUiModel(roomFilterTypeUiModel);
                break;
            case ROOM_10_STRING:
                mRoomFilterTypeLiveData.setValue(ROOM_10);
                mSelectedFilterRoomIndex = 10;
                setValueFilterUiModel(roomFilterTypeUiModel);
                break;
        }
    }

    private void setRoomFilterTypeUiModel() {
        RoomFilterTypeUiModel mRoomFilterTypeUiModel = new RoomFilterTypeUiModel();
        String[] listOfItemFilterRoomMenu = new String[11];

        mRoomFilterTypeUiModel.setTitle(CHOOSE_ROOM_TO_FILTER);
        mRoomFilterTypeUiModel.setPositiveButtonText(VALIDATE_CHOICE);
        mRoomFilterTypeUiModel.setToastChoiceMeeting(YOUR_FILTER_ROOM_CHOICE_IS);

        listOfItemFilterRoomMenu[0] = ALL_ROOM_STRING;
        listOfItemFilterRoomMenu[1] = ROOM_1_STRING;
        listOfItemFilterRoomMenu[2] = ROOM_2_STRING;
        listOfItemFilterRoomMenu[3] = ROOM_3_STRING;
        listOfItemFilterRoomMenu[4] = ROOM_4_STRING;
        listOfItemFilterRoomMenu[5] = ROOM_5_STRING;
        listOfItemFilterRoomMenu[6] = ROOM_6_STRING;
        listOfItemFilterRoomMenu[7] = ROOM_7_STRING;
        listOfItemFilterRoomMenu[8] = ROOM_8_STRING;
        listOfItemFilterRoomMenu[9] = ROOM_9_STRING;
        listOfItemFilterRoomMenu[10] = ROOM_10_STRING;

        mRoomFilterTypeUiModel.setNames(listOfItemFilterRoomMenu);
        mRoomFilterTypeUiModel.setSelectedIndex(mSelectedFilterRoomIndex);
        mRoomFilterTypeUiModelLiveData.setValue(mRoomFilterTypeUiModel);
    }

    private void setValueFilterUiModel(RoomFilterTypeUiModel roomFilterTypeUiModel) {
        mSelectedFilterRoomLiveData.setValue(mSelectedFilterRoomIndex);
        roomFilterTypeUiModel.setSelectedIndex(mSelectedFilterRoomIndex);
    }

    void displayFilterRoomPopup() {

        if (mRoomFilterTypeUiModelLiveData.getValue()==null){
            setRoomFilterTypeUiModel();
        } else {
            mRoomFilterTypeUiModelLiveData.setValue(getRoomFilterTypeUiModelLiveData().getValue());
        }
    }

    // DATE FILTER
    void compareDateToFilter(String dateForFilter) {
        mChoiceDateFilterUiModelLiveData.setValue(dateForFilter);
    }

    void setToastTextForChoiceDateFilter(String toastText){
        mToastTextForChoiceDateFilterLiveData.setValue(toastText);
    }

    void displayChoiceDateFilterPopup() {
        mChoiceDateFilterUiModel.setTitle("Choisis la date à filtrer");
        mChoiceDateFilterUiModel.setMessage("Exemple : 2019-12-01");
        mChoiceDateFilterUiModel.setPositiveButtonText("Valider");
        mChoiceDateFilterUiModel.setToastForDisplayAllMeeting("Tu as choisi d'afficher toutes les réunions ");
        mChoiceDateFilterUiModel.setToastForInvalideDate("La date est invalide : ");
        mChoiceDateFilterUiModel.setToastForValideDate("Tu as choisi d'afficher les réunions de cette date : ");
        mChoiceDateFilterUiModelData.setValue(mChoiceDateFilterUiModel);
    }

    // DELETE MEETING
    void deleteMeeting(int meetingId) {
        MeetingManager.getInstance().deleteMeeting(meetingId);
    }
}