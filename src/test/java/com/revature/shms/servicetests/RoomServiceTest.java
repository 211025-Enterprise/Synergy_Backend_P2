package com.revature.shms.servicetests;

import com.revature.shms.enums.Amenities;
import com.revature.shms.enums.CleaningStatus;
import com.revature.shms.enums.WorkStatus;
import com.revature.shms.models.AmenityWrapper;
import com.revature.shms.models.Room;
import com.revature.shms.repositories.RoomRepository;
import com.revature.shms.services.RoomService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {
	@Mock RoomRepository roomRepository;
	@InjectMocks RoomService roomService;

	@Test
	public void addRoomTest(){
		Room room = new Room();
		when(roomRepository.save(any())).thenReturn(room);
		Assertions.assertEquals(room, roomService.addRoom(room));
	}

	@Test
	public void addRoomsTest(){
		List<Room> rooms = new ArrayList<>();
		rooms.add(new Room());
		rooms.add(new Room());
		rooms.add(new Room());
		rooms.add(new Room());
		rooms.add(new Room());
		rooms.add(new Room());
		when(roomRepository.saveAll(any())).thenReturn(rooms);
		Assertions.assertEquals(rooms, roomService.addRooms(rooms));
	}

	@Test
	public void isAvailableTest(){
		Room room = new Room();
		room.setStatus(CleaningStatus.CLEAN);
		room.setWorkStatus(WorkStatus.NO_ISSUES);
		room.setOccupied(false);
		Assertions.assertTrue(roomService.isAvailable(room));
	}

	@Test
	public void findAllAvailableTest(){
		Room room = new Room();
		room.setStatus(CleaningStatus.CLEAN);
		room.setOccupied(false);
		room.setWorkStatus(WorkStatus.NO_ISSUES);
		List<Room> roomList = new ArrayList<>();
		roomList.add(room);
		roomList.add(room);

		when(roomRepository.findAllByStatusAndIsOccupiedAndWorkStatusOrderByRoomNumberDesc(any(), anyBoolean(), any())).thenReturn(roomList);
		Assertions.assertEquals(roomList, roomService.findAllAvailable());
	}

	@Test
	public void setOccupationStatusTest(){
		boolean isOccupied = true;
		Room room = new Room();
		room.setOccupied(isOccupied);
		Assertions.assertEquals(room, roomService.setOccupationStatus(room, isOccupied));
	}

	@Test
	public void getAllByIsOccupiedTest(){
		boolean testing = true;
		for(int i = 0; i < 2; i++){
			Room room = new Room();
			room.setOccupied(testing);
			List<Room> roomList = new ArrayList<>();
			roomList.add(room);
			roomList.add(room);
			when(roomRepository.findAllByIsOccupied(anyBoolean())).thenReturn(roomList);
			Assertions.assertEquals(roomList, roomService.findAllByIsOccupied(testing));
			testing = false;
		}
	}

	@Test
	public void OccupiedTest(){
		Assertions.assertTrue(roomService.Occupied(new Room()).isOccupied());
	}

	@Test
	public void notOccupiedTest(){
		Assertions.assertFalse(roomService.notOccupied(new Room()).isOccupied());
	}

	@Test
	public void setRoomStatusTest(){
		for (CleaningStatus cleaningStatus:CleaningStatus.values()) {
			Assertions.assertEquals(cleaningStatus,roomService.setRoomStatus(new Room(),cleaningStatus).getStatus());
		}
	}

	@Test
	public void getAllByStatusTest(){
		for(CleaningStatus cleaningStatus:CleaningStatus.values()) {
			Room room = new Room();
			room.setStatus(cleaningStatus);
			List<Room> roomList = new ArrayList<>();
			roomList.add(room);
			roomList.add(room);
			when(roomRepository.findAllByStatus(any(CleaningStatus.class))).thenReturn(roomList);
			Assertions.assertEquals(cleaningStatus, roomService.findAllByStatus(cleaningStatus).get(0).getStatus());
		}
	}

	@Test
	public void findAllByNotStatusTest(){
		for(CleaningStatus cleaningStatus:CleaningStatus.values()) {
			Room room = new Room();
			room.setStatus(cleaningStatus);
			List<Room> roomList = new ArrayList<>();
			roomList.add(room);
			roomList.add(room);
			when(roomRepository.findAllByStatusNot(any(CleaningStatus.class))).thenReturn(roomList);
			Assertions.assertEquals(cleaningStatus, roomService.findAllByNotStatus(cleaningStatus).get(0).getStatus());
		}
	}

	@Test
	public void scheduleCleaningTest(){
		Assertions.assertEquals(CleaningStatus.SCHEDULED,roomService.scheduleCleaning(new Room()).getStatus());
	}

	@Test
	public void notScheduleCleaningTest(){
		Assertions.assertEquals(CleaningStatus.NOT_SCHEDULED, roomService.notScheduleCleaning(new Room()).getStatus());
	}

	@Test
	public void startCleaningTest(){
		Assertions.assertEquals(CleaningStatus.IN_PROGRESS,roomService.startCleaning(new Room()).getStatus());
	}

	@Test
	public void finishCleaningTest(){
		Assertions.assertEquals(CleaningStatus.CLEAN, roomService.finishCleaning(new Room()).getStatus());
	}

	@Test
	public void setWorkStatusTest(){
		for(WorkStatus workStatus: WorkStatus.values()){
			Assertions.assertEquals(workStatus, roomService.setWorkStatus(new Room(), workStatus).getWorkStatus());
		}
	}

	@Test
	public void getAllByWorkStatusTest(){
		for(WorkStatus workStatus: WorkStatus.values()){
			Room room = new Room();
			room.setWorkStatus(workStatus);
			List<Room> roomList = new ArrayList<>();
			roomList.add(room);
			roomList.add(room);
			when(roomRepository.findAllByWorkStatus(any())).thenReturn(roomList);
			Assertions.assertEquals(roomList, roomService.findAllByWorkStatus(workStatus));
		}
	}

	@Test
	public void findAllByNotWorkStatusTest(){
		for(WorkStatus workStatus: WorkStatus.values()){
			Room room = new Room();
			room.setWorkStatus(workStatus);
			List<Room> roomList = new ArrayList<>();
			roomList.add(room);
			roomList.add(room);
			when(roomRepository.findAllByWorkStatusNot(any())).thenReturn(roomList);
			Assertions.assertEquals(roomList, roomService.findAllByNotWorkStatus(workStatus));
		}
	}

	@Test
	public void startWorkingTest(){
		Assertions.assertEquals(WorkStatus.IN_PROGRESS, roomService.startWorking(new Room()).getWorkStatus());
	}

	@Test
	public void finishWorkingTest(){
		Assertions.assertEquals(WorkStatus.NO_ISSUES, roomService.finishWorking(new Room()).getWorkStatus());
	}

	@Test
	public void scheduleWorkingTest(){
		Assertions.assertEquals(WorkStatus.SCHEDULED, roomService.scheduleWorking(new Room()).getWorkStatus());
	}

	@Test
	public void notScheduleWorkingTest(){
		Assertions.assertEquals(WorkStatus.NOT_SCHEDULED, roomService.notScheduleWorking(new Room()).getWorkStatus());
	}

	@Test
	public void getAllRoomsTest(){
		List<Room> roomList = new ArrayList<>();
		roomList.add(new Room());
		roomList.add(new Room());
		when(roomRepository.findAllByOrderByRoomNumberDesc()).thenReturn(roomList);
		Assertions.assertEquals(roomList, roomService.findAllRooms());
	}

	@Test
	public void getAllByAmenityTest(){
		for(Amenities amenities:Amenities.values()) {
			AmenityWrapper amenityWrapper = new AmenityWrapper();
			amenityWrapper.setAmenity(amenities);
			List<AmenityWrapper> amenityWrapperList = new ArrayList<>();
			amenityWrapperList.add(amenityWrapper);

			Room room = new Room();
			room.setAmenitiesList(amenityWrapperList);
			List<Room> roomList = new ArrayList<>();
			roomList.add(room);
			roomList.add(room);

			when(roomRepository.findAllByAmenitiesList_Amenity(amenities)).thenReturn(roomList);
			Assertions.assertEquals(roomList, roomService.findAllByAmenity(amenities));
		}
	}

	@Test
	public void getByRoomNumberTest() throws NotFound {
		int roomNumber = 102;
		Room room = new Room();
		room.setRoomNumber(roomNumber);
		when(roomRepository.findByRoomNumber(roomNumber)).thenReturn(java.util.Optional.of(room));
		Assertions.assertEquals(room, roomService.findByRoomNumber(roomNumber));
	}

	@Test
	public void getterAndSetterTest(){
		RoomRepository roomRepository = null;
		RoomService roomService = new RoomService();
		roomService.setRoomRepository(roomRepository);
		Assertions.assertNull(roomService.getRoomRepository());

		roomService.setRoomRepository(null);
		Assertions.assertNull(roomService.getRoomRepository());
	}
}
