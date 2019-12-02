package com.roland.movietheater_jdbc.service.StaffService;

import com.roland.movietheater_jdbc.model.Staff;

import java.util.List;

public interface IStaffService {
    List<Staff> findAllStaffInBranch(int cinemaId);

    Staff createStaffInCinemaBranch(int cinemaId, Staff staff) throws FailedToCreateStaffInCinemaBranch;

    String deleteStaffInCinemaBranch(int cinemaId, int staffId) throws FailedToDeleteStaffInCinemaBranch;
}
