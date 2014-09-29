package com.ashleycortez.texteditor;

import com.ashleycortez.texteditor.data.NoteItem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

public class NoteEditorActivity extends Activity {

	private NoteItem note;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_editor);
		//create back button in action bar using the app icon
		//turns the launcher icon into an options button w/ id of "home"
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		//reconstruct text object in the new screen
		Intent intent = this.getIntent();
		note = new NoteItem();
		note.setKey(intent.getStringExtra("key"));
		note.setText(intent.getStringExtra("text"));
		
		//
		EditText et= (EditText) findViewById(R.id.noteText);
		et.setText(note.getText());
		
		//place cursor at the end of existing code
		et.setSelection(note.getText().length());
	}
	
	//returning to the previous activity is called "finishing"
	private void saveAndFinish(){
		EditText et= (EditText) findViewById(R.id.noteText);
		String noteText= et.getText().toString();
		
		//package to send back to main activity
		Intent intent = new Intent();
		intent.putExtra("key", note.getKey());
		intent.putExtra("text", noteText);
		
		//save this data
		setResult(RESULT_OK, intent);
		finish();
	}
	
	//code to add button functionality to the launcher icon in action bar
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home){
			saveAndFinish();
		}
		return false;
	}
	
	//also add back button functionality to physical device back button
	@Override
	public void onBackPressed() {
		saveAndFinish();
	}
	
}
