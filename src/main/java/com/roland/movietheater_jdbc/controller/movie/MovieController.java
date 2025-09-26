package com.roland.movietheater_jdbc.controller.movie;

import com.roland.movietheater_jdbc.model.Movie;
import com.roland.movietheater_jdbc.service.MovieService.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.origin.SystemEnvironmentOrigin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MovieController {

    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/admin/movies")
    public ResponseEntity<List<MovieApiResponseForAdmin>> getAllMovieForAdmin() {
        List<Movie> movieList = movieService.findAllMovies();
        List<MovieApiResponseForAdmin> responseList = buildResponse(movieList);
        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    @GetMapping("/admin/movies/{movieId}")
    public ResponseEntity getMovieById(@PathVariable("movieId") int movieId) {
        try {
            Movie movieById = movieService.findMovieById(movieId);
            MovieApiResponseForAdmin responseList = getMovieApiResponse(movieById);
            return ResponseEntity.status(HttpStatus.OK).body(responseList);
        } catch (FailedToFindMovieExcpetion e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getLocalizedMessage());
        }
    }

    @GetMapping("/movies/{search}")
    public ResponseEntity searchForMovie(@PathVariable("search") String search){
        List<Movie> movieList = movieService.SearchForMovie(search);
        List<MovieApiResponseForAdmin> responseList = buildResponse(movieList);
        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    @DeleteMapping("/admin/movies/{movieId}")
    public ResponseEntity DeleteMovieInCinemaBranch(@PathVariable("movieId") int movieId) {
        try {
            int movieIdDeleted = movieService.deleteMovieInBranch(movieId);
            return ResponseEntity.status(HttpStatus.OK).body(movieIdDeleted);
        } catch (FailedToDeleteMovieException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/admin/movies/{movieId}")
    public ResponseEntity updateMovieInCinemaBranch(@PathVariable("movieId") int movieId, @RequestBody MovieApiRequestForAdmin request) {
        try {
            Movie movie = movieService.updateMovieInBranch(movieId, getMovie(request));
            MovieApiResponseForAdmin response = getMovieApiResponse(movie);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (FailedToUpdateMovieException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/admin/movies")
    public ResponseEntity createMovieForCinemaBranch(@RequestBody MovieApiRequestForAdmin request) {
        try {
            Movie movie = movieService.createMovieForCinemaBranch(getMovie(request));
            MovieApiResponseForAdmin response = getMovieApiResponse(movie);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (FailedToInsertMovieException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    private Movie getMovie(MovieApiRequestForAdmin request) {
        return new Movie().builder()
                .movieName(request.getMovieName())
                .movieDescription(request.getMovieDescription())
                .movieDuration(request.getMovieDuration())
                .movieGenre(request.getMovieGenre())
                .movieReleaseDate(request.getMovieReleaseDate())
                .movieDirectors(request.getMovieDirectors())
                .movieStars(request.getMovieStars())
                .movieUrlImage(request.getMovieUrlImage())
                .movieUrlPosterImage(request.getMovieUrlPosterImage())
                .movieRating(request.getMovieRating())  // <-- Added movieRating here
                .build();
    }

    private List<MovieApiResponseForAdmin> buildResponse(List<Movie> movieList) {
        List<MovieApiResponseForAdmin> responseList = new ArrayList<>();
        for (Movie movie : movieList) {
            responseList.add(getMovieApiResponse(movie));
        }
        return responseList;
    }

    private MovieApiResponseForAdmin getMovieApiResponse(Movie movie) {
        return new MovieApiResponseForAdmin().builder()
                .movieId(movie.getMovieId())
                .movieName(movie.getMovieName())
                .movieDescription(movie.getMovieDescription())
                .movieDuration(movie.getMovieDuration())
                .movieGenre(movie.getMovieGenre())
                .movieDirectors(movie.getMovieDirectors())
                .movieStars(movie.getMovieStars())
                .movieUrlImage(movie.getMovieUrlImage())
                .movieUrlPosterImage(movie.getMovieUrlPosterImage())
                .movieReleaseDate(movie.getMovieReleaseDate())
                .movieRating(movie.getMovieRating())  // <-- Added movieRating here
                .build();
    }
}
