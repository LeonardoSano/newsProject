package com.nyTimes.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Entity
public abstract class News implements Comparable<News> , Serializable {

	private static final long serialVersionUID = UUID.randomUUID().getLeastSignificantBits();
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long newsId;
	private String title;
	private String url;
	private Date data;

	abstract Date getData();

	@Override
	public int compareTo(News b) {

		return -this.getData().compareTo(b.getData());

	}

}
