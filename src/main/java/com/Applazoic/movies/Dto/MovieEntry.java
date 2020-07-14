package com.Applazoic.movies.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MovieEntry {

	@JsonProperty("moviename")
	private String movieName;

	@JsonProperty("startdate")
	private String startDate;

	@JsonProperty("enddate")
	private String endDate;

	public MovieEntry(String movieName, String startDate, String endDate) {
		super();
		this.movieName = movieName;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "MovieEntry [movieName=" + movieName + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getMovieName() {
		return movieName;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public MovieEntry() {
		// TODO Auto-generated constructor stub
	}

	public MovieEntry(String movieName, MovieDates movieDates) {
		super();
		this.movieName = movieName;
		this.startDate = movieDates.getStartDate();
		this.endDate = movieDates.getEndDate();
	}

}
