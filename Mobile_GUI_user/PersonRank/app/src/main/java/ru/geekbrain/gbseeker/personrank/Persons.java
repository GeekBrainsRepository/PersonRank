package ru.geekbrain.gbseeker.personrank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import ru.geekbrain.gbseeker.personrank.entities.PersonsDB;
import ru.geekbrain.gbseeker.personrank.net.ReloadFromNet;
import ru.geekbrain.gbseeker.personrank.net.RestAPI;

public class Persons extends Fragment implements ReloadFromNet {
    private PersonsDB personsDB;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        personsDB = new PersonsDB(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.person_list, container, false);

        getActivity().setTitle(R.string.person_list);

        ListView list = (ListView) v.findViewById(R.id.PersonList);
        SimpleCursorAdapter scAdapter = personsDB.getAdapterWithPerson(getActivity().getSupportLoaderManager());
        list.setAdapter(scAdapter);

        reload();

        return v;
    }

    @Override
    public void reload() {
        RestAPI.getPersons(personsDB);
    }
}



