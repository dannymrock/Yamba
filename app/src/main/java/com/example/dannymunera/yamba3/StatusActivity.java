package com.example.dannymunera.yamba3;

import android.os.Bundle;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class StatusActivity extends AppCompatActivity implements View.OnClickListener{


    private EditText editStatus;
    private Button buttonTweet;

    private static final String TAG = "StatusActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        editStatus = (EditText) findViewById(R.id.editStatus);
        buttonTweet = (Button) findViewById(R.id.buttonTweet);

        buttonTweet.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_status, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        String status = editStatus.getText().toString();
        Log.d(TAG, "onClicked with status= " + status);

        new PostTask().execute(status);
    }

    private final class PostTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            YambaClient yambaCloud = new YambaClient("student","password");
            try {
                yambaCloud.postStatus(params[0]);
                return "successfully posted";
            } catch (YambaClientException e) {
                e.printStackTrace();
                return "Failed to post to yamba service";
            }
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            Toast.makeText(StatusActivity.this, result, Toast.LENGTH_LONG).show();
        }
    }

}
