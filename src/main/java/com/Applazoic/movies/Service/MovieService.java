package com.Applazoic.movies.Service;

import com.Applazoic.movies.Dto.MovieEntry;
import com.Applazoic.movies.Dto.OutputMovieSchedule;

public interface MovieService {

	public String addMovieData(MovieEntry[] movieEntry);

	public OutputMovieSchedule showMovies();

}
