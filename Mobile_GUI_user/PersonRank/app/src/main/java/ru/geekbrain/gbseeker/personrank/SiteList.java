package ru.geekbrain.gbseeker.personrank;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class SiteList extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.site_list,container,false);

        String[] data = {"lenta.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru"};
        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, data);

        getActivity().setTitle("Список сайтов");
        ListView list= (ListView) v.findViewById(R.id.SiteList);
        list.setAdapter(adapter);

        return v;
    }
}



