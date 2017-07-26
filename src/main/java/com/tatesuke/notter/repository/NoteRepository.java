package com.tatesuke.notter.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tatesuke.notter.model.Note;


public interface NoteRepository extends JpaRepository<Note, Long> {

	List<Note> findByCreateDateBetween(Date start, Date end);

	List<Note> findByIdIn(List<Long> idList);

	List<Note> findByReplyFrom(Note note);
	
}

