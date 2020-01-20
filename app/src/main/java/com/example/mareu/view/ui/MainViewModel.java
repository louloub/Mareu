package com.example.mareu.view.ui;

import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.mareu.R;
import com.example.mareu.service.model.Meeting;
import com.example.mareu.utility.MeetingManager;
import com.example.mareu.utility.SingleLiveEvent;
import com.example.mareu.view.ui.model.DateFilterUiModel;
import com.example.mareu.view.ui.model.MeetingUiModel;
import com.example.mareu.view.ui.model.RoomFilterTypeUiModel;
import com.example.mareu.view.ui.model.SortingTypeUiModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
    private final Resources mResources;

    public MainViewModel(@NonNull MeetingManager meetingManager, Resources resources) {
        mMeetingListLiveData = meetingManager.getMeetingListLiveData();
        wireUpMediator();
        mResources = resources;
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

    LiveData<String> getToastTextForChoiceDateFilter() {
        return mToastTextForChoiceDateFilterLiveData;
    }

    void setToastTextForChoiceDateFilter(String toastText) {
        mToastTextForChoiceDateFilterLiveData.setValue(toastText);
    }

    private void wireUpMediator() {

        mMeetingUiModelsLiveData.addSource(mMeetingListLiveData, new Observer<List<Meeting>>() {
            @Override
            public void onChanged(List<Meeting> meetings) {
                mMeetingUiModelsLiveData.setValue(
                        combineMeeting(
                                meetings,
                                mSortingTypeLiveData.getValue(),
                                mChoiceDateFilterUiModelLiveData.getValue(),
                                mRoomFilterTypeLiveData.getValue()
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
                                mChoiceDateFilterUiModelLiveData.getValue(),
                                mRoomFilterTypeLiveData.getValue()
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
                                mChoiceDateFilterUiModelLiveData.getValue(),
                                roomFilterType
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
                                dateToFilter,
                                mRoomFilterTypeLiveData.getValue()
                        )
                );
            }
        });
    }


    // TODO : use "roomFilterType"
    @Nullable
    private List<MeetingUiModel> combineMeeting(
            @Nullable List<Meeting> meetingList,
            @Nullable SortingType sortingType,
            @Nullable String dateToFilter,
            @Nullable RoomFilterType roomFilterType) {

        int index = 0;

        List<MeetingUiModel> meetingUiModelToShow = new ArrayList<>();
        List<MeetingUiModel> meetingUiModelListWithDateAndRoomFilter = new ArrayList<>();
        List<MeetingUiModel> allMeetingListUiModel = new ArrayList<>();

        if (mChoiceDateFilterUiModelLiveData.getValue() == null) {
            dateToFilter = " ";
            mChoiceDateFilterUiModelLiveData.setValue(" ");
        }

        if (mRoomFilterTypeUiModelLiveData.getValue() == null) {
            roomFilterType = ALL_ROOM;
        }

        int roomFilterTypeSelected = filterMeetingListWithRoomInCombine(meetingList,roomFilterType);

        // SORTING TYPE
        if (sortingMeetingListWithSortingTypeInCombine(meetingList, sortingType)) return null;

        // DATE & ROOM FILTER
        meetingUiModelToShow = filterMeetingListWithDateAndRoomFilterInCombine(
                meetingList,
                dateToFilter,
                index,
                meetingUiModelToShow,
                meetingUiModelListWithDateAndRoomFilter,
                allMeetingListUiModel,
                roomFilterTypeSelected);

        return meetingUiModelToShow;
    }

    private List<MeetingUiModel> filterMeetingListWithDateAndRoomFilterInCombine(
            @NotNull List<Meeting> meetingList,
            @NotNull String dateToFilter,
            int index,
            List<MeetingUiModel> meetingUiModelToShow,
            List<MeetingUiModel> meetingUiModelListWithValidDateAndRoomFilter,
            List<MeetingUiModel> allMeetingListUiModel,
            @NonNull int roomFilterTypeSelected) {

        for (Meeting meeting : meetingList) {

            MeetingUiModel meetingUiModelForAllMeetingListUiModel = createMeetingUiModel(meeting);

            allMeetingListUiModel.add(meetingUiModelForAllMeetingListUiModel);

            // Get the current meeting and check if it matches the filters
            Meeting curentMeeting = meetingList.get(index);

            // Check room filter
            boolean shouldCheckRoom = roomFilterTypeSelected != 0;
            boolean isRoomValid = true;
            if (curentMeeting.getRoom() != roomFilterTypeSelected && shouldCheckRoom)
            {
                isRoomValid = false;
            }

            // Check date filter
            boolean shouldCheckDate = !dateToFilter.equals(" ");
            boolean isDateValid = true;
            if (!curentMeeting.getDate().toString().equals(dateToFilter) && shouldCheckDate) {
                isDateValid = false;
            }

            if (isDateValid && isRoomValid)
            {
                MeetingUiModel meetingUiModelForListWithValidDateAndRoomFilter = createMeetingUiModel(meeting);

                meetingUiModelListWithValidDateAndRoomFilter.add(meetingUiModelForListWithValidDateAndRoomFilter);

                meetingUiModelToShow = meetingUiModelListWithValidDateAndRoomFilter;

                mMeetingUiModelsLiveData.setValue(meetingUiModelListWithValidDateAndRoomFilter);

                if (dateToFilter.equals(" ")) {

                    setToastTextForChoiceDateFilter(mChoiceDateFilterUiModel.getToastForDisplayAllMeeting());

                } else {

                    setToastTextForChoiceDateFilter(mChoiceDateFilterUiModel.getToastForValideDate());
                }

            } else if (dateToFilter.length() != 10) {

                mMeetingUiModelsLiveData.setValue(meetingUiModelToShow);

                setToastTextForChoiceDateFilter(mChoiceDateFilterUiModel.getToastForInvalideDate());

            } else {

                meetingUiModelToShow = meetingUiModelListWithValidDateAndRoomFilter;

                mMeetingUiModelsLiveData.setValue(meetingUiModelToShow);
            }

            index++;
        }
        return meetingUiModelToShow;
    }

    private int filterMeetingListWithRoomInCombine(@Nullable List<Meeting> meetingList, @Nullable RoomFilterType roomFilterType){
        if (meetingList == null) {
            return 0;
        }

        int i = 0;

        switch (roomFilterType) {
            case ALL_ROOM:
                i= 0;
                break;
            case ROOM_1:
                i = 1;
                break;
            case ROOM_2:
                i = 2;
                break;
            case ROOM_3:
                i = 3;
                break;
            case ROOM_4:
                i = 4;
                break;
            case ROOM_5:
                i = 5;
                break;
            case ROOM_6:
                i = 6;
                break;
            case ROOM_7:
                i = 7;
                break;
            case ROOM_8:
                i = 8;
                break;
            case ROOM_9:
                i = 9;
                break;
            case ROOM_10:
                i = 10;
                break;
        }

        return i;
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

    @NotNull
    private MeetingUiModel createMeetingUiModel(Meeting meeting) {
        StringBuilder stringBuilder = new StringBuilder();

        List<String> listOfEmailOfParticipant = meeting.getListOfEmailOfParticipant();

        for (int i = 0; i < listOfEmailOfParticipant.size(); i++) {
            String participant = listOfEmailOfParticipant.get(i);
            stringBuilder.append(participant);
            if (i + 1 < listOfEmailOfParticipant.size()) {
                stringBuilder.append(", ");
            }
        }

        return new MeetingUiModel(
                meeting.getId(),
                meeting.getDate().toString(),
                meeting.getHour(),
                Integer.toString(meeting.getRoom()),
                meeting.getSubject(),
                stringBuilder.toString());
    }

    // SORTING TYPE
    void setSortingType(String sortChoice, SortingTypeUiModel sortingTypeUiModel) {

        if (Objects.equals(mResources.getString(R.string.room_alphabetical_asc_string), sortChoice)) {
            mSortingTypeLiveData.setValue(ROOM_ALPHABETICAL_ASC);
            mSelectedSortingTypeIndex = 0;
            setValueSortingTypeUiModel(sortingTypeUiModel);

        } else if (Objects.equals(mResources.getString(R.string.room_alphabetical_dsc_string), sortChoice)) {
            mSortingTypeLiveData.setValue(ROOM_ALPHABETICAL_DSC);
            mSelectedSortingTypeIndex = 1;
            setValueSortingTypeUiModel(sortingTypeUiModel);

        } else if (Objects.equals(mResources.getString(R.string.date_asc_string), sortChoice)) {
            mSortingTypeLiveData.setValue(DATE_ASC);
            mSelectedSortingTypeIndex = 2;
            setValueSortingTypeUiModel(sortingTypeUiModel);

        } else if (Objects.equals(mResources.getString(R.string.date_dsc_string), sortChoice)) {
            mSortingTypeLiveData.setValue(DATE_DSC);
            mSelectedSortingTypeIndex = 3;
            setValueSortingTypeUiModel(sortingTypeUiModel);

        }
    }

    private void setValueSortingTypeUiModel(SortingTypeUiModel sortingTypeUiModel) {
        sortingTypeUiModel.setSelectedIndex(mSelectedSortingTypeIndex);
    }

    private void setSortingTypeUiModel() {
        SortingTypeUiModel mSortingTypeUiModel = new SortingTypeUiModel();
        String[] listOfItemSortMenu = new String[4];

        mSortingTypeUiModel.setTitle(mResources.getString(R.string.choose_sorting_title));
        mSortingTypeUiModel.setPositiveButtonText(mResources.getString(R.string.validate_choice));
        mSortingTypeUiModel.setToastChoiceSorting(mResources.getString(R.string.your_sorting_choice_is));

        listOfItemSortMenu[0] = mResources.getString(R.string.room_alphabetical_asc_string);
        listOfItemSortMenu[1] = mResources.getString(R.string.room_alphabetical_dsc_string);
        listOfItemSortMenu[2] = mResources.getString(R.string.date_asc_string);
        listOfItemSortMenu[3] = mResources.getString(R.string.date_dsc_string);

        mSortingTypeUiModel.setNames(listOfItemSortMenu);
        mSortingTypeUiModel.setSelectedIndex(mSelectedSortingTypeIndex);

        mSortingTypeUiModelLiveData.setValue(mSortingTypeUiModel);
    }

    void displaySortingTypePopup() {

        if (mSortingTypeUiModelLiveData.getValue() == null) {
            setSortingTypeUiModel();
        } else {
            mSortingTypeUiModelLiveData.setValue(getSortingTypeUiModelLiveData().getValue());
        }
    }

    // ROOM FILTER
    void setRoomFilterType(String filterChoice, RoomFilterTypeUiModel roomFilterTypeUiModel) {
        if (mResources.getString(R.string.all_room_string).equals(filterChoice)) {
            mRoomFilterTypeLiveData.setValue(ALL_ROOM);
            mSelectedFilterRoomIndex = 0;
            setValueRoomFilterUiModel(roomFilterTypeUiModel);
        } else if (mResources.getString(R.string.room_1_string).equals(filterChoice)) {
            mRoomFilterTypeLiveData.setValue(ROOM_1);
            mSelectedFilterRoomIndex = 1;
            setValueRoomFilterUiModel(roomFilterTypeUiModel);
        } else if (mResources.getString(R.string.room_2_string).equals(filterChoice)) {
            mRoomFilterTypeLiveData.setValue(ROOM_2);
            mSelectedFilterRoomIndex = 2;
            setValueRoomFilterUiModel(roomFilterTypeUiModel);
        } else if (mResources.getString(R.string.room_3_string).equals(filterChoice)) {
            mRoomFilterTypeLiveData.setValue(ROOM_3);
            mSelectedFilterRoomIndex = 3;
            setValueRoomFilterUiModel(roomFilterTypeUiModel);
        } else if (mResources.getString(R.string.room_4_string).equals(filterChoice)) {
            mRoomFilterTypeLiveData.setValue(ROOM_4);
            mSelectedFilterRoomIndex = 4;
            setValueRoomFilterUiModel(roomFilterTypeUiModel);
        } else if (mResources.getString(R.string.room_5_string).equals(filterChoice)) {
            mRoomFilterTypeLiveData.setValue(ROOM_5);
            mSelectedFilterRoomIndex = 5;
            setValueRoomFilterUiModel(roomFilterTypeUiModel);
        } else if (mResources.getString(R.string.room_6_string).equals(filterChoice)) {
            mRoomFilterTypeLiveData.setValue(ROOM_6);
            mSelectedFilterRoomIndex = 6;
            setValueRoomFilterUiModel(roomFilterTypeUiModel);
        } else if (mResources.getString(R.string.room_7_string).equals(filterChoice)) {
            mRoomFilterTypeLiveData.setValue(ROOM_7);
            mSelectedFilterRoomIndex = 7;
            setValueRoomFilterUiModel(roomFilterTypeUiModel);
        } else if (mResources.getString(R.string.room_8_string).equals(filterChoice)) {
            mRoomFilterTypeLiveData.setValue(ROOM_8);
            mSelectedFilterRoomIndex = 8;
            setValueRoomFilterUiModel(roomFilterTypeUiModel);
        } else if (mResources.getString(R.string.room_9_string).equals(filterChoice)) {
            mRoomFilterTypeLiveData.setValue(ROOM_9);
            mSelectedFilterRoomIndex = 9;
            setValueRoomFilterUiModel(roomFilterTypeUiModel);
        } else if (mResources.getString(R.string.room_10_string).equals(filterChoice)) {
            mRoomFilterTypeLiveData.setValue(ROOM_10);
            mSelectedFilterRoomIndex = 10;
            setValueRoomFilterUiModel(roomFilterTypeUiModel);
        }
    }

    private void setRoomFilterTypeUiModel() {
        RoomFilterTypeUiModel mRoomFilterTypeUiModel = new RoomFilterTypeUiModel();
        String[] listOfItemFilterRoomMenu = new String[11];

        mRoomFilterTypeUiModel.setTitle(mResources.getString(R.string.choose_room_to_filter));
        mRoomFilterTypeUiModel.setPositiveButtonText(mResources.getString(R.string.validate_choice));
        mRoomFilterTypeUiModel.setToastChoiceMeeting(mResources.getString(R.string.your_filter_room_choice_is));

        listOfItemFilterRoomMenu[0] = mResources.getString(R.string.all_room_string);
        listOfItemFilterRoomMenu[1] = mResources.getString(R.string.room_1_string);
        listOfItemFilterRoomMenu[2] = mResources.getString(R.string.room_2_string);
        listOfItemFilterRoomMenu[3] = mResources.getString(R.string.room_3_string);
        listOfItemFilterRoomMenu[4] = mResources.getString(R.string.room_4_string);
        listOfItemFilterRoomMenu[5] = mResources.getString(R.string.room_5_string);
        listOfItemFilterRoomMenu[6] = mResources.getString(R.string.room_6_string);
        listOfItemFilterRoomMenu[7] = mResources.getString(R.string.room_7_string);
        listOfItemFilterRoomMenu[8] = mResources.getString(R.string.room_8_string);
        listOfItemFilterRoomMenu[9] = mResources.getString(R.string.room_9_string);
        listOfItemFilterRoomMenu[10] = mResources.getString(R.string.room_10_string);

        mRoomFilterTypeUiModel.setNames(listOfItemFilterRoomMenu);
        mRoomFilterTypeUiModel.setSelectedIndex(mSelectedFilterRoomIndex);
        mRoomFilterTypeUiModelLiveData.setValue(mRoomFilterTypeUiModel);
    }

    private void setValueRoomFilterUiModel(RoomFilterTypeUiModel roomFilterTypeUiModel) {
        mSelectedFilterRoomLiveData.setValue(mSelectedFilterRoomIndex);
        roomFilterTypeUiModel.setSelectedIndex(mSelectedFilterRoomIndex);
    }

    void displayFilterRoomPopup() {

        if (mRoomFilterTypeUiModelLiveData.getValue() == null) {
            setRoomFilterTypeUiModel();
        } else {
            mRoomFilterTypeUiModelLiveData.setValue(getRoomFilterTypeUiModelLiveData().getValue());
        }
    }

    // DATE FILTER
    void compareDateToFilter(String dateForFilter) {
        mChoiceDateFilterUiModelLiveData.setValue(dateForFilter);
    }

    void displayChoiceDateFilterPopup() {
        mChoiceDateFilterUiModel.setTitle("Choisis la date à filtrer");
        mChoiceDateFilterUiModel.setMessage("Exemple : 2019-12-01");
        mChoiceDateFilterUiModel.setPositiveButtonText("Valider");
        mChoiceDateFilterUiModel.setToastForDisplayAllMeeting("Tu as choisi d'afficher toutes les réunions ");
        mChoiceDateFilterUiModel.setToastForInvalideDate("La date est invalide");
        mChoiceDateFilterUiModel.setToastForValideDate("Tu as choisi d'afficher les réunions de cette date : ");
        mChoiceDateFilterUiModelData.setValue(mChoiceDateFilterUiModel);
    }

    // DELETE MEETING
    void deleteMeeting(int meetingId) {
        MeetingManager.getInstance().deleteMeeting(meetingId);
    }
}