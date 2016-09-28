package com.dibi.flickrtagchecker;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

/**
 * Created by dibi on 27/09/16.
 */

public class TitlesFragment extends android.support.v4.app.Fragment
{
    public OnCreateListener creationCallback;
    private ArrayList<String> m_titles = new ArrayList<String>();
    private ArrayAdapter<String> m_adapter;
    private ListView TitlesList;

    public interface OnCreateListener
    {
        public void notifyTitleListCreate();
    }

    public void update(ArrayList<String> titles)
    {
        m_titles.clear();
        m_titles.addAll(titles);
        refreshList();
    }

    public void refreshList()
    {
        m_adapter.notifyDataSetChanged();
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        try
        {
            creationCallback = (OnCreateListener) context;
        } catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString()
                    + " must implement OnCreateListener");
        }


        Log.i("onAttach", "OK");
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        m_adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, m_titles);

        creationCallback.notifyTitleListCreate();

        Log.i("onCreate", "OK");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.titlesfrag_layout, container, false);

        TitlesList = (ListView) V.findViewById(R.id.TitlesList);
        TitlesList.setAdapter(m_adapter);

        Log.i("onCreateView", "OK");
        return V;
    }

    @Override
    public void onResume ()
    {
        super.onResume ();
        refreshList();
        Log.i("onResume", "OK");
    }
}


