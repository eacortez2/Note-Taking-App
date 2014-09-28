package com.ashleycortez.texteditor.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import android.content.Context;
import android.content.SharedPreferences;

public class NotesDataSource {

	private static final String PREFKEY = "notes"; // basically a constant
	SharedPreferences notePreferences;

	public NotesDataSource(Context context) { // "Context" is the superclass of
												// an Activity
		notePreferences = context.getSharedPreferences(PREFKEY,
				Context.MODE_PRIVATE); // "Context.MODE_PRIVATE" is a constant
										// that is a member of the Context
										// class.
		// As the datasource object is instantiated, the sharedpreferences will
		// be instantiated too.
	}

	public List<NoteItem> findAll() {

		/*"Map" is an unordered data collection - w/ key and value
		 * Each item will have a string based key
		 * the second item is a ? because it doesn't need to know what kind of data will be returned
		*/
		Map<String, ?> notesMap= notePreferences.getAll();
		
		/*sort the data by the key --which is a date
		 * The keyset returns a listing of all the keys of all the notes from sharedpreferences
		 * but in any order. Then the treeset auto sorts the data and returns it in
		 * a sorted set. Keys is now sorted from oldest to newest from an alphanumeric sort.
		*/
		SortedSet<String> keys= new TreeSet<String>(notesMap.keySet());
		
		List<NoteItem> noteList = new ArrayList<NoteItem>();
		for(String key:keys){
			NoteItem note = new NoteItem();
			note.setKey(key);
			note.setText((String) notesMap.get(key));
			noteList.add(note);
		}
		return noteList;
	}

	public boolean update(NoteItem note) {

		SharedPreferences.Editor editor = notePreferences.edit(); 															
		editor.putString(note.getKey(), note.getText());
		editor.commit(); // commit changes.. aka SAVE
		return true;
	}

	public boolean remove(NoteItem note) {

		if (notePreferences.contains(note.getKey())) {
			SharedPreferences.Editor editor = notePreferences.edit(); 															
			editor.remove(note.getKey());
			editor.commit(); // commit changes.. aka SAVE
		}
		return true;
	}
}
