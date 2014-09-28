package com.ashleycortez.texteditor;

import java.util.List;

import com.ashleycortez.texteditor.data.NoteItem;
import com.ashleycortez.texteditor.data.NotesDataSource;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

//using ListActivity along with ListView in XML
public class MainActivity extends ListActivity {

	private NotesDataSource datasource;
	List<NoteItem> notesList;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        datasource= new NotesDataSource(this);
        
        refreshDisplay();

//        FOR TESTING:::
//        notes = datasource.findAll();
//        NoteItem note= notes.get(0);
//        //testing code
//        note.setText("Updated!");
//        	// save the newly added note object
//        datasource.update(note); 
//        
//        notes=datasource.findAll();
//        note=notes.get(0);
//        
//        Log.i("NOTES", note.getKey() +": "+ note.getText());
    }


    private void refreshDisplay() {
		notesList =datasource.findAll();
		//array adaptor class --- indicates how data should be displayed
		ArrayAdapter<NoteItem> adapter = 
				new ArrayAdapter<NoteItem>(this, R.layout.list_item_layout, notesList);
		setListAdapter(adapter);	
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
