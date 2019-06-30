package com.example.bloodservice.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bloodservice.R;
import com.example.bloodservice.constants.Constants;
import com.example.bloodservice.database.AppDatabase;
import com.example.bloodservice.database.AppExecutors;
import com.example.bloodservice.models.Person;

import java.text.DateFormat;
import java.util.Calendar;

public class EditActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText name, email,phoneNumber,notes;
    TextView tvLastDate;
    Spinner bloodType;
    Button button;
    int mPersonId;
    Intent intent;
    private AppDatabase mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Button btnDate = (Button) findViewById(R.id.btnDate);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initViews();

        mDb = AppDatabase.getInstance(getApplicationContext());
        intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.UPDATE_Person_Id)) {
            button.setText("Update");
            mPersonId = intent.getIntExtra(Constants.UPDATE_Person_Id, -1);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    Person person = mDb.personDao().loadPersonById(mPersonId);
                    populateUI(person);
                }
            });

        }

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        TextView textView = (TextView) findViewById(R.id.tvLastDate);
        textView.setText(currentDateString);
    }

    private void populateUI(Person person) {

        if (person == null) {
            return;
        }

        name.setText(person.getName());
        email.setText(person.getFaceBookLink());
        phoneNumber.setText(person.getNumber());
        bloodType.setSelection(0);
        tvLastDate.setText(person.getLastDate());
        notes.setText(person.getNotes());
    }

    private void initViews() {
        name = findViewById(R.id.edit_name);
        email = findViewById(R.id.edit_email);
        phoneNumber = findViewById(R.id.edit_number);
        tvLastDate = findViewById(R.id.tvLastDate);
        button = findViewById(R.id.button);
        notes = findViewById(R.id.edit_notes);
        bloodType =findViewById(R.id.spinnerBloodType);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClicked();
            }
        });
    }

    public void onSaveButtonClicked() {
        final Person person = new Person(
                name.getText().toString(),
                phoneNumber.getText().toString(),
                notes.getText().toString(),
                bloodType.getSelectedItem().toString(),
                tvLastDate.getText().toString(),
                email.getText().toString());

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (!intent.hasExtra(Constants.UPDATE_Person_Id)) {
                    mDb.personDao().insertPerson(person);
                } else {
                    person.setId(mPersonId);
                    mDb.personDao().updatePerson(person);
                }
                finish();
            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
