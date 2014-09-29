package com.ashleycortez.texteditor;

import java.util.List;

import com.ashleycortez.texteditor.data.NoteItem;
import com.ashleycortez.texteditor.data.NotesDataSource;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;

//using ListActivity along with ListView in XML
public class MainActivity extends ListActivity {

	private static final int EDITOR_ACTIVITY_REQUEST = 1001;
	private static final int MENU_DELETE_ID= 1002;
	private int currentNoteID; //used to identify which context menu item has been selected b/w different functions
	private NotesDataSource datasource;
	List<NoteItem> notesList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//context menu request registration for holding a list item
		registerForContextMenu(getListView());

		datasource = new NotesDataSource(this);

		refreshDisplay();

		// FOR TESTING:::
		// notes = datasource.findAll();
		// NoteItem note= notes.get(0);
		// //testing code
		// note.setText("Updated!");
		// // save the newly added note object
		// datasource.update(note);
		//
		// notes=datasource.findAll();
		// note=notes.get(0);
		//
		// Log.i("NOTES", note.getKey() +": "+ note.getText());
	}

	private void refreshDisplay() {
		notesList = datasource.findAll();
		// array adaptor class --- indicates how data should be displayed
		ArrayAdapter<NoteItem> adapter = new ArrayAdapter<NoteItem>(this,
				R.layout.list_item_layout, notesList);
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
		if (id == R.id.action_create) {
			createNote();
		}
		return super.onOptionsItemSelected(item);
	}

	private void createNote() {
		NoteItem note = NoteItem.getNew();
		Intent intent = new Intent(this, NoteEditorActivity.class); // android
																	// instantiates
																	// the class
																	// for you
		// pass data to new activity
		intent.putExtra("key", note.getKey());
		intent.putExtra("text", note.getText());
		// switching screens
		startActivityForResult(intent, EDITOR_ACTIVITY_REQUEST);

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		// reference to the note object that the user selection ---from position
		// argument
		NoteItem note = notesList.get(position);
		// remaining is the same code from "createNote()"
		Intent intent = new Intent(this, NoteEditorActivity.class); // android
																	// instantiates
																	// the class
																	// for you
		// pass data to new activity
		intent.putExtra("key", note.getKey());
		intent.putExtra("text", note.getText());
		// switching screens
		startActivityForResult(intent, EDITOR_ACTIVITY_REQUEST);
	}

	// when editor activity returns to main activity...performs this:
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == EDITOR_ACTIVITY_REQUEST && resultCode == RESULT_OK) {
			NoteItem note = new NoteItem();
			note.setKey(data.getStringExtra("key"));
			note.setText(data.getStringExtra("text"));
			//save data to persistent storage
			datasource.update(note);
			//now that everything has been saved, re-gather information to be displayed
			refreshDisplay();
		}

	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		
		//find out which note the user wants to delete
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		currentNoteID = (int) info.id; //must cast to int because the info.id is stored as a long
		
		//add an item to the menu (groupID, ID constant, order menu item should be listed, string for display) 
		menu.add(0,MENU_DELETE_ID, 0, "Delete");
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		if(item.getItemId() == MENU_DELETE_ID) {
			NoteItem note = notesList.get(currentNoteID);
			datasource.remove(note);
			refreshDisplay();
		}
		
		return super.onContextItemSelected(item);
	}
}
