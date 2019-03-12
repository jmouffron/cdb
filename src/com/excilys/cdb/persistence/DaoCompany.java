package com.excilys.cdb.persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.excilys.cdb.model.Entity;

public class DaoCompany extends DaoInstance {
	private final String SELECT_ALL = "Select * from company";
	
	@Override
	Connection getConnection() {
		Properties props = new Properties();
		try {
			props.load(new FileInputStream("jdbc.properties"));
		} catch(FileNotFoundException e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		this.driver = props.getProperty("jdbc.driver");
		this.url = props.getProperty("jdc.url");
		this.username = props.getProperty("jdbc.username");
		
		//Récupération du driver dans le contexte
		try {
			Class.forName(this.driver);
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} 
		
		//Récupération de la connexion
		try{
			this.conn = DriverManager.getConnection(this.url, this.username, this.password);
		}catch (SQLException sqlex) {
			sqlex.printStackTrace();
		}
		return this.conn;
	}

	@Override
	List<Entity> getAll() {
		List<Entity> result = new ArrayList<>();
		try {
			Statement stmt = this.conn.createStatement();
			ResultSet rs = stmt.executeQuery(this.SELECT_ALL);
			while(rs.next()) {
				result.add(CompanyMapper.map(rs));
			}
		}catch(SQLException sqlex) {
			System.out.println(sqlex.getMessage());
			rs.close();
			stmt.close();
		}
		
		return null;
	}

}
