package com.Applazoic.movies.Dao;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.Applazoic.movies.Constants.MovieConstants;
import com.Applazoic.movies.Dto.MovieDates;
import com.Applazoic.movies.Dto.MovieEntry;
import com.Applazoic.movies.Dto.OutputMovieSchedule;
import com.Applazoic.movies.Exceptions.InvalidRequestFormat;
import com.Applazoic.movies.Exceptions.MovieAlreadyPresent;
import com.Applazoic.movies.Service.MovieService;

@Component
public class Movies implements MovieService {

	HashMap<String, MovieDates> movies = new HashMap<String, MovieDates>();
	ArrayList<MovieEntry> moviesVector = new ArrayList<MovieEntry>();

	String monthNames[] = new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov",
			"Dec" };
	int numberOfDaysForMonths[] = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	private String calcMonth(String monthName) {
		String monthNumeric;
		for (int i = 0; i < 12; i++) {
			if (monthNames[i].equalsIgnoreCase(monthName)) {
				monthNumeric = String.valueOf(i + 1);
				monthNumeric = monthNumeric.length() < 2 ? "0" + monthNumeric : monthNumeric;
				return monthNumeric;
			}
		}
		return null;
	}

	// This method calculates the hash on basis of month and date.The hash
	// calculated is such that
	// for 28 Jan the hash becomes 0128 i.e Month concatenated with date.
	private String calculateHash(String date, String movieName) {

		if (areAllParametersPresentInRequest(date, movieName)) {

			if (movieName.trim().length() != 0) {
				String[] splitDate = date.trim().split(MovieConstants.SpaceChar);

				if (splitDate.length == 2) {
					// Any date can't have more than one space
					String dateVal = splitDate[0].length() < 2 ? "0" + splitDate[0] : splitDate[0];
					String monthInNum = calcMonth(splitDate[1]);

					if (monthInNum != null && !IsdateInValid(dateVal, monthInNum)) {
						String hashDate = monthInNum + dateVal;
						return hashDate;
					}
				}
				throw new InvalidRequestFormat(MovieConstants.INVALID_DATE_FORMAT_ERROR, movieName);
			}
			throw new InvalidRequestFormat(MovieConstants.MOVIE_NAME_CANT_BE_EMPTY);
		}
		throw new InvalidRequestFormat(MovieConstants.PARAMETER_KEY_NOT_APPROPRIAITE);

	}

	private boolean areAllParametersPresentInRequest(String date, String movieName) {
		return date != null && (movieName != null);
	}

	private boolean IsdateInValid(String date, String monthInNum) {

		try {
			int month = Integer.parseInt(monthInNum);
			int day = Integer.parseInt(date);
			if (day > numberOfDaysForMonths[month - 1] || day <= 0) {
				return true;
			}
			return false;
		} catch (Exception exception) {
			exception.printStackTrace();
			return true;
		}

	}

	private ArrayList<MovieEntry> findMoviesToDo(ArrayList<MovieEntry> moviesList) {

		if (moviesList.size() == 0) {
			return new ArrayList<MovieEntry>();
		}

		ArrayList<MovieEntry> ans = new ArrayList<MovieEntry>();

		String firstMovieToDo = moviesList.get(0).getMovieName();
		ans.add(new MovieEntry(firstMovieToDo, movies.get(firstMovieToDo)));

		int indexOfLastMovie = 0;
		for (int i = 1; i < moviesList.size(); i++) {
			if (isMoviePossible(moviesList.get(i), moviesList.get(indexOfLastMovie))) {
				String movieName = moviesList.get(i).getMovieName();
				ans.add(new MovieEntry(movieName, movies.get(movieName)));
				indexOfLastMovie = i;

			}
		}
		return ans;
	}

	private boolean isMoviePossible(MovieEntry newMovie, MovieEntry prevMovie) {

		return (newMovie.getStartDate().compareTo(prevMovie.getEndDate()) > 0);
	}

	private void revertAllChanges(int index) {
		int movieSize = moviesVector.size();

		for (int i = movieSize - 1; i > movieSize - 1 - index; i--) {
			movies.remove(moviesVector.get(i).getMovieName());
			moviesVector.remove(i);
		}

	}

	@Override
	public String addMovieData(MovieEntry[] movieEntry) {

		for (int i = 0; i < movieEntry.length; i++) {
			String movieName = movieEntry[i].getMovieName();
			String startDate = movieEntry[i].getStartDate();
			String endDate = movieEntry[i].getEndDate();

			String hashStartDate = calculateHash(startDate, movieName);
			String hashEndDate = calculateHash(endDate, movieName);

			if (movies.containsKey(movieName)) {
				revertAllChanges(i);
				throw new MovieAlreadyPresent(MovieConstants.MOVIE_ALREADY_PRESENT_ERROR);
			}

			if (areAllParametersPresentInRequest(hashStartDate, hashEndDate)
					&& !isEndDateLessThanStart(hashStartDate, hashEndDate)) {
				movies.put(movieName, new MovieDates(startDate, endDate));
				moviesVector.add(new MovieEntry(movieName, hashStartDate, hashEndDate));
			} else {
				revertAllChanges(i);
				throw new InvalidRequestFormat(MovieConstants.END_DATE_LESS_THAN_START_DATE, movieName);
			}

		}
		moviesVector.sort(new Comparator<MovieEntry>() {
			@Override
			public int compare(MovieEntry movie1, MovieEntry movie2) {
				return movie1.getEndDate().compareTo(movie2.getEndDate());
			}
		});

		return null;
	}

	private boolean isEndDateLessThanStart(String hashStartDate, String hashEndDate) {
		return hashEndDate.compareTo(hashStartDate) < 0;
	}

	@Override
	public OutputMovieSchedule showMovies() {
		ArrayList<MovieEntry> moviesDetails = new ArrayList<MovieEntry>();
		moviesDetails = findMoviesToDo(moviesVector);
		return new OutputMovieSchedule(moviesDetails.size(), moviesDetails);
	}

}
