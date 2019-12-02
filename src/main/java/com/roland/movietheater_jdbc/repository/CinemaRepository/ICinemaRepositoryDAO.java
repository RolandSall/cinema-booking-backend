package com.roland.movietheater_jdbc.repository.CinemaRepository;

import com.roland.movietheater_jdbc.model.CinemaBranch;
import com.roland.movietheater_jdbc.service.CinemaService.FailedToDeleteCinemaException;
import com.roland.movietheater_jdbc.service.CinemaService.FailedToInsertCinemaException;
import com.roland.movietheater_jdbc.service.CinemaService.FailedToUpdateCinemaException;

import java.util.List;

public interface ICinemaRepositoryDAO {

    CinemaBranch createCinemaBranch(CinemaBranch cinemaBranch) throws FailedToInsertCinemaException;

    int deleteCinemaBranch(int cinemaId) throws FailedToDeleteCinemaException;

    List<CinemaBranch> findAllCinemasBranch();

    CinemaBranch updateCinemaBranch(int id, CinemaBranch cinemaId) throws FailedToUpdateCinemaException;
}
