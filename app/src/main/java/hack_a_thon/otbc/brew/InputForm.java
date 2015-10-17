package hack_a_thon.otbc.brew;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseObject;
import com.parse.ParseQuery;

public class InputForm extends AppCompatActivity {

    EditText post;
    String inputpost;
    int postid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Done Brewing?", Snackbar.LENGTH_LONG)
                        .setAction("Yes!", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar snackbarb = Snackbar.make(view, "Creating a new post!", Snackbar.LENGTH_SHORT);
                                snackbarb.show();
                                post = (EditText) findViewById(R.id.form_input_post);
                                inputpost = post.getText().toString().trim();
                                /*ParseQuery<ParseObject> query = ParseQuery.getQuery("Posts");
                                query.orderByDescending("PostID");
                                if((postid == null)&&(created_at == null ))*/

                                ParseObject poststore = new ParseObject("Posts");
                                poststore.put("Posts", inputpost);
                                poststore.put("PostID", postid+1);
                                poststore.saveInBackground();

                                finish();
                            }
                        }).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
