package com.ashleycortez.texteditor.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.annotation.SuppressLint;

public class NoteItem {

	private String key;
	private String text;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	// End getters and setters
	
	@SuppressLint("SimpleDateFormat")
	public static NoteItem getNew(){
		
		Locale locale = new Locale("en_US"); //easily sortable timestamp type -- en_US
		Locale.setDefault(locale);
		
		String pattern = "yyyy-MM-dd HH:mm:ss Z"; //datetime stamp e.g. 1989-02-14 12:20:32 -9
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		String key = formatter.format(new Date());
		
		NoteItem note = new NoteItem();
		note.setKey(key);
		note.setText("");
		return note;	
	}
	
	@Override
	public String toString() {
		return this.getText();
	}
	
}
