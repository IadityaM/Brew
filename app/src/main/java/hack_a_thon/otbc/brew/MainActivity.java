package hack_a_thon.otbc.brew;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<ParseObject> ob;

    ProgressDialog mProgressDialog;
    ArrayAdapter<String> adapter;
    ListView PostList;
    boolean inputpnr;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        inputpnr=true;

        final Intent go_to_form = new Intent(MainActivity.this, InputForm.class);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Wanna Brew Something?", Snackbar.LENGTH_LONG)
                        .setAction("Brew Now!", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar snackbarb = Snackbar.make(view, "Creating a new post!", Snackbar.LENGTH_SHORT);
                                snackbarb.show();
                                startActivity(go_to_form);
                            }
                        }).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*Toast toast = Toast.makeText(getApplicationContext(), "Login Succesful",
            Toast.LENGTH_SHORT);
    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 100);
    toast.show();*/

        // Execute RemoteDataTask AsyncTask
        new RemoteDataTask().execute();
    }


    // RemoteDataTask AsyncTask
    public class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
// Create a progressdialog
            mProgressDialog = new ProgressDialog(MainActivity.this);
// Set progressdialog title
            mProgressDialog.setTitle("Finding out !");
// Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
// Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
// Locate the class table named "ReservationList_of_Passengers" in Parse.com
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Posts");
            query.whereExists("PostID");
            query.orderByDescending("createdAt");
            try {
                ob = query.find();
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
/*
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ReservationList_of_Passengers");
            query.whereEqualTo("PostID", inputpnr);
            query.orderByDescending("_created_at");
            try {
                ob = query.find();
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;*/
        }

        @Override
        protected void onPostExecute(Void result) {
// Locate the listview in listview_main.xml
            PostList = (ListView) findViewById(R.id.a_pass1);
// Pass the results into an ArrayAdapter
            adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.listview_item);
// Retrieve object "name" from Parse.com database
            for (ParseObject passlist : ob) {
                adapter.add((String) passlist.get("Posts"));
                //adapter.add((String) passlist.get("Likes"));
/*                adapter.add((String) passlist.get("Coach_Seat_and_Berth"));
                adapter.add((String) passlist.get("Presence_of_passenger"));*/
            }
// Binds the Adapter to the ListView
            PostList.setAdapter(adapter);
// Close the progressdialog
            mProgressDialog.dismiss();
// Capture button clicks on ListView items
            PostList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
// Send single item click data to SingleItemView Class
                    Intent i = new Intent(MainActivity.this, SingleItemView.class);
// Pass data "name" followed by the position
                    i.putExtra("Name_of_Passenger", ob.get(position).getString("Name_of_Passenger").toString());
// Open SingleItemView.java Activity
                    startActivity(i);
                }
            });
        }

    }
}
