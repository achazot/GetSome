package com.dibi.flickrtagchecker;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity
        extends FragmentActivity
        implements TitlesFragment.OnCreateListener
{
    // *********************************************************************************************
    // *********************************** Variables ***********************************************
    // *********************************************************************************************

    private FragmentTabHost m_tabhost;
    private ArrayList<String> m_titles = new ArrayList<String>();
    private ArrayList<String> m_links = new ArrayList<String>();


    // *********************************************************************************************
    // ****************************** Fragments Callbacks ******************************************
    // *********************************************************************************************

    public void notifyTitleListCreate()
    {
        updateFragments();
    }

    // *********************************************************************************************
    // ******************************* HTTP Request Task *******************************************
    // *********************************************************************************************
    private class GetSomeTask extends AsyncTask<String, Integer, String>
    {
        @Override
        protected String doInBackground(String... params)
        {
            String responseStr = "";
            String dataUrl = "http://www.flickr.com/services/feeds/photos_public.gne" + "?tags=" + params[0] + "&format=json";
            URL url;
            HttpURLConnection connection = null;
            try
            {
                url = new URL(dataUrl);
                connection = (HttpURLConnection) url.openConnection();
                InputStream is = connection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer response = new StringBuffer();
                while ((line = rd.readLine()) != null)
                {
                    response.append(line);
                    response.append('\r');
                }
                rd.close();
                responseStr = response.toString().substring(15, response.toString().length()-1);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                if (connection != null)
                    connection.disconnect();
            }
            return responseStr;
        }

        protected void onPostExecute(String result)
        {
            try
            {
                JSONParser(result);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            updateFragments();
        }
    }

    // *********************************************************************************************
    // ******************************* JSON Parser ***********************************************
    // *********************************************************************************************
    private void JSONParser( String string ) throws JSONException
    {
        JSONObject jsonObject = new JSONObject( string );
        JSONArray jsonArray = jsonObject.getJSONArray( "items" );
        for ( int i = 0; i < jsonArray.length(); i++ )
        {
            JSONObject explorerObject = jsonArray.getJSONObject( i );
            m_titles.add(explorerObject.getString( "title" ));
            m_links.add(explorerObject.getString( "link" ));
        }
    }


    // *********************************************************************************************
    // ******************************* Activity Init ***********************************************
    // *********************************************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TabHost Creation
        m_tabhost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        m_tabhost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        m_tabhost.addTab(m_tabhost.newTabSpec("tabTitles").setIndicator("TITLES"),
                TitlesFragment.class, null);
        m_tabhost.addTab(m_tabhost.newTabSpec("tabList").setIndicator("LIST"),
                ListFragment.class, null);
        m_tabhost.addTab(m_tabhost.newTabSpec("tabGrid").setIndicator("GRID"),
                GridFragment.class, null);
        m_tabhost.setCurrentTabByTag("tabList");

        // Button Action
        final Button getSome = (Button) findViewById(R.id.getSomeButton);
        getSome.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                final EditText tag = (EditText) findViewById(R.id.TagInput);
                Toast.makeText(MainActivity.this, "getting " + tag.getText().toString(), Toast.LENGTH_SHORT).show();
                new GetSomeTask().execute(tag.getText().toString());
            }
        });
    }


    private void updateFragments()
    {
        TitlesFragment tfrag = (TitlesFragment)
                getSupportFragmentManager().findFragmentByTag("tabTitles");
        if (tfrag != null)
            tfrag.update(m_titles);

    }




}