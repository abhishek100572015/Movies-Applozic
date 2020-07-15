package com.Applazoic.movies.Dto;

import java.util.ArrayList;

import com.Applazoic.movies.Constants.MovieConstants;

public class OutputMovieSchedule {

	String totalMoney;
	ArrayList<MovieEntry> finalListOfMovies;

	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}

	public void setFinalListOfMovies(ArrayList<MovieEntry> finalListOfMovies) {
		this.finalListOfMovies = finalListOfMovies;
	}

	public String getTotalMoney() {
		return totalMoney;
	}

	public ArrayList<MovieEntry> getFinalListOfMovies() {
		return finalListOfMovies;
	}

	public OutputMovieSchedule(int moneyEarned, ArrayList<MovieEntry> finalListOfMovies) {
		super();
		totalMoney = MovieConstants.TOTAL_MONEY_FOR_YEAR + moneyEarned + MovieConstants.CRORES;
		this.finalListOfMovies = finalListOfMovies;
	}

}
