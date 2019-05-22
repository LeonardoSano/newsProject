package com.nyTimes.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class HackerNews extends News implements Serializable {

	private static final long serialVersionUID = UUID.randomUUID().getLeastSignificantBits();
	private String by;
	private float descendants;
	private float id;
	List<Integer> kids;
	private float score;
	private String title;
	private String type;
	private String url;
	private int time;

	@Override
	public Date getData() {
		return new Date(time);
	}

}
