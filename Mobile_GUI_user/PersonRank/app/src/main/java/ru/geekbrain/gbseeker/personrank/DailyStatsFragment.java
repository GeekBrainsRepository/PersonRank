package ru.geekbrain.gbseeker.personrank;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;


public class DailyStatsFragment extends Fragment {
    int myYear = 2011;
    int myMonth = 2;
    int myDay = 3;
    int myYear1 = 2011;
    int myMonth1 = 2;
    int myDay1 = 3;
    Button butFrom;
    Button butTo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.daily_stats, container, false);

        String[] dataSites = {"lenta.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru", "mail.ru"};
        String[] dataPersons = {"Путин", "Medvedev", "Antonov"};

        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, dataSites);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) v.findViewById(R.id.daily_stats_site);
        spinner.setAdapter(adapter);

        // адаптер
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, dataPersons);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner1 = (Spinner) v.findViewById(R.id.daily_stats_person);
        spinner1.setAdapter(adapter1);

        getActivity().setTitle("Ежедневная статистика");


        butFrom = (Button) v.findViewById(R.id.date_from);
        butFrom.setText("c " + android.text.format.DateFormat.format("yyyy-MM-dd", new Date(myYear-1900,myMonth,myDay)));

        butFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog tpd = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                myYear = year;
                                myMonth = monthOfYear;
                                myDay = dayOfMonth;
                                butFrom.setText("c " + android.text.format.DateFormat.format("yyyy-MM-dd", new Date(myYear-1900,myMonth,myDay)));                            }
                        },
                        myYear, myMonth, myDay);
                tpd.show();
            }
        });
        butTo = (Button) v.findViewById(R.id.date_to);
        butTo.setText("по " + android.text.format.DateFormat.format("yyyy-MM-dd", new Date(myYear1-1900,myMonth1,myDay1)));

        butTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog tpd = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                myYear1 = year;
                                myMonth1 = monthOfYear;
                                myDay1 = dayOfMonth;

                                butTo.setText("по " + android.text.format.DateFormat.format("yyyy-MM-dd", new Date(myYear1-1900,myMonth1,myDay1)));
                            }
                        },
                        myYear1, myMonth1, myDay1);
                tpd.show();
            }
        });


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
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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





