package com.tatesuke.notter.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tatesuke.notter.model.Note;
import com.tatesuke.notter.service.NoteService;

@RestController
@RequestMapping("/note")
public class NoteController {

	@Autowired
	private NoteService noteService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public List<Note> add(@RequestBody NewNote newNote) {
		return noteService.add(newNote.text, newNote.tags);
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public List<Note> list() {
		return noteService.list();
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public List<Note> remove(@RequestBody Note note) {
		return noteService.remove(note);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public List<Note> update(@RequestBody Note note) {
		return noteService.update(note);
	}

	private static class NewNote {
		String text;
		String tags;

		@SuppressWarnings("unused")
		public String getText() {
			return text;
		}
		@SuppressWarnings("unused")
		public void setText(String text) {
			this.text = text;
		}
		@SuppressWarnings("unused")
		public String getTags() {
			return tags;
		}
		@SuppressWarnings("unused")
		public void setTags(String tags) {
			this.tags = tags;
		}
	}

}