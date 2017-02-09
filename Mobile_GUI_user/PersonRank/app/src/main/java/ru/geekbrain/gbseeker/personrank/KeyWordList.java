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
import ru.geekbrain.gbseeker.personrank.entities.KeywordListDB;
import ru.geekbrain.gbseeker.personrank.net.RestAPI;


public class KeyWordList extends Fragment {
    KeywordListDB keywordListDB;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        keywordListDB = new KeywordListDB(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.keyword_list,container,false);

        getActivity().setTitle("Справочник - ключевые слова");

        Spinner spinnerPerson = (Spinner) v.findViewById(R.id.keyword_person);
        spinnerPerson.setAdapter(keywordListDB.getAdapterWithPerson());

        ListView keywordList= (ListView) v.findViewById(R.id.keyword_list);
        keywordList.setAdapter(keywordListDB.getAdapterWithWords(getActivity().getSupportLoaderManager()));

        RestAPI.getKeyword(keywordListDB, DBHelper.getInstance().getPersonID(keywordListDB.getSelectedPerson()));


        spinnerPerson.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                RestAPI.getKeyword(keywordListDB, DBHelper.getInstance().getPersonID(keywordListDB.getSelectedPerson()));
                keywordListDB.setSelectedPersonPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        return v;
    }
}



