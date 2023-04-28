package com.example.MeetingScheduler.Entity;

import com.example.MeetingScheduler.Enum.MeetingStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.Inet4Address;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Setter@Getter
@NoArgsConstructor
public class Meeting {
    private UUID id;
    private Person bookedBy;
    private Date startTime;
    private Date endTime;
    private Room room;
    List<Person> memberList;
    private MeetingStatus meetingStatus;

    public Meeting(Person bookedBy, Date startTime, Date endDate, List<Person> memberList, Room room){
        this.id = UUID.randomUUID();
        this.bookedBy = bookedBy;
        this.memberList = memberList;
        this.startTime = startTime;
        this.endTime = endDate;
        this.room = room;
        this.meetingStatus= MeetingStatus.INITIATED;
    }
}
