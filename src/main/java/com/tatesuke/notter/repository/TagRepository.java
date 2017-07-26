package com.tatesuke.notter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tatesuke.notter.model.Tag;


public interface TagRepository  extends JpaRepository<Tag, Long>{

	Tag findByText(String tagStr);

}
