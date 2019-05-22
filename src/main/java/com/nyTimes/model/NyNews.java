package com.nyTimes.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class NyNews extends News implements Serializable {

	private static final long serialVersionUID = UUID.randomUUID().getLeastSignificantBits();
	private Date published_date;
	private String section;
	private String subsection;
	private String title;
	private String url;
	private String byline;
	private String item_type;
	private String updated_date;
	private String created_date;
	private String material_type_facet;
	private String kicker;
	private String short_url;

	@Override
	public Date getData() {
		return this.published_date;
	}

}
