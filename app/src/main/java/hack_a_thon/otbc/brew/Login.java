package hack_a_thon.otbc.brew;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class Login extends AppCompatActivity {

    protected EditText mUsername;
    protected EditText mPassword;
    protected Button LoginButton;

    //protected TextView mSignUpTextView;
    public Button go_to_signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //mSignUpTextView = (TextView) findViewById(R.id.signuptext);
        go_to_signUp =(Button)findViewById(R.id.login_signupbutton);
        go_to_signUp.setOnClickListener(new View.OnClickListener() {//what happens when we click sign up text
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUpAct.class);
                startActivity(intent);
            }
        });
        mUsername = (EditText) findViewById(R.id.login_username);
        mPassword = (EditText) findViewById(R.id.login_pass);

        LoginButton = (Button) findViewById(R.id.login_loginbutton);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Username = mUsername.getText().toString();
                String Password = mPassword.getText().toString();//as the text is editable which is a different data type we change it to a string

                Username = Username.trim();//trim gets rid of spaces added on the ends by mistake
                Password = Password.trim();

                if (Username.isEmpty() || Password.isEmpty()) {//checks if the Username or password is empty or not
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                        builder.setMessage(R.string.LoginErrorMessage);//Error for Wrong input
                        builder.setTitle(R.string.LoginErrorTitle);
                        builder.setPositiveButton(android.R.string.ok, null);
                        AlertDialog Dialog = builder.create();
                        Dialog.show();//Gives an alert dialog with ok as an option
                    }
                } else {
                    ParseUser.logInInBackground(Username, Password, new LogInCallback() {
                        @Override
                        public void done(ParseUser User, ParseException e) {
                            if (User != null) {
                                //Success in logging in (null means we have a valid user)
                                Toast toast = Toast.makeText(getApplicationContext(), "Login Succesful",
                                        Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 100);
                                toast.show();
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);//these flags are used for the user not to return to sigupActivity when pressed back, it clears the task stack
                                startActivity(intent);
                                finish();
                            } else {//login failed,signup failed
                                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                                builder.setMessage(e.getMessage());
                                builder.setTitle(R.string.LoginErrorTitle);
                                builder.setPositiveButton(android.R.string.ok, null);
                                AlertDialog Dialog = builder.create();
                                Dialog.show();

                            }
                        }
                    });

                    //Login user
                }
            }
        });


    }

}
