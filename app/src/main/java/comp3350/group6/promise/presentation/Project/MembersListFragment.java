package comp3350.group6.promise.presentation.Project;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import java.util.List;

import comp3350.group6.promise.R;
import comp3350.group6.promise.application.CurrentSession;
import comp3350.group6.promise.application.Service;
import comp3350.group6.promise.objects.Access;
import comp3350.group6.promise.objects.Project;
import comp3350.group6.promise.objects.enumClasses.AccessRole;


public class MembersListFragment extends Fragment {

    private int projectId;
    private Access access;
    private List<Access> accessList;

    private ListView listView;

    public MembersListFragment() {
        super(R.layout.fragment_members_list);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        projectId = ProjectFragmentArgs.fromBundle(getArguments()).getProjectId();
        access = Service.accesses.getAccessByIDs(CurrentSession.getAccount().getUserID(), projectId);
        accessList = Service.accesses.getProjectAccess(projectId);

        // display the list of all users and their roles that have access to this project
        listView = view.findViewById(R.id.members_list);

        final ArrayAdapter<Access> members = new ArrayAdapter<Access>(view.getContext(), android.R.layout.simple_list_item_2, android.R.id.text1, accessList)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(Service.users.getUserByUserId(accessList.get(position).getUserId()).getName());
                text2.setText(accessList.get(position).getRole());

                return view;
            }
        };

        listView.setAdapter(members);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg) {

                // only CREATORs or ADMINs can change roles
                // ADMINs can only change roles of MEMBERs
                if( (access.getRole().equals(AccessRole.ADMIN.name()) && accessList.get(position).getRole().equals(AccessRole.MEMBER.name()))
                        || (access.getRole().equals(AccessRole.CREATOR.name()) && !accessList.get(position).getRole().equals(AccessRole.CREATOR.name())))
                {
                    Access userAccess = accessList.get(position);
                    String userName = Service.users.getUserByUserId(userAccess.getUserId()).getName();
                    changeRole(userAccess, userName);
                    // TODO: list is not refreshed after role update
                }
            }

        });
    }

    // popup dialogue for changing role of a project member
    public void changeRole(Access userAccess, String userName)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Which role would you like to give " + userName + "?");

        final String roles[] = {AccessRole.MEMBER.name(), AccessRole.ADMIN.name()};
        final int[] checkedItem = {0};
        builder.setSingleChoiceItems(roles, checkedItem[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkedItem[0] = which;
            }
        });

        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // update current access
                userAccess.setRole(roles[checkedItem[0]]);
                Service.accesses.updateAccess(userAccess);
            }
        });

        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
