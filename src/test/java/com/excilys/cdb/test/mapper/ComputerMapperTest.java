package com.excilys.cdb.test.mapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.excilys.cdb.mapper.ComputerMapper;

class ComputerMapperTest {

	private static ResultSet rs;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		rs = null;
	}

//	@Test
//	void givenGoodInput_whenMappingComputer_shouldSucceed() {
//		try {
//			rs = mock(ResultSet.class);
//			
//			LocalDateTime dateTime = LocalDateTime.now().minusDays(7);
//			LocalDateTime dateTimeMinus = dateTime.minusDays(7);
//			
//			lenient().when(rs.getLong(anyInt())).thenReturn(1L).thenReturn(2L);
//			lenient().when(rs.getString(anyString())).thenReturn("Mac In Tosh").thenReturn("Mike Ro Soft");
//			lenient().when(rs.getTimestamp(anyString())).thenReturn(Timestamp.valueOf(dateTimeMinus))
//					.thenReturn(Timestamp.valueOf(dateTime));
//
//			assertNotNull(ComputerMapper.map(rs));
//		} catch (SQLException e) {
//			fail(e.getMessage());
//		}
//	}

//	@Test
//	void givenBadInput_whenMappingComputer_shouldFail() {
//		try {
//			rs = mock(ResultSet.class);
//			LocalDateTime dateTime = LocalDateTime.now().minusDays(7);
//			LocalDateTime dateTimeMinus = dateTime.minusDays(7);
//
//			lenient().when(rs.getLong(anyString())).thenReturn(-1L).thenReturn(-2L);
//			lenient().when(rs.getString(anyString())).thenReturn("").thenReturn(null);
//			lenient().when(rs.getTimestamp(anyString())).thenReturn(Timestamp.valueOf(dateTime))
//					.thenReturn(Timestamp.valueOf(dateTimeMinus));
//			
//			assertNull(ComputerMapper.map(rs));
//			fail("Computer shouldn't be mapped properly!");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
}
