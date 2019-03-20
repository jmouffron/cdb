package com.excilys.cdb.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAmount;

public class DateUtils {
  private static DateTimeFormatter timestampFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  
  private DateUtils(){
    
  }
  public static Timestamp getNowTimestamp() {
    LocalDateTime dateTime = LocalDateTime.now();
    
    String textDate = dateTime.format(timestampFormatter);
    
    LocalDateTime now = LocalDateTime.parse(textDate, timestampFormatter);
    
    return Timestamp.valueOf(now);
  }
  
  public static Timestamp getAfterNowTimestamp(TemporalAmount amountToAdd) {
    LocalDateTime dateTime = LocalDateTime.now().plus(amountToAdd);
    
    String textDate = dateTime.format(timestampFormatter);
    
    LocalDateTime now = LocalDateTime.parse(textDate, timestampFormatter);
    
    return Timestamp.valueOf(now);
  }
  public static Timestamp getBeforeNowTimestamp(TemporalAmount amountToTake) {
    LocalDateTime dateTime = LocalDateTime.now().minus(amountToTake);
    
    String textDate = dateTime.format(timestampFormatter);
    
    LocalDateTime now = LocalDateTime.parse(textDate, timestampFormatter);
    
    return Timestamp.valueOf(now);
  }
  
}
