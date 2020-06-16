package com.github.noteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.noteapp.CRUD.EditActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    private ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Note APP");


        FloatingActionButton saveNote = (FloatingActionButton) findViewById(R.id.saveNote);
        saveNote.setOnClickListener(this);
        RecyclerView recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        final RecycleAdapter adapter = new RecycleAdapter();
        recyclerView.setAdapter(adapter);
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.submitList(notes);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
        adapter.setOnItemClickListener(new RecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra(EditActivity.EXTRA_ID, note.getId());
                intent.putExtra(EditActivity.EXTRA_TITLE, note.getTittle());
                intent.putExtra(EditActivity.EXTRA_MAINCONTENT, note.getMaincontent());
                intent.putExtra(EditActivity.EXTRA_DATE, note.getDate());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(EditActivity.EXTRA_TITLE);
            String mainContent = data.getStringExtra(EditActivity.EXTRA_MAINCONTENT);
            String date = data.getStringExtra(EditActivity.EXTRA_DATE);
            Note note = new Note(title, mainContent, date);
            viewModel.insert(note);
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(EditActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(EditActivity.EXTRA_TITLE);
            String mainContent = data.getStringExtra(EditActivity.EXTRA_MAINCONTENT);
            String date = data.getStringExtra(EditActivity.EXTRA_DATE);
            Note note = new Note(title, mainContent, date);
            note.setId(id);
            viewModel.update(note);
            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                viewModel.deleteAllNotes();
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.saveNote:
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
                break;

            default:
                break;
        }
    }

}