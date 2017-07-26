package com.tatesuke.notter.service;

import java.util.List;

import com.tatesuke.notter.model.Note;

public interface NoteService {

	List<Note>add(String text, String tags);

	List<Note> list();

	List<Note> remove(Note note);

	List<Note> update(Note note);

}
