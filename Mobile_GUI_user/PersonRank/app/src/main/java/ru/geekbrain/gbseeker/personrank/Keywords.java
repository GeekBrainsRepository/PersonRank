package ru.geekbrain.gbseeker.personrank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import ru.geekbrain.gbseeker.personrank.DB.DBHelper;
import ru.geekbrain.gbseeker.personrank.entities.KeywordsDB;
import ru.geekbrain.gbseeker.personrank.net.ReloadFromNet;
import ru.geekbrain.gbseeker.personrank.net.RestAPI;


public class Keywords extends Fragment implements ReloadFromNet {
    KeywordsDB keywordsDB;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        keywordsDB = new KeywordsDB(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.keyword_list,container,false);

        getActivity().setTitle("Ключевые слова");

        Spinner spinnerPerson = (Spinner) v.findViewById(R.id.keyword_person);
        spinnerPerson.setAdapter(keywordsDB.getAdapterWithPerson());

        ListView keywordList= (ListView) v.findViewById(R.id.keyword_list);
        keywordList.setAdapter(keywordsDB.getAdapterWithWords(getActivity().getSupportLoaderManager()));

        spinnerPerson.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                keywordsDB.setSelectedPersonPosition((String)parent.getItemAtPosition(position));
                reload();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        reload();

        return v;
    }

    @Override
    public void reload() {
        RestAPI.getKeyword(keywordsDB, DBHelper.getInstance().getPersonID(keywordsDB.getSelectedPerson()));
    }
}



