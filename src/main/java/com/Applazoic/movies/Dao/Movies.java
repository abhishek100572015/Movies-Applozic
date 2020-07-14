package com.Applazoic.movies.Dao;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.Applazoic.movies.Dto.MovieEntry;
import com.Applazoic.movies.Service.MovieService;

@Component
public class Movies implements MovieService {

	@Override
	public String addMovieData(MovieEntry[] movieEntry) {

		return null;
	}

	@Override
	public ArrayList<MovieEntry> showMovies() {
		return null;
	}

}
