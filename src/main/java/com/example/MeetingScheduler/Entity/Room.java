package com.example.MeetingScheduler.Entity;


import com.example.MeetingScheduler.Enum.RoomStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Room {
    private Integer roomId;
    private RoomStatus roomStatus;
    private Integer Floor;

    public Room(Integer id, Integer floorId){
        this.roomId = id;
        this.roomStatus = RoomStatus.AVAILABLE;
        this.Floor = floorId;
    }
}
