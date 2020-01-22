package com.example.mareu.view.viewModel;

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
import com.example.mareu.manager.MeetingManager;
import com.example.mareu.utility.SingleLiveEvent;
import com.example.mareu.view.helper.RoomFilterEnum;
import com.example.mareu.view.helper.DateFilterType;
import com.example.mareu.view.helper.RoomFilterType;
import com.example.mareu.view.model.MeetingUiModel;
import com.example.mareu.view.helper.SortingFilterType;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static com.example.mareu.view.helper.RoomFilterEnum.ALL_ROOM;
import static com.example.mareu.view.helper.RoomFilterEnum.ROOM_1;
import static com.example.mareu.view.helper.RoomFilterEnum.ROOM_10;
import static com.example.mareu.view.helper.RoomFilterEnum.ROOM_2;
import static com.example.mareu.view.helper.RoomFilterEnum.ROOM_3;
import static com.example.mareu.view.helper.RoomFilterEnum.ROOM_4;
import static com.example.mareu.view.helper.RoomFilterEnum.ROOM_5;
import static com.example.mareu.view.helper.RoomFilterEnum.ROOM_6;
import static com.example.mareu.view.helper.RoomFilterEnum.ROOM_7;
import static com.example.mareu.view.helper.RoomFilterEnum.ROOM_8;
import static com.example.mareu.view.helper.RoomFilterEnum.ROOM_9;

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

    private final DateFilterType mChoiceDateFilterType = new DateFilterType();
    private final MutableLiveData<String> mChoiceDateFilterUiModelLiveData = new MutableLiveData<>();
    private final LiveData<List<Meeting>> mMeetingListLiveData;
    private final MediatorLiveData<List<MeetingUiModel>> mMeetingUiModelsLiveData = new MediatorLiveData<>();
    private final SingleLiveEvent<SortingFilterType> mSortingTypeLiveData = new SingleLiveEvent<>();
    private final SingleLiveEvent<SortingFilterType> mSortingTypeUiModelLiveData = new SingleLiveEvent<>();
    private final MutableLiveData<RoomFilterEnum> mRoomFilterTypeLiveData = new MutableLiveData<>();
    private final SingleLiveEvent<RoomFilterType> mRoomFilterTypeUiModelLiveData = new SingleLiveEvent<>();
    private final MutableLiveData<Integer> mSelectedFilterRoomLiveData = new MutableLiveData<>();
    private final SingleLiveEvent<DateFilterType> mChoiceDateFilterUiModelData = new SingleLiveEvent<>();
    private final SingleLiveEvent<String> mToastTextForChoiceDateFilterLiveData = new SingleLiveEvent<>();
    private int mSelectedSortingTypeIndex = 0;
    private int mSelectedFilterRoomIndex = 0;
    private Resources mResources;

    public MainViewModel(@NonNull MeetingManager meetingManager, Resources resources) {
        mMeetingListLiveData = meetingManager.getMeetingListLiveData();
        wireUpMediator();
        mResources = resources;
    }

    public LiveData<List<MeetingUiModel>> getMeetingUiModelsLiveData() {
        return mMeetingUiModelsLiveData;
    }

    public LiveData<SortingFilterType> getSortingTypeUiModelLiveData() {
        return mSortingTypeUiModelLiveData;
    }

    public LiveData<RoomFilterType> getRoomFilterTypeUiModelLiveData() {
        return mRoomFilterTypeUiModelLiveData;
    }

    public LiveData<DateFilterType> getChoiceDateFilterUiModelLiveData() {
        return mChoiceDateFilterUiModelData;
    }

    public LiveData<String> getToastTextForChoiceDateFilter() {
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

        mMeetingUiModelsLiveData.addSource(mSortingTypeLiveData, new Observer<SortingFilterType>() {
            @Override
            public void onChanged(SortingFilterType sortingFilterType) {
                mMeetingUiModelsLiveData.setValue(
                        combineMeeting(
                                mMeetingListLiveData.getValue(),
                                sortingFilterType,
                                mChoiceDateFilterUiModelLiveData.getValue(),
                                mRoomFilterTypeLiveData.getValue()
                        )
                );
            }
        });

        mMeetingUiModelsLiveData.addSource(mRoomFilterTypeLiveData, new Observer<RoomFilterEnum>() {
            @Override
            public void onChanged(RoomFilterEnum roomFilterEnum) {
                mMeetingUiModelsLiveData.setValue(
                        combineMeeting(
                                mMeetingListLiveData.getValue(),
                                mSortingTypeLiveData.getValue(),
                                mChoiceDateFilterUiModelLiveData.getValue(),
                                roomFilterEnum
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

    @Nullable
    private List<MeetingUiModel> combineMeeting(
            @Nullable List<Meeting> meetingList,
            @Nullable SortingFilterType sortingFilterType,
            @Nullable String dateToFilter,
            @Nullable RoomFilterEnum roomFilterEnum) {

        int index = 0;

        List<MeetingUiModel> meetingUiModelToShow = new ArrayList<>();
        List<MeetingUiModel> meetingUiModelListWithDateAndRoomFilter = new ArrayList<>();
        List<MeetingUiModel> allMeetingListUiModel = new ArrayList<>();

        if (mChoiceDateFilterUiModelLiveData.getValue() == null) {
            dateToFilter = " ";
            mChoiceDateFilterUiModelLiveData.setValue(" ");
        }

        if (mRoomFilterTypeUiModelLiveData.getValue() == null) {
            roomFilterEnum = ALL_ROOM;
        }

        int roomFilterTypeSelected = filterMeetingListWithRoomInCombine(meetingList, roomFilterEnum);

        // SORTING TYPE
        if (sortingMeetingListWithSortingTypeInCombine(meetingList, sortingFilterType)) return null;

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

                    setToastTextForChoiceDateFilter(mChoiceDateFilterType.getToastForDisplayAllMeeting());

                } else {

                    setToastTextForChoiceDateFilter(mChoiceDateFilterType.getToastForValideDate());
                }

            } else if (dateToFilter.length() != 10) {

                mMeetingUiModelsLiveData.setValue(meetingUiModelToShow);

                setToastTextForChoiceDateFilter(mChoiceDateFilterType.getToastForInvalideDate());

            } else {

                meetingUiModelToShow = meetingUiModelListWithValidDateAndRoomFilter;

                mMeetingUiModelsLiveData.setValue(meetingUiModelToShow);
            }

            index++;
        }
        return meetingUiModelToShow;
    }

    private int filterMeetingListWithRoomInCombine(@Nullable List<Meeting> meetingList, @Nullable RoomFilterEnum roomFilterEnum){
        if (meetingList == null) {
            return 0;
        }

        int i = 0;

        switch (roomFilterEnum) {
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

    private boolean sortingMeetingListWithSortingTypeInCombine(@Nullable List<Meeting> meetingList, @Nullable SortingFilterType sortingFilterType) {
        if (meetingList == null) {
            return true;
        }

        if (sortingFilterType == null || sortingFilterType == ROOM_ALPHABETICAL_ASC) {

            Collections.sort(meetingList, ROOM_COMPARATOR_MEETING_ASC);

        } else if (sortingFilterType == ROOM_ALPHABETICAL_DSC) {

            Collections.sort(meetingList, ROOM_COMPARATOR_MEETING_DSC);

        } else if (sortingFilterType == DATE_ASC) {

            Collections.sort(meetingList, DATE_COMPARATOR_ASC);

        } else if (sortingFilterType == DATE_DSC) {

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
    public void setSortingType(String sortChoice, SortingFilterType sortingFilterType) {
        if (Objects.equals(mResources.getString(R.string.room_alphabetical_asc_string), sortChoice)) {
            mSortingTypeLiveData.setValue(ROOM_ALPHABETICAL_ASC);
            mSelectedSortingTypeIndex = 0;
            setValueSortingTypeUiModel(sortingFilterType);
        } else if (Objects.equals(mResources.getString(R.string.room_alphabetical_dsc_string), sortChoice)) {
            mSortingTypeLiveData.setValue(ROOM_ALPHABETICAL_DSC);
            mSelectedSortingTypeIndex = 1;
            setValueSortingTypeUiModel(sortingFilterType);
        } else if (Objects.equals(mResources.getString(R.string.date_asc_string), sortChoice)) {
            mSortingTypeLiveData.setValue(DATE_ASC);
            mSelectedSortingTypeIndex = 2;
            setValueSortingTypeUiModel(sortingFilterType);
        } else if (Objects.equals(mResources.getString(R.string.date_dsc_string), sortChoice)) {
            mSortingTypeLiveData.setValue(DATE_DSC);
            mSelectedSortingTypeIndex = 3;
            setValueSortingTypeUiModel(sortingFilterType);
        }
    }

    private void setValueSortingTypeUiModel(SortingFilterType sortingFilterType) {
        sortingFilterType.setSelectedIndex(mSelectedSortingTypeIndex);
    }

    private void setSortingTypeUiModel() {
        SortingFilterType mSortingFilterType = new SortingFilterType();
        String[] listOfItemSortMenu = new String[4];

        mSortingFilterType.setTitle(mResources.getString(R.string.choose_sorting_title));
        mSortingFilterType.setPositiveButtonText(mResources.getString(R.string.validate_choice));
        mSortingFilterType.setToastChoiceSorting(mResources.getString(R.string.your_sorting_choice_is));

        listOfItemSortMenu[0] = mResources.getString(R.string.room_alphabetical_asc_string);
        listOfItemSortMenu[1] = mResources.getString(R.string.room_alphabetical_dsc_string);
        listOfItemSortMenu[2] = mResources.getString(R.string.date_asc_string);
        listOfItemSortMenu[3] = mResources.getString(R.string.date_dsc_string);

        mSortingFilterType.setNames(listOfItemSortMenu);
        mSortingFilterType.setSelectedIndex(mSelectedSortingTypeIndex);

        mSortingTypeUiModelLiveData.setValue(mSortingFilterType);
    }

    public void displaySortingTypePopup() {

        if (mSortingTypeUiModelLiveData.getValue() == null) {
            setSortingTypeUiModel();
        } else {
            mSortingTypeUiModelLiveData.setValue(getSortingTypeUiModelLiveData().getValue());
        }
    }

    // ROOM FILTER
    public void setRoomFilterType(String filterChoice, RoomFilterType roomFilterType) {
        if (Objects.equals(mResources.getString(R.string.all_room_string), filterChoice)) {
            mRoomFilterTypeLiveData.setValue(ALL_ROOM);
            mSelectedFilterRoomIndex = 0;
            setValueRoomFilterUiModel(roomFilterType);
        } else if (Objects.equals(mResources.getString(R.string.room_1_string), filterChoice)) {
            mRoomFilterTypeLiveData.setValue(ROOM_1);
            mSelectedFilterRoomIndex = 1;
            setValueRoomFilterUiModel(roomFilterType);
        } else if (Objects.equals(mResources.getString(R.string.room_2_string), filterChoice)) {
            mRoomFilterTypeLiveData.setValue(ROOM_2);
            mSelectedFilterRoomIndex = 2;
            setValueRoomFilterUiModel(roomFilterType);
        } else if (Objects.equals(mResources.getString(R.string.room_3_string), filterChoice)) {
            mRoomFilterTypeLiveData.setValue(ROOM_3);
            mSelectedFilterRoomIndex = 3;
            setValueRoomFilterUiModel(roomFilterType);
        } else if (Objects.equals(mResources.getString(R.string.room_4_string), filterChoice)) {
            mRoomFilterTypeLiveData.setValue(ROOM_4);
            mSelectedFilterRoomIndex = 4;
            setValueRoomFilterUiModel(roomFilterType);
        } else if (Objects.equals(mResources.getString(R.string.room_5_string), filterChoice)) {
            mRoomFilterTypeLiveData.setValue(ROOM_5);
            mSelectedFilterRoomIndex = 5;
            setValueRoomFilterUiModel(roomFilterType);
        } else if (Objects.equals(mResources.getString(R.string.room_6_string), filterChoice)) {
            mRoomFilterTypeLiveData.setValue(ROOM_6);
            mSelectedFilterRoomIndex = 6;
            setValueRoomFilterUiModel(roomFilterType);
        } else if (Objects.equals(mResources.getString(R.string.room_7_string), filterChoice)) {
            mRoomFilterTypeLiveData.setValue(ROOM_7);
            mSelectedFilterRoomIndex = 7;
            setValueRoomFilterUiModel(roomFilterType);
        } else if (Objects.equals(mResources.getString(R.string.room_8_string), filterChoice)) {
            mRoomFilterTypeLiveData.setValue(ROOM_8);
            mSelectedFilterRoomIndex = 8;
            setValueRoomFilterUiModel(roomFilterType);
        } else if (Objects.equals(mResources.getString(R.string.room_9_string), filterChoice)) {
            mRoomFilterTypeLiveData.setValue(ROOM_9);
            mSelectedFilterRoomIndex = 9;
            setValueRoomFilterUiModel(roomFilterType);
        } else if (Objects.equals(mResources.getString(R.string.room_10_string), filterChoice)) {
            mRoomFilterTypeLiveData.setValue(ROOM_10);
            mSelectedFilterRoomIndex = 10;
            setValueRoomFilterUiModel(roomFilterType);
        }
    }

    private void setRoomFilterTypeUiModel() {
        RoomFilterType mRoomFilterType = new RoomFilterType();
        String[] listOfItemFilterRoomMenu = new String[11];

        mRoomFilterType.setTitle(mResources.getString(R.string.choose_room_to_filter));
        mRoomFilterType.setPositiveButtonText(mResources.getString(R.string.validate_choice));
        mRoomFilterType.setToastChoiceMeeting(mResources.getString(R.string.your_filter_room_choice_is));

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

        mRoomFilterType.setNames(listOfItemFilterRoomMenu);
        mRoomFilterType.setSelectedIndex(mSelectedFilterRoomIndex);
        mRoomFilterTypeUiModelLiveData.setValue(mRoomFilterType);
    }

    private void setValueRoomFilterUiModel(RoomFilterType roomFilterType) {
        mSelectedFilterRoomLiveData.setValue(mSelectedFilterRoomIndex);
        roomFilterType.setSelectedIndex(mSelectedFilterRoomIndex);
    }

    public void displayFilterRoomPopup() {

        if (mRoomFilterTypeUiModelLiveData.getValue() == null) {
            setRoomFilterTypeUiModel();
        } else {
            mRoomFilterTypeUiModelLiveData.setValue(getRoomFilterTypeUiModelLiveData().getValue());
        }
    }

    // DATE FILTER
    public void compareDateToFilter(String dateForFilter) {
        mChoiceDateFilterUiModelLiveData.setValue(dateForFilter);
    }

    public void displayChoiceDateFilterPopup() {
        mChoiceDateFilterType.setTitle("Choisis la date à filtrer");
        mChoiceDateFilterType.setMessage("Exemple : 2019-12-01");
        mChoiceDateFilterType.setPositiveButtonText("Valider");
        mChoiceDateFilterType.setToastForDisplayAllMeeting("Tu as choisi d'afficher toutes les réunions ");
        mChoiceDateFilterType.setToastForInvalideDate("La date est invalide");
        mChoiceDateFilterType.setToastForValideDate("Tu as choisi d'afficher les réunions de cette date : ");
        mChoiceDateFilterUiModelData.setValue(mChoiceDateFilterType);
    }

    // DELETE MEETING
    public void deleteMeeting(int meetingId) {
        MeetingManager.getInstance().deleteMeeting(meetingId);
    }
}