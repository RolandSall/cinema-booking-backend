package com.roland.movietheater_jdbc.service.BookingService;

import com.roland.movietheater_jdbc.model.CineMovieEvent;
import com.roland.movietheater_jdbc.model.CineMovieEventRoomSeat;
import com.roland.movietheater_jdbc.model.CineMovieEventRoomTiming;
import com.roland.movietheater_jdbc.model.Customer;
import com.roland.movietheater_jdbc.repository.BookingRepository.BookingRepository;
import com.roland.movietheater_jdbc.service.Customer.CustomerService;
import com.roland.movietheater_jdbc.service.Customer.FailedToFindAccountException;
import com.roland.movietheater_jdbc.service.TicketService.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService implements IBookingService {

    private BookingRepository bookingRepository;
    private CustomerService customerService;
    private TicketService ticketService;

    @Autowired
    public BookingService(BookingRepository bookingRepository, CustomerService customerService, TicketService ticketService) {
        this.bookingRepository = bookingRepository;
        this.customerService = customerService;
        this.ticketService = ticketService;
    }



    @Override
    public List<CineMovieEvent> getCinemaBranchHostingByMovieId(int movieId) {
        return bookingRepository.getCinemaBranchHostingByMovie(movieId);
    }

    @Override
    public List<CineMovieEventRoomTiming> getRoomTimingHostingMovieByMovieIdAndCinemaId(int movieId, int cinemaId) {
        return bookingRepository.getRoomTimingHostingMovieByMovieIdAndCinemaId(movieId, cinemaId);
    }

    @Override
    public List<CineMovieEventRoomSeat> getSeatAllSeatsForMovieEvent(int movieId, int cinemaId, int roomId) {
        return bookingRepository.getSeatAllSeatsForMovieEvent(movieId, cinemaId, roomId);
    }

    @Override
    public String reserveSeatForUser(int cinemaId, int roomId, int seatId, int userID, double ticketPrice) throws FailedToFindAccountException {
        if (isUser(userID)) {
            int tickedIdGenerated = ticketService.createTicket(userID,ticketPrice);
            return bookingRepository.reserveSeatForUser(cinemaId, roomId, seatId, userID);

        }

    else
        return " User Not Found ! Please Register before Booking ! ";

    }

    private boolean isUser(int userID) throws FailedToFindAccountException {
        Customer customer = customerService.getCustomerById(userID);
            if (customer.getCustomerUsername().equals(null))
                return false;
            else
                return true;

    }
}
