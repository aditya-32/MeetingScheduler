package com.example.MeetingScheduler;

import com.example.MeetingScheduler.Entity.Meeting;
import com.example.MeetingScheduler.Entity.Person;
import com.example.MeetingScheduler.Entity.Room;
import com.example.MeetingScheduler.Enum.MeetingStatus;
import com.example.MeetingScheduler.Enum.RoomStatus;
import com.example.MeetingScheduler.Exception.NoSuchRoomExistedException;
import com.example.MeetingScheduler.Exception.RoomAlreadyBookedException;
import lombok.extern.slf4j.Slf4j;
import sun.lwawt.macosx.CThreading;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Slf4j
public class Scheduler {

    private static final List<Room> rooms = new ArrayList<>();
    private static final ConcurrentHashMap<Room, Meeting> bookedRoom = new ConcurrentHashMap<>();

    private static final Object lock = new Object();

    public Scheduler(){

    }

    public static Room addRoom(Room room){
        rooms.add(room);
        return room;
    }

    public static Meeting bookRoom(Room toBeBookedRoom, Person person, Date startTime, Date endTime, List<Person> memberList){
        Meeting meeting;
        Room selectedRoom = null;
        for(Room room : rooms){
            if(room.equals(toBeBookedRoom)){
                selectedRoom = room;
                break;
            }
        }
        if(Objects.isNull(selectedRoom)){
            throw new NoSuchRoomExistedException("No room with Id "+ toBeBookedRoom.getRoomId());
        }
        //log.info("room :{}", selectedRoom);
        synchronized (selectedRoom){
            //System.out.println("inside Critical area "+ Thread.currentThread().getName());
            if(bookedRoom.containsKey(selectedRoom)){
                throw new RoomAlreadyBookedException(Thread.currentThread().getName() +" Room "+selectedRoom.getRoomId()+" Already Booked");
            }
            meeting = new Meeting(person,  startTime ,endTime, memberList,  selectedRoom);
            selectedRoom.setRoomStatus(RoomStatus.UN_AVAILABLE);
            meeting.setMeetingStatus(MeetingStatus.ACTIVE);
            System.out.println(Thread.currentThread().getName()+ " Room booked successfully "+ selectedRoom.getRoomId());
            bookedRoom.put(selectedRoom, meeting);
            //System.out.println("GoingOut Critical area "+ Thread.currentThread().getName());
        }
        return meeting;
    }

    public static List<Room> getAllAvailableRoom(){
        return rooms.stream().filter(room -> RoomStatus.AVAILABLE.equals(room.getRoomStatus())).collect(Collectors.toList());
    }
}
