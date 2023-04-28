package com.example.MeetingScheduler;

import com.example.MeetingScheduler.Entity.Meeting;
import com.example.MeetingScheduler.Entity.Person;
import com.example.MeetingScheduler.Entity.Room;
import com.example.MeetingScheduler.Enum.RoomStatus;
import com.example.MeetingScheduler.Exception.RoomAlreadyBookedException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class MeetingSchedulerApplicationTests {

	@Test
	void testMeetingScheduling() {

		Person person1 = new Person("Aditya");
		Person person2 = new Person("Chandan");
		Person person3 = new Person("Adarsh");
		List<Person> personList = new ArrayList<>();
		personList.add(person3);
		personList.add(person2);
		personList.add(person1);
		Room room1 = new Room(1, 1);
		Room room2 = new Room(2, 1);
		Room room3 = new Room(3, 1);

		Scheduler.addRoom(room1);
		Scheduler.addRoom(room2);
		Scheduler.addRoom(room3);

		ExecutorService executorService = Executors.newFixedThreadPool(5);
		for(int i=0;i<5;i++){
			executorService.execute( ()-> {
				try{
					Scheduler.bookRoom(room1, person1,new Date(), new Date(), personList);
				}catch (RoomAlreadyBookedException ex){
					System.out.println(ex.getMessage());
				}
			});
		}

	}


	@Test
	void testMeetingSchedulingWithDifferentRoom() {

		Person person1 = new Person("Aditya");
		Person person2 = new Person("Chandan");
		Person person3 = new Person("Adarsh");
		List<Person> personList = new ArrayList<>();
		personList.add(person3);
		personList.add(person2);
		personList.add(person1);
		Room room1 = new Room(1, 1);
		Room room2 = new Room(2, 1);
		Room room3 = new Room(3, 1);

		Scheduler.addRoom(room1);
		Scheduler.addRoom(room2);
		Scheduler.addRoom(room3);



		CompletableFuture<Meeting> bookRoom1_1 = CompletableFuture.supplyAsync(()-> Scheduler.bookRoom(room1, person1,new Date(), new Date(), personList) );
		CompletableFuture<Meeting> bookRoom1_2 = CompletableFuture.supplyAsync(()-> Scheduler.bookRoom(room1, person2,new Date(), new Date(), personList) );
		CompletableFuture<Meeting> bookRoom1_3 = CompletableFuture.supplyAsync(()-> Scheduler.bookRoom(room2, person3,new Date(), new Date(), personList) );

		CompletableFuture <Void> all = CompletableFuture.allOf(bookRoom1_1, bookRoom1_3, bookRoom1_2);
		try {
			all.get();
		} catch (InterruptedException | ExecutionException | RoomAlreadyBookedException e) {
			System.out.println(e.getMessage());
		}

	}

}
