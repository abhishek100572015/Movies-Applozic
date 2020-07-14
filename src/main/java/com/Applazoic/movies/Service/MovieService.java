package com.Applazoic.movies.Service;

import java.util.ArrayList;

import com.Applazoic.movies.Dto.MovieEntry;

public interface MovieService {

	public String addMovieData(MovieEntry[] movieEntry);

	public ArrayList<MovieEntry> showMovies();

}
