package com.tmnt.tritontrade.view;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tmnt.tritontrade.R;
import com.tmnt.tritontrade.controller.CurrentState;
import com.tmnt.tritontrade.controller.Encrypt;
import com.tmnt.tritontrade.controller.Server;
import com.tmnt.tritontrade.controller.User;

public class ChangePassword extends AppCompatActivity {

    private User currUser;
    private EditText verifyText;
    private TextView newPW;
    private EditText newPWEntry;
    private TextView confirmPW;
    private EditText confirmPWEntry;
    private Button submit;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);


        currUser = CurrentState.getInstance().getCurrentUser();
        verifyText = (EditText) findViewById(R.id.verifyField);

        // Set all the change password fields to invisible until the password is verified
        newPW = (TextView) findViewById(R.id.newPW);
        newPW.setVisibility(View.INVISIBLE);

        newPWEntry = (EditText) findViewById(R.id.newPWEntry);
        newPWEntry.setVisibility(View.INVISIBLE);

        confirmPW = (TextView) findViewById(R.id.confirmPW);
        confirmPW.setVisibility(View.INVISIBLE);

        confirmPWEntry = (EditText) findViewById(R.id.confirmPWEntry);
        confirmPWEntry.setVisibility(View.INVISIBLE);

        submit = (Button) findViewById(R.id.submit);
        submit.setVisibility(View.INVISIBLE);

        cancel = (Button) findViewById(R.id.Cancel);
        cancel.setVisibility(View.INVISIBLE);
    }

    /**
     * Private Innner class to update the server with the new user info
     */
    private class UpdateUserTask extends AsyncTask<User, Void, Void> {
        protected Void doInBackground(User... params) {
            try {
                Server.modifyExistingUser(params[0]);

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        /*
        @Override
        protected void onPreExecute() {
        }

        protected void onPostExecute(Void result) {
        }
        */
    }


    public void verifyPassword(View view){

        String verifyPW = verifyText.getText().toString();

        String hashedPW = Encrypt.hashpw(verifyPW, currUser.getSalt());

        String currPW = currUser.getPassword();

        if(currPW.equals(hashedPW)) {
            verifyText.setText("true");
            setVisible();
        } else {
            Toast.makeText(this, "The password does not match", Toast.LENGTH_LONG)
                    .show();
        }

    }

    private void setVisible(){
        newPW.setVisibility(View.VISIBLE);

        newPWEntry.setVisibility(View.VISIBLE);

        confirmPW.setVisibility(View.VISIBLE);

        confirmPWEntry.setVisibility(View.VISIBLE);

        submit.setVisibility(View.VISIBLE);

        cancel.setVisibility(View.VISIBLE);
    }


    public void changePW(View view) {

        String firstEntry = newPWEntry.getText().toString();
        String secondEntry = confirmPWEntry.getText().toString();

        if(firstEntry.equals(secondEntry)) {
            Toast.makeText(this, "Password sucessfully changed", Toast.LENGTH_LONG)
                    .show();

            currUser.hashAndSetPassword(firstEntry);
            CurrentState.getInstance().setCurrentUser(currUser);
            new UpdateUserTask().execute(currUser);

            finish();
        } else {
            Toast.makeText(this, "Your new password entries do not match", Toast.LENGTH_LONG)
                    .show();
        }


    }
}

