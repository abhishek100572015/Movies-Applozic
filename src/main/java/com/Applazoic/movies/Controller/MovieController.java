package com.Applazoic.movies.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.Applazoic.movies.Dto.MovieEntry;
import com.Applazoic.movies.Dto.OutputMovieSchedule;
import com.Applazoic.movies.Service.MovieService;

@RestController
public class MovieController {

	@Autowired
	MovieService movieService;

	@GetMapping("/test")
	public String test() {
		return "Abhishek";
	}

	@PostMapping("/addmovie")
	@ResponseBody
	public String addMovieData(@RequestBody MovieEntry[] movieEntry) {

		if (movieEntry != null) {
			return movieService.addMovieData(movieEntry);
		}
		return null;

	}

	@GetMapping("/showmovieschedule")
	public OutputMovieSchedule showMovies() {

		return movieService.showMovies();
	}

}
