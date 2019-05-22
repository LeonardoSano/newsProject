package com.nyTimes.model;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class NyList implements Serializable {

	private static final long serialVersionUID = UUID.randomUUID().getLeastSignificantBits();
	private String status;
	private String copyright;
	private String section;
	private String last_updated;
	private int num_results;
	private List<NyNews> results;

	
	
}
