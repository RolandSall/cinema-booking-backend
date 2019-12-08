package com.roland.movietheater_jdbc.controller.movieEvent;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieEventApiResponseForUserAndAdmin {
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date movieStartTime;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date movieEndTime;
    private int movieEventId;
    private int roomId;
    private int movieId;
}
