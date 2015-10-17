package hack_a_thon.otbc.brew;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpAct extends AppCompatActivity {

    protected EditText mUsername;
    protected EditText mPassword;
    protected EditText mPasswordconf;
    protected Button mSignupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mUsername = (EditText) findViewById(R.id.signup_uname);
        mPassword = (EditText) findViewById(R.id.signupact_pass);
        mPasswordconf = (EditText) findViewById(R.id.signupact_passconf);

        mSignupButton = (Button) findViewById(R.id.signupact_signupbutton);

        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Username = mUsername.getText().toString();
                String Password = mPassword.getText().toString();
                String Passconf = mPasswordconf.getText().toString();

                Username = Username.trim();
                /*Password = Password.trim();
                Passconf = Passconf.trim();*/

                if (Username.isEmpty() || Password.isEmpty() || Passconf.isEmpty()) {//refer to Login Activity comments for this section
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpAct.this);
                    builder.setMessage(R.string.SignUpErrorMessage);
                    builder.setTitle(R.string.SignUpErrorTitle);
                    builder.setPositiveButton(android.R.string.ok, null);
                    AlertDialog Dialog = builder.create();
                    Dialog.show();
                /*} else if (Password != Passconf) {//refer to Login Activity comments for this section
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpAct.this);
                    builder.setMessage(R.string.SignUpErrorMessage2);
                    builder.setTitle(R.string.SignUpErrorTitle);
                    builder.setPositiveButton(android.R.string.ok, null);
                    AlertDialog Dialog = builder.create();
                    Dialog.show();*/
                } else {
                    ParseUser newUser = new ParseUser();//using parse.com api

                    newUser.setUsername(Username);
                    newUser.setPassword(Password);
                    //newUser.setEmail(Email);

                    newUser.signUpInBackground(new SignUpCallback() {//callback is used for the backend to ping us when it has processed the data
                        @Override
                        public void done(ParseException e) {//done method refers to when the data is processed by the backend
                            if (e == null) {                                // SignUp successful!
                                Intent intent = new Intent(SignUpAct.this, MainActivity.class);
                                Toast toast = Toast.makeText(getApplicationContext(), "SignUp is Succesful",
                                        Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 100);
                                toast.show();
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);//these flags are used for the user not to return to sigupActivity when pressed back, it clears the task stack
                                startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignUpAct.this);
                                builder.setMessage(e.getMessage());
                                builder.setTitle(R.string.SignUpErrorTitle);
                                builder.setPositiveButton(android.R.string.ok, null);
                                AlertDialog Dialog = builder.create();
                                Dialog.show();
                            }
                        }
                    });

                    //Create user
                }
            }
        });

    }

}
