package com.chakib.mvvm.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chakib.mvvm.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddEditNoteActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.chakib.mvvm.view.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.chakib.mvvm.view.EXTRA_TITLE";

    public static final String EXTRA_DESCRIPTION =
            "com.chakib.mvvm.view.EXTRA_DESCRIPTION";

    public static final String EXTRA_PRIORITY =
            "com.chakib.mvvm.view.EXTRA_PRIORITY";

    @BindView(R.id.title_et)
    EditText titleEt;
    @BindView(R.id.description_et)
    EditText descriptionEt;
    @BindView(R.id.priority_np)
    NumberPicker priorityNp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        ButterKnife.bind(this);

        priorityNp.setMinValue(1);
        priorityNp.setMaxValue(10);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();


        if (intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Note");
            titleEt.setText(intent.getStringExtra(EXTRA_TITLE));
            descriptionEt.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            priorityNp.setValue(intent.getIntExtra(EXTRA_PRIORITY,1));

        } else {
            setTitle("Add Note");
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflator = getMenuInflater();
        menuInflator.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.save_note):
                saveNote();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void saveNote() {
        String title = titleEt.getText().toString().trim();
        String desc = descriptionEt.getText().toString().trim();
        int priority = priorityNp.getValue();

        if (title.isEmpty() || desc.isEmpty()) {
            Toast.makeText(this, "Please insert a text and description", Toast.LENGTH_LONG);

            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, desc);
        data.putExtra(EXTRA_PRIORITY, priority);

        int id = getIntent().getIntExtra(EXTRA_ID,-1);
        if (id != -1){
            data.putExtra(EXTRA_ID,id);
        }

        setResult(RESULT_OK, data);
        finish();

    }
}
