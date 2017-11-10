/**
 * Amanda Aldrich
 *
 * This is just a placeholder
 */

package group4.tcss450.uw.edu.campanion;

import android.content.Context;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 *
 * create an instance of this fragment.
 */
public class PlaceHolderFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;


    public PlaceHolderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_place_holder, container, false);
        Button b = (Button) v.findViewById(R.id.submit);
        b.setOnClickListener(this);

        return v;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        //Log.d("GET IN THERE", "AND HERE");

        if(mListener != null) {
            Log.d("GET IN THERE", "AND HERE");

            if(v.getId() == R.id.submit) {
                //Log.d("GET IN THERE", "AND HERE");
                EditText code = (EditText) getView().findViewById(R.id.verify_code);

                String email = getArguments().getString("email");
                String verifyCode = code.getText().toString();

                if(verifyCode.isEmpty()) {
                    code.setError("Enter the code sent to your email");
                } else {
                    mListener.placeHolderFragmentInteraction(email, verifyCode);
                }
            }
        }
    }

    public interface OnFragmentInteractionListener {
        void placeHolderFragmentInteraction(String email, String code);
    }
}
