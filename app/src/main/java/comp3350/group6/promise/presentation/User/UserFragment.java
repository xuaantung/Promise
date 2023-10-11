package comp3350.group6.promise.presentation.User;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import comp3350.group6.promise.R;
import comp3350.group6.promise.application.CurrentSession;
import comp3350.group6.promise.application.Service;
import comp3350.group6.promise.objects.AccountUser;


public class UserFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    public UserFragment(){
        super(R.layout.fragment_user);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        TextView nameTextView = view.findViewById(R.id.profilePageName);
        TextView aboutMeTextView = view.findViewById(R.id.aboutMeText);
        String displayName = Service.accountUser.getUserByAccount(CurrentSession.getAccount()).getUserName();
        displayName += "'s Profile Page";
        String displayMessage = Service.accountUser.getUserByAccount(CurrentSession.getAccount()).getIntro();

        nameTextView.setText( displayName );
        aboutMeTextView.setText(displayMessage);

    }


}