package com.chakib.mvvm.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.chakib.mvvm.R;
import com.chakib.mvvm.adapter.NoteAdapter;
import com.chakib.mvvm.model.Note;
import com.chakib.mvvm.viewModel.NoteViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init recycler view
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //initAdapter
        final NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);



        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                //update RecyclerView
                Toast.makeText(MainActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
                adapter.setNotes(notes);
            }
        });
    }
}