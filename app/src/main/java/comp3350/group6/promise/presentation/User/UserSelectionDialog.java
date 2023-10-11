package comp3350.group6.promise.presentation.User;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import comp3350.group6.promise.R;
import comp3350.group6.promise.objects.AccountUser;

public class UserSelectionDialog extends BottomSheetDialogFragment {

    protected UserSelectorViewModel model;
    protected ViewModelStoreOwner modelOwner;

    protected CoordinatorLayout layout;
    protected Button submitButton;
    private EditText searchInput;

    protected RecyclerView selectedListRecycler;
    private RecyclerView searchListRecycler;
    protected NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_select, container, false);
    }

    // Called only when fragment is rendered as a dialog

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return dialog;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        navController = NavHostFragment.findNavController(this);

        modelOwner = modelOwner != null ? modelOwner : getActivity();
        model = new ViewModelProvider(modelOwner).get(UserSelectorViewModel.class);

        layout = getActivity().findViewById(R.id.coordinator_layout);
        submitButton = view.findViewById( R.id.sendInviteButton );
        selectedListRecycler = view.findViewById( R.id.user_search_selection_list );

        AccountUserAdapter selectionListAdapter = new AccountUserAdapter(
                getContext(),
                model.getSelectedUsers().getValue(),
                new SelectedUserClickListener(model),
                R.layout.user_list_item_selected
        );

        selectedListRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        selectedListRecycler.setAdapter(selectionListAdapter);

        final Observer<List<AccountUser>> selectedListObserver = new Observer<List<AccountUser>>() {
            @Override
            public void onChanged(@Nullable final List<AccountUser> selectionList) {
                int selectionListVisibility = View.VISIBLE;
                boolean isButtonEnabled = true;
                if(selectionList.isEmpty()) {
                    selectionListVisibility = View.GONE;
                    isButtonEnabled = false;
                }
                selectedListRecycler.setVisibility(selectionListVisibility);
                submitButton.setEnabled(isButtonEnabled);
                selectionListAdapter.notifyDataSetChanged();
            }
        };

        model.getSelectedUsers().observe(getViewLifecycleOwner(), selectedListObserver);

        submitButton.setOnClickListener(button -> {
            boolean submitWasSuccessful = handleSubmit();
            if(submitWasSuccessful) {
                dismiss();
            }
        });

        searchInput = view.findViewById( R.id.user_search_input);
        searchListRecycler = view.findViewById( R.id.user_search_result_list );

        // Setup search list

        AccountUserAdapter searchListAdapter = new AccountUserAdapter(getContext(), model.getSearchResultUsers().getValue(), new SearchedUserClickListener(model));
        searchListRecycler.setLayoutManager( new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        searchListRecycler.setAdapter(searchListAdapter);

        // Setup search input functionality

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                String searchTerm = editable.toString();
                model.searchUsers(searchTerm);
            }
        });

        // Observer to update views when the search results change

        final Observer<List<AccountUser>> searchResultListObserver = new Observer<List<AccountUser>>() {
            @Override
            public void onChanged(@Nullable final List<AccountUser> searchResultList) {
                int searchListVisibility = View.VISIBLE;
                if(searchResultList.isEmpty()) {
                    searchListVisibility = View.GONE;
                }
                searchListRecycler.setVisibility(searchListVisibility);
                searchListAdapter.notifyDataSetChanged();
            }
        };

        // Apply observer to search list LiveData instance

        model.getSearchResultUsers().observe(getViewLifecycleOwner(), searchResultListObserver);

    }

    // Can be overwritten by subclass to extend form functionality, return values indicates success

    protected boolean handleSubmit() {
        return true;
    }

    public void setViewModelOwner(ViewModelStoreOwner modelOwner) {
        this.modelOwner = modelOwner;
    }

    // Can be overwritten by subclass to specify what activity/fragment should own the model state

    private static class SearchedUserClickListener implements AccountUserAdapter.OnUserClickListener {

        private UserSelectorViewModel model;

        public SearchedUserClickListener(UserSelectorViewModel model) {
            this.model = model;
        }

        @Override
        public void onUserClick(int position) {
            model.addUserToSelection(position);
        }

    }

    private static class SelectedUserClickListener implements AccountUserAdapter.OnUserClickListener {

        UserSelectorViewModel model;

        public SelectedUserClickListener(UserSelectorViewModel model) {
            this.model = model;
        }

        @Override
        public void onUserClick(int position) {
            model.removeUserFromSelection(position);
        }

    }

}