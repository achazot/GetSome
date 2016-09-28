package com.dibi.flickrtagchecker;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by dibi on 27/09/16.
 */

public class GridFragment extends android.support.v4.app.Fragment
{
    TextView t;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.gridfrag_layout, container, false);

        return V;
    }
}
