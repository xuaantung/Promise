package comp3350.group6.promise.presentation.User;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import comp3350.group6.promise.application.CurrentSession;
import comp3350.group6.promise.application.Service;
import comp3350.group6.promise.objects.AccountUser;

public class UserSelectorViewModel extends ViewModel {

    protected String searchTerm;
    protected List<AccountUser> lastSearchResults;
    protected final MutableLiveData<List<AccountUser>> searchListLive = new MutableLiveData<>(new ArrayList<>());
    protected final MutableLiveData<List<AccountUser>> selectedListLive = new MutableLiveData<>(new ArrayList<>());
    protected List<Integer> excludedUserIds;

    public LiveData<List<AccountUser>> getSearchResultUsers() {
        return searchListLive;
    }

    public LiveData<List<AccountUser>> getSelectedUsers() {
        return selectedListLive;
    }

    public UserSelectorViewModel() {
        selectedListLive.observeForever(selectedUsers -> {
            if(lastSearchResults != null) {
                filterResults();
            }
        });
    }

    public void searchUsers(String searchTerm) {
        this.searchTerm = searchTerm;
        lastSearchResults = Service.accountUser.search(searchTerm);
        setSearchResults(lastSearchResults);
    }

    // Updates the search results given a list of users
    private void setSearchResults(List<AccountUser> results) {
        List<AccountUser> searchList = searchListLive.getValue();
        searchList.clear();
        searchList.addAll(results);
        searchListLive.setValue(searchList);
        filterResults();
    }

    // Adds a user at a given index in the search results to the selection
    public void addUserToSelection(int searchListIndex) {
        AccountUser selectedUser = searchListLive.getValue().get(searchListIndex);
        List<AccountUser> selectedList = selectedListLive.getValue();
        selectedList.add(selectedUser);
        Log.i("anchor", "Adding user to selection" + selectedUser.toString() + selectedList.toString());
        selectedListLive.setValue(selectedList);
    }

    // Adds a user at a given index in the search results to the selection
    public void removeUserFromSelection(int positionInSelectionList) {
        List<AccountUser> selectedList = selectedListLive.getValue();
        selectedList.remove(positionInSelectionList);
        selectedListLive.setValue(selectedList);
    }

    private void filterResults() {
        List<AccountUser> searchList = searchListLive.getValue();
        List<AccountUser> selectedList = selectedListLive.getValue();
        searchList.clear();
        for(AccountUser user : lastSearchResults) {
            boolean isSelected = selectedList.contains(user);
            boolean isExcluded = excludedUserIds != null && excludedUserIds.contains(user.getUserID());
            if(!isSelected && !isExcluded) {
                searchList.add(user);
            }
        }
        searchListLive.setValue(searchList);
    }

    public void setExcludedUsers(List<Integer> excludedUserIds) {
        this.excludedUserIds = excludedUserIds;
    }

}