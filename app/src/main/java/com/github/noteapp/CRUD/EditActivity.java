package com.github.noteapp.CRUD;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.github.noteapp.MainActivity;
import com.github.noteapp.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditActivity extends AppCompatActivity{

    public static final String EXTRA_ID = "com.codinginflow.architectureexample.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.codinginflow.architectureexample.EXTRA_TITLE";
    public static final String EXTRA_MAINCONTENT = "com.codinginflow.architectureexample.EXTRA_MAINCONTENT";
    public static final String EXTRA_DATE = "com.codinginflow.architectureexample.EXTRA_DATE";

    private EditText edTittle;
    private EditText edMainContent;
    private TextView txtDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        edTittle = findViewById(R.id.edTittle);
        edMainContent = findViewById(R.id.edMainContent);
        txtDate = findViewById(R.id.txtDate);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_launcher_foreground);
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            edTittle.setText(intent.getStringExtra(EXTRA_TITLE));
            edMainContent.setText(intent.getStringExtra(EXTRA_MAINCONTENT));
            txtDate.setText(intent.getStringExtra(EXTRA_DATE));
        } else {
            setTitle("Add Note");
        }
    }
    private void saveNote() {
        DateFormat mtanggal = new SimpleDateFormat("d MMM yyyy");

        String title = edTittle.getText().toString();
        String mainContent = edMainContent.getText().toString();
        String date =  mtanggal.format(Calendar.getInstance().getTime());
        if (title.trim().isEmpty() || mainContent.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_MAINCONTENT, mainContent);
        data.putExtra(EXTRA_DATE, date);
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, data);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}