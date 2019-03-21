package com.excilys.cdb.test.mapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.excilys.cdb.mapper.CompanyMapper;

class CompanyMapperTest {

	private static ResultSet rs;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		rs = mock(ResultSet.class);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		rs = null;
	}

	@Test
	void givenGoodInput_whenMappingComputer_shouldSucceed() {
		try {

			lenient().when( rs.first() ).thenReturn(true);
			lenient().when( rs.next() ).thenReturn(true).thenReturn(true);
			lenient().when( rs.getLong( anyInt() ) ).thenReturn(1L).thenReturn(2L);
			lenient().when( rs.getString( anyString() ) ).thenReturn("Apple Corp.").thenReturn("Microsoft Corp.");

			assertNotNull(CompanyMapper.map(rs));
		} catch (SQLException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void givenBadInput_whenMappingCompany_shouldFail() {
		try {
			rs = mock(ResultSet.class);

			lenient().when( rs.getLong( anyInt() ) ).thenReturn(-2L);
			lenient().when( rs.getString( anyString() ) ).thenReturn("");

			assertNull(CompanyMapper.map(rs));
			fail("Company shouldn't be mapped properly!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
