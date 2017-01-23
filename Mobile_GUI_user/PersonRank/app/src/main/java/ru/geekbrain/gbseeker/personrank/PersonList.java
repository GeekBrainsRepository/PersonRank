package ru.geekbrain.gbseeker.personrank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class PersonList extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.person_list,container,false);

        String[] data = {"Павел", "Медведев", "Путин", "Путин", "Путин", "Путин", "Путин", "Путин", "Путин", "Путин", "Путин", "Путин", "Путин", "Путин", "Путин", "Путин", "Путин", "Путин", "Путин", "Путин", "Путин", "Путин", "Путин", "Путин"        };
        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, data);

        getActivity().setTitle("Список персон");
        ListView list= (ListView) v.findViewById(R.id.PersonList);
        list.setAdapter(adapter);

        return v;
    }
}



