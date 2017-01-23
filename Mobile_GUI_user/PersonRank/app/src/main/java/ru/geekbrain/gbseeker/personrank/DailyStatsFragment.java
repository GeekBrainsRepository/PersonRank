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


public class DailyStatsFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.daily_stats,container,false);

        String[] dataSites = {"lenta.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru"};
        String[] dataPersons = {"Путин", "Medvedev","Antonov"};

        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, dataSites);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) v.findViewById(R.id.daily_stats_site);
        spinner.setAdapter(adapter);
        // адаптер
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, dataPersons);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner1 = (Spinner) v.findViewById(R.id.daily_stats_person);
        spinner1.setAdapter(adapter);

        getActivity().setTitle("Ежедневная статистика");

        // адаптер
      /*  ArrayAdapter<String> adapterKeyWord = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, dataKeyWords);

        ListView list= (ListView) v.findViewById(R.id.keyword_list);
        list.setAdapter(adapter);
*/

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                Toast.makeText(getActivity(), "Position = " + position, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        return v;
    }
}





