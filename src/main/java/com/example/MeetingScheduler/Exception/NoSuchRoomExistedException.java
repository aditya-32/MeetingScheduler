package com.example.MeetingScheduler.Exception;

public class NoSuchRoomExistedException extends RuntimeException {
    public NoSuchRoomExistedException(String msg){
        super(msg);
    }
}
