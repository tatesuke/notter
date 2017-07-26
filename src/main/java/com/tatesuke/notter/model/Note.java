package com.tatesuke.notter.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Note {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private String text;
	
	@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	private List<Tag> tags = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.EAGER)
	private List<Note> replyFrom = new ArrayList<>();

	private Date createDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public List<Note> getReplyFrom() {
		return replyFrom;
	}

	public void setReplyFrom(List<Note> replyFrom) {
		this.replyFrom = replyFrom;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
