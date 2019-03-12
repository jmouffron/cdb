package com.excilys.cdb.persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Entity;

public abstract class DaoInstance<T> {
	protected String url;
	protected String driver;
	protected Connection conn;
	protected String username;
	protected String password;
	
	DaoInstance(){
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
			System.out.println(sqlex.getMessage());
		}
	}
	
	Connection getConnection() {
		return this.conn;
	}
	
	abstract List<T> getAll();
	abstract T getOneById(Long id);
	abstract boolean create(T newEntity);
	abstract boolean updateById(Long id, T newEntity);
	abstract boolean deleteById(Long id);
	abstract boolean deleteByName(String name);
}
