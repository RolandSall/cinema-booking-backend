package com.roland.movietheater_jdbc.controller.movieRating;

import com.roland.movietheater_jdbc.model.MovieRatingForm;
import com.roland.movietheater_jdbc.service.RatingMovieService.RatingMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MovieRatingController {

    private RatingMovieService ratingMovieService;

    @Autowired
    public  MovieRatingController(RatingMovieService ratingMovieService){
        this.ratingMovieService = ratingMovieService;

    }


    @GetMapping("admin/movies/{movieId}/ratings")
    public ResponseEntity getAllRatingForMovieForAdmin(@PathVariable("movieId") int movieId){
        List<MovieRatingForm> movieRatingFormList = ratingMovieService.findAllRatingForMovie(movieId);
        List<MovieRatingApiResponseForAdmin> responseList = buildResponseForAdmin(movieRatingFormList);
        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    @GetMapping("movies/{movieId}/ratings")
    public ResponseEntity getAllRatingForMovieForUser(@PathVariable("movieId") int movieId){
        List<MovieRatingForm> movieRatingFormList = ratingMovieService.findAllRatingForMovie(movieId);
        List<MovieRatingApiResponseForUser> responseList = buildResponseForUser(movieRatingFormList);
        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    @DeleteMapping("movies/{movieId}/ratings/{customerId}")
    public ResponseEntity deleteRatingForMovie(@PathVariable("movieId") int movieId, @PathVariable("customerId") int customerId){
        String ratingDeleted = ratingMovieService.deleteMovieRatingForMovie(movieId,customerId);
        return ResponseEntity.status(HttpStatus.OK).body(ratingDeleted);
    }



    private List<MovieRatingApiResponseForUser> buildResponseForUser(List<MovieRatingForm> movieRatingFormList) {
        List<MovieRatingApiResponseForUser> responseList = new ArrayList<>();
        for (MovieRatingForm cinemaBranch : movieRatingFormList) {
            responseList.add(getMovieRatingForUser(cinemaBranch));
        }
        return responseList;
    }

    private MovieRatingApiResponseForUser getMovieRatingForUser(MovieRatingForm movieRatingForm) {
        return new MovieRatingApiResponseForUser().builder()
                .movieId(movieRatingForm.getMovieId())
                .movieReviewComment(movieRatingForm.getMovieReviewComment())
                .movieReviewRating(movieRatingForm.getMovieReviewRating())
                .customerUserName(movieRatingForm.getCustomerUserName())
                .build();

    }

    private List<MovieRatingApiResponseForAdmin> buildResponseForAdmin(List<MovieRatingForm> movieRatingFormList) {
        List<MovieRatingApiResponseForAdmin> responseList = new ArrayList<>();
        for (MovieRatingForm cinemaBranch : movieRatingFormList) {
            responseList.add(getMovieRatingForAdmin(cinemaBranch));
        }
        return responseList;
    }

    private MovieRatingApiResponseForAdmin getMovieRatingForAdmin(MovieRatingForm movieRatingForm) {
        return new MovieRatingApiResponseForAdmin().builder()
                .movieId(movieRatingForm.getMovieId())
                .customerId(movieRatingForm.getCustomerId())
                .movieReviewComment(movieRatingForm.getMovieReviewComment())
                .movieReviewRating(movieRatingForm.getMovieReviewRating())
                .customerUserName(movieRatingForm.getCustomerUserName())
                .build();
    }
}