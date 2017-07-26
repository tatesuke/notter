package com.tatesuke.notter.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tatesuke.notter.model.Note;
import com.tatesuke.notter.model.Tag;
import com.tatesuke.notter.repository.NoteRepository;
import com.tatesuke.notter.repository.TagRepository;

@Service
public class NoteServiceImpl implements NoteService {
	
	@Autowired
	private NoteRepository noteRepository;
	
	@Autowired
	private TagRepository tagRepository;
	
	@Override
	@Transactional
	public List<Note> add(String text, String tags) {
		List<Note> result = new ArrayList<>();
		
		Date now = new Date();
		
		Note note = new Note();
		note.setText(text);
		note.setTags(createTagList(tags));
		note.setCreateDate(now);
		noteRepository.save(note);
		result.add(note);
		
		result.addAll(setReplyFrom(text, note));
		
		return result;
	}

	private List<Tag> createTagList(String tags) {
		List<Tag> tagList = new ArrayList<>();
		String[] tagArray = tags.split("[ 　]+");
		for (String tagStr : tagArray) {
			if (tagStr.trim().isEmpty()) {
				continue;
			}
			Tag tag = tagRepository.findByText(tagStr);
			if (tag == null) {
				tag = new Tag();
				tag.setText(tagStr);
				tagRepository.save(tag);
			}
			tagList.add(tag);
		}
		return tagList;
	}

	private List<Note> setReplyFrom(String text, Note note) {
		List<Long> idList = new ArrayList<>();
		
		Pattern pattern = Pattern.compile("\\[[1-9]+?[0-9]*?\\]");
		Matcher m = pattern.matcher(text);
		while (m.find()) {
			String idStr = m.group().replaceAll("[\\[\\]]", "");
			idList.add(Long.parseLong(idStr));
		}
		
		List<Note> targetNotes = noteRepository.findByIdIn(idList);
		for (Note targetNote : targetNotes) {
			List<Note> replyFrom = targetNote.getReplyFrom();
			if (replyFrom == null) {
				replyFrom = new ArrayList<>();
			}
			replyFrom.add(note);
			targetNote.setReplyFrom(replyFrom);
		}
		noteRepository.save(targetNotes);
		
		return targetNotes;
	}
	
	@Override
	public List<Note> list() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime start = LocalDateTime.now().minusDays(30).truncatedTo(ChronoUnit.DAYS);
		
		Date nowDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
		Date startDate = Date.from(start.atZone(ZoneId.systemDefault()).toInstant());
		
		return noteRepository.findByCreateDateBetween(startDate, nowDate);
	}

	@Override
	@Transactional
	public List<Note> remove(Note note) {
		List<Note> affecteds = noteRepository.findByReplyFrom(note);
		for (Note affected : affecteds) {
			affected.getReplyFrom().remove(note);
		}
		
		noteRepository.delete(note);
		
		return affecteds;
	}

	@Override
	public List<Note> update(Note note) {
		noteRepository.save(note);
		return Arrays.asList(new Note[]{note});
	}

}
