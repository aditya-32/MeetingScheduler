package com.example.MeetingScheduler.Exception;

public class RoomAlreadyBookedException extends RuntimeException{
    public RoomAlreadyBookedException(String msg){
        super(msg);
    }
}
