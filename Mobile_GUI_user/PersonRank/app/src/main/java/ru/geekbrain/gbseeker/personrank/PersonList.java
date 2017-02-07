package ru.geekbrain.gbseeker.personrank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import ru.geekbrain.gbseeker.personrank.entities.PersonListDB;
import ru.geekbrain.gbseeker.personrank.net.RestAPI;

public class PersonList extends Fragment {
    PersonListDB personListDB;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        personListDB = new PersonListDB(getContext());
        RestAPI.getPerson(personListDB);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.person_list, container, false);

        getActivity().setTitle("Список персон");

        ListView list = (ListView) v.findViewById(R.id.PersonList);
        SimpleCursorAdapter scAdapter = personListDB.getAdapterWithPerson(getActivity().getSupportLoaderManager());
        list.setAdapter(scAdapter);

        return v;
    }
}



