/**
 * Amanda Aldrich
 *
 * Main Activity for Login and Register Capabilities
 */

package group4.tcss450.uw.edu.campanion;

import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity implements ChoiceFragment.OnFragmentInteractionListener,
        LoginFragment.OnFragmentInteractionListener, SignupFragment.OnFragmentInteractionListener,
        PlaceHolderFragment.OnFragmentInteractionListener {


    private static final String PARTIAL_URL = "http://cssgate.insttech.washington.edu/" +
            "~msbry92/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            if (findViewById(R.id.fragmentContainer) != null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragmentContainer, new ChoiceFragment())
                        .commit();
            }
        }
    }


    //calls login or register depending on button press
    @Override
    public void onFragmentInteraction(ChoiceFragment.Choice choice) {

        switch (choice){
            case  LOGIN:

                LoginFragment loginFragment;
                loginFragment = new LoginFragment();
                Bundle args = new Bundle();
                args.putSerializable(getString(R.string.choice_key), choice);
                loginFragment.setArguments(args);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new LoginFragment())
                        .addToBackStack(null);


                // Commit the transaction
                transaction.commit();
                //do things

                break;

            case  REGISTER:

                SignupFragment signupFragment;
                signupFragment = new SignupFragment();
                Bundle args1 = new Bundle();
                args1.putSerializable(getString(R.string.choice_key), choice);
                signupFragment.setArguments(args1);
                FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new SignupFragment())
                        .addToBackStack(null);


                // Commit the transaction
                transaction1.commit();

                break;

        }
    }


    //login hands off to our placeholder for now
    @Override
    public void onLoginFragmentInteraction(String email, String password) {

        if(email.equals("forgot") && password.equals("forgot")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                    new ChoiceFragment());
        } else {
            AsyncTask<String, Void, String> task = new LoginTask();
            task.execute(PARTIAL_URL, email, password);
            Log.d("Login", email);
        }

    }

    //signup hands off to our login for now
    @Override
    public void onSignupFragmentInteraction(String email, String password) {

        AsyncTask<String, Void, String> task = new SignUpTask();
        task.execute(PARTIAL_URL, email, password);
        Log.d("Register", email);
    }

    @Override
    public void placeHolderFragmentInteraction(String email, String code) {
        AsyncTask<String, Void, String> task = new PlaceHolderTask();
        task.execute(PARTIAL_URL, email, code);
    }

    private class LoginTask extends AsyncTask<String, Void, String> {
        private final String SERVICE = "login.php";
        @Override
        protected String doInBackground(String... strings) {
            String response = "";
            HttpURLConnection urlConnection = null;
            String url = strings[0];
            String args = "?email=" + strings[1] + "&password=" + strings[2];
            try {
                URL urlObject = new URL(url + SERVICE + args);
                urlConnection = (HttpURLConnection) urlObject.openConnection();
                InputStream content = urlConnection.getInputStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s = "";
                while ((s = buffer.readLine()) != null) {
                    response += s;
                }
            } catch (Exception e) {
                response = "Unable to connect, Reason: "
                        + e.getMessage();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            return response;
        }
        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            if (result.startsWith("Unable to")) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            } else if (result.startsWith("{\"code\":200")) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
            } else {
                //String[] string = result.split("\"");

            }
            Log.d("Login Result", result);
            //mTextView.setText(result);
        }
    }

    private class SignUpTask extends AsyncTask<String, Void, String> {
        private final String SERVICE = "register.php";
        private String myEmail;
        @Override
        protected String doInBackground(String... strings) {
            String response = "";
            HttpURLConnection urlConnection = null;
            String url = strings[0];
            String args = "?email=" + strings[1] + "&password=" + strings[2];
            myEmail = strings[1];

            try {
                URL urlObject = new URL(url + SERVICE + args);
                urlConnection = (HttpURLConnection) urlObject.openConnection();
                InputStream content = urlConnection.getInputStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s = "";
                while ((s = buffer.readLine()) != null) {
                    response += s;
                }
            } catch (Exception e) {
                response = "Unable to connect, Reason: "
                        + e.getMessage();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            return response;
        }
        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            if (result.startsWith("Unable to")) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            } else if (result.startsWith("{\"code\":200")) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
            } else {
                //String[] string = result.split("\"");

                PlaceHolderFragment verifyFragment = new PlaceHolderFragment();
                Bundle args = new Bundle();
                args.putString("email", myEmail);
                verifyFragment.setArguments(args);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, verifyFragment)
                        .addToBackStack(null).commit();


            }
            Log.d("Login Result", result);
            //mTextView.setText(result);
        }
    }

    private class PlaceHolderTask extends AsyncTask<String, Void, String> {
        private final String SERVICE = "db_functions.php";
        @Override
        protected String doInBackground(String... strings) {
            String response = "";
            HttpURLConnection urlConnection = null;
            String url = strings[0];
            String args = "?email=" + strings[1] + "&code=" + strings[2];
            try {
                URL urlObject = new URL(url + SERVICE + args);
                urlConnection = (HttpURLConnection) urlObject.openConnection();
                InputStream content = urlConnection.getInputStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s = "";
                while ((s = buffer.readLine()) != null) {
                    response += s;
                }
            } catch (Exception e) {
                response = "Unable to connect, Reason: "
                        + e.getMessage();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            return response;
        }
        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            if (result.startsWith("Unable to")) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            } else if (result.startsWith("{\"code\":200")) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
            } else {

                LoginFragment loginFragment = new LoginFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, loginFragment)
                        .addToBackStack(null).commit();

            }
            Log.d("Login Result", result);
        }
    }
}

