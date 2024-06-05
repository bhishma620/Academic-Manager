package com.bhishma.ams.response.attendance;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

@Data
public class StudentsAttendanceResponse implements Serializable {

  private int totalClass;
  private List<PresentStatusResponse> presentStatusResponseList;
  private HttpStatus statusCode;
}
