package com.project.graduation.welcomeback;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUsFragment extends Fragment {

    private EditText mEditTextName;

    private EditText mEditTextEmail;

    private EditText mEditTextPhone;

    private EditText mEditTextMessage;

    private Button mButtonSubmit;

    public ContactUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View contactUs =inflater.inflate(R.layout.fragment_contact_us, container, false);

        mEditTextName = (EditText) contactUs.findViewById(R.id.contact_us_name);

        mEditTextEmail = (EditText) contactUs.findViewById(R.id.contact_us_mail);

        mEditTextPhone = (EditText) contactUs.findViewById(R.id.contact_us_phone);

        mEditTextMessage = (EditText) contactUs.findViewById(R.id.contact_us_message);

        mButtonSubmit = (Button) contactUs.findViewById(R.id.contact_us_submit_button);

        mButtonSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String name = mEditTextName.getText().toString();
                String email= mEditTextEmail.getText().toString();
                String phone = mEditTextPhone.getText().toString();
                String message = mEditTextMessage.getText().toString();

                if( mEditTextName.getText().toString().length()<3){
                    Toast nameError = Toast.makeText(getActivity(), "Please enter full name.",
                            Toast.LENGTH_LONG);
                    nameError.show();
                }
                else if (mEditTextPhone.getText().toString().length()!=11){
                    Toast phoneError = Toast.makeText(getActivity(), "Please enter a valid mobile number.",
                            Toast.LENGTH_LONG);
                    phoneError.show();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(mEditTextEmail.getText().toString()).matches())
                {
                    Toast emailError = Toast.makeText(getActivity(), "Please enter a valid email Address.",
                            Toast.LENGTH_LONG);
                    emailError.show();
                }
                else if(mEditTextMessage.getText().toString()== null)
                {
                    Toast messageError = Toast.makeText(getActivity(), "Please enter your message.",
                            Toast.LENGTH_LONG);
                    messageError.show();
                }
            }
        });

        return contactUs;
    }


}
