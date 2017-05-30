package com.project.graduation.welcomeback.Home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.graduation.welcomeback.Home.Data.Report;
import com.project.graduation.welcomeback.R;
import com.squareup.picasso.Picasso;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class ReportDetailsActivity extends AppCompatActivity {

    private ImageView mPhotoView;       //Report's photo field view.

    private TextView mNameView,mAgeView,mGenderView,mLocationView
            ,mContactInfoView,mMoreInfoView;   //Report fields views.

    private Report mReport;     //Chosen report.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_details);

        mPhotoView = (ImageView) findViewById(R.id.report_details_photo);
        mNameView = (TextView) findViewById(R.id.report_details_name);
        mAgeView = (TextView) findViewById(R.id.report_details_age);
        mGenderView = (TextView) findViewById(R.id.report_details_gender);
        mLocationView = (TextView) findViewById(R.id.report_details_location);
        mMoreInfoView = (TextView) findViewById(R.id.report_details_more_info);
        mContactInfoView = (TextView) findViewById(R.id.report_details_contact_info);

        mReport = new Report();

        Intent startIntent = getIntent();      //Intent that started this activity.
        if (startIntent != null ){
            Bundle b = startIntent.getExtras();     //Return a bundle with the report fields.
            String unknownField = getResources().getString(R.string.report_details_unknown_field);      //Alternative if a field hasn't been filled.
            //Set fields of the report.
            mReport.setPhoto(b.getString("REPORT_PHOTO"));
            mReport.setName(b.getString("REPORT_NAME").equals("") ? unknownField : b.getString("REPORT_NAME"));
            mReport.setAge(b.getString("REPORT_AGE").equals("") ? unknownField : b.getString("REPORT_AGE"));
            mReport.setGender(b.getString("REPORT_GENDER").equals("") ? unknownField : b.getString("REPORT_GENDER"));
            mReport.setLocation(b.getString("REPORT_LOCATION").equals("") ? unknownField : b.getString("REPORT_LOCATION"));
            mReport.setMoreInfo(b.getString("REPORT_MORE_INFO").equals("") ? unknownField : b.getString("REPORT_MORE_INFO"));
            mReport.setContactInfo(b.getString("REPORT_CONTACT_INFO").equals("") ? unknownField : b.getString("REPORT_CONTACT_INFO"));

            //Loading photo into imageView.
            Picasso.with(this)
                    .load(mReport.getPhoto())
                    .resize(480,640)
                    .into(mPhotoView);

            //Appending Report Fields to their views.
            mNameView.append(" "+mReport.getName());
            mAgeView.append(" "+mReport.getAge());
            mGenderView.append(" "+mReport.getGender());
            mLocationView.append(" "+mReport.getLocation());
            mMoreInfoView.append(" "+mReport.getMoreInfo());
            mContactInfoView.append(" "+mReport.getContactInfo());

        }

    }
}
