package com.example.cyncyn.plainolnotes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class  EditorActivity extends AppCompatActivity {


    private String action;
    private String noteFilter;

    private EditText editor;
    private String oldText;

    private String oldQunatity;
    private EditText editorQuantity;

    private String oldExp;
    private EditText editorExp;

    private CheckBox editorCheck;
    private int oldCheck = -1;

    private CheckBox editorCheck2;
    private int oldCheck2 = -1;

    private String oldEmail;
    private EditText editorEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        editor = (EditText) findViewById(R.id.editProduct);
        editorQuantity = (EditText) findViewById(R.id.editNumber);
        editorExp = (EditText) findViewById(R.id.editExpiry);
        editorCheck = (CheckBox) findViewById(R.id.checkBox1);
        editorCheck2 = (CheckBox) findViewById(R.id.checkBox);
        editorEmail = (EditText) findViewById(R.id.editEmail);


        Intent intent = getIntent();

        Uri uri = intent.getParcelableExtra(NotesProvider.CONTENT_ITEM_TYPE);

        if(uri == null){
            action = Intent.ACTION_INSERT;
            setTitle(getString(R.string.new_note));
        } else {
            action = Intent.ACTION_EDIT;
            noteFilter = DBOpenHelper.NOTE_ID + "=" + uri.getLastPathSegment();

            Cursor cursor = getContentResolver().query(uri,
                    DBOpenHelper.ALL_COLUMNS, noteFilter, null, null);
            cursor.moveToFirst();
            oldText = cursor.getString(cursor.getColumnIndex(DBOpenHelper.NOTE_TEXT));
            editor.setText(oldText);
            editor.requestFocus();

            oldQunatity = cursor.getString(cursor.getColumnIndex(DBOpenHelper.NOTE_QUANTITY));
            editorQuantity.setText(oldQunatity);

            oldExp = cursor.getString(cursor.getColumnIndex(DBOpenHelper.NOTE_EXPIRY));
            editorExp.setText(oldExp);

            oldCheck = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.NOTE_CHECK));
            if(oldCheck == 1 ){
                    editorCheck.setChecked(true);
                }
            else if (oldCheck == 0) {
                    editorCheck.setChecked(false);
                }

            oldCheck2 = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.NOTE_CHECK2));
            if(oldCheck2 == 1 ){
                    editorCheck2.setChecked(true);
                }
            else if (oldCheck2 == 0) {
                    editorCheck2.setChecked(false);
                }

            oldEmail = cursor.getString(cursor.getColumnIndex(DBOpenHelper.NOTE_EMAIL));
            editorEmail.setText(oldEmail);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(action.equals(Intent.ACTION_EDIT)) {
            getMenuInflater().inflate(R.menu.menu_editor, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finishEditing();
                break;
            case R.id.action_delete:
                deleteNote();
                break;
        }

        return true;
    }
    private void deleteNote() {
        getContentResolver().delete(NotesProvider.CONTENT_URI,
                noteFilter, null);
        Toast.makeText(this, getString(R.string.note_deleted),
                Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    private void finishEditing() {
        String newText = editor.getText().toString().trim();
        String newExp = editorExp.getText().toString().trim();
        String newEmail = editorEmail.getText().toString().trim();
        String newQuantity = editorQuantity.getText().toString().trim();

        //int newNum;
        //newNum = Integer.parseInt(noteNum);

        int newCheck = oldCheck;
        if (editorCheck.isChecked()) {
                newCheck = 1;
            }
        else if (!editorCheck.isChecked()) {
                newCheck = 0;
            }

        int newCheck2 = oldCheck2;

        if (editorCheck2.isChecked()) {
                newCheck2 = 1;
            }
        else if (!editorCheck2.isChecked()) {
                newCheck2 = 0;
            }

        boolean finished = true;

        Log.i("Test","In Finished");
        switch (action) {
            case Intent.ACTION_INSERT:
                Log.i("Test","ACTION INSERT");
                if (newText.length() == 0 || newQuantity.length() == 0 || newCheck == -1 || newCheck2 == -1 || newEmail.length() == 0
                        || newExp.length() == 0) {
                    finished = false;
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    Log.i("Test", "insert failed");
                    setResult(RESULT_CANCELED);
                }
                else {
                    Log.i("Test", "inserted");
                    insertNote(newText, newQuantity, newExp, newCheck, newCheck2, newEmail);
                }
                break;

            case Intent.ACTION_EDIT:
                Log.i("Test","ACTION EDIT");
                if (newText.length() == 0 && newQuantity.length() ==0 && newCheck == 0 && newExp.length() ==0 && newCheck2 == 0 && newEmail.length() == 0) {
                    deleteNote();
                    Log.i("Test", "delete");
                } else if (newText == oldText && newExp == oldExp && oldCheck == newCheck && oldCheck2 == newCheck2 && newEmail == oldEmail) {
                    setResult(RESULT_CANCELED);
                    Log.i("Test", "cancel");
                }
                else if(newText.length() == 0 || newQuantity.length() ==0 || newEmail.length()==0 || newExp.length() == 0){
                    finished = false;
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    updateNote(newText, newQuantity, newExp, newCheck, newCheck2, newEmail);
                    Log.i("Test", "updated " + newQuantity);
                }

        }
        if(finished)finish();
    }

    private void updateNote(String noteText, String newQuantity, String newExp, int newCheck, int newCheck2, String newEmail) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.NOTE_TEXT, noteText);
        values.put(DBOpenHelper.NOTE_QUANTITY, newQuantity);
        values.put(DBOpenHelper.NOTE_EXPIRY, newExp);
        values.put(DBOpenHelper.NOTE_CHECK, newCheck);
        values.put(DBOpenHelper.NOTE_CHECK2, newCheck2);
        values.put(DBOpenHelper.NOTE_EMAIL, newEmail);
        getContentResolver().update(NotesProvider.CONTENT_URI, values, noteFilter, null);
        //Toast.makeText(this, R.string.note_updated, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
    }

    private void insertNote(String noteText, String newQuantity, String newExp, int newCheck, int newCheck2, String newEmail) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.NOTE_TEXT, noteText);
        values.put(DBOpenHelper.NOTE_QUANTITY, newQuantity);
        values.put(DBOpenHelper.NOTE_EXPIRY, newExp);
        values.put(DBOpenHelper.NOTE_CHECK, newCheck);
        values.put(DBOpenHelper.NOTE_CHECK2, newCheck2);
        values.put(DBOpenHelper.NOTE_EMAIL, newEmail);
        getContentResolver().insert(NotesProvider.CONTENT_URI, values);
        setResult(RESULT_OK);
    }

    public void onBackPressed(){
        finishEditing();
    }
}
