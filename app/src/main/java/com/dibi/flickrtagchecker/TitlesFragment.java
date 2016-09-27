package com.dibi.flickrtagchecker;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dibi on 27/09/16.
 */

public class TitlesFragment extends android.support.v4.app.Fragment
{
    public interface ItemSelectListener
    {
        public void onItemSelected(int itemPosition);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.titlesfrag_layout, container, false);

        return V;
    }
}
