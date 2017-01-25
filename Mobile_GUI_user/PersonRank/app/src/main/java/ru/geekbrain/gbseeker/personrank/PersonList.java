package ru.geekbrain.gbseeker.personrank;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.concurrent.TimeUnit;

public class PersonList extends Fragment  {
    SimpleCursorAdapter scAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.person_list, container, false);

        getActivity().setTitle("Список персон");
        ListView list = (ListView) v.findViewById(R.id.PersonList);

        // формируем столбцы сопоставления
        String[] from = new String[] { DBHelper.DB.COLUMNS.PERSON.PERSON};
        int[] to = new int[] {  android.R.id.text1};

        // создаем адаптер и настраиваем список
        scAdapter = new SimpleCursorAdapter(getContext(), android.R.layout.simple_list_item_1, null, from, to, 0);
        getActivity().getSupportLoaderManager().initLoader(0, null,  new GetCursor(getContext(),scAdapter),);

        list.setAdapter(scAdapter);

        // создаем лоадер для чтения данных
        return v;
    }

    static class MyCursorLoader extends CursorLoader {

        public MyCursorLoader(Context context) {
            super(context);
        }

        @Override
        public Cursor loadInBackground() {
            return DBHelper.getInstance().getCursorWithPerson();
        }

    }


}



