package com.example.MeetingScheduler.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
public class Floor {
    private  Integer floorId;
    private List<Room>  rooms;

    public Floor(Integer id){
        this.floorId=id;
        this.rooms = new ArrayList<>();
    }
}
