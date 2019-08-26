package com.neufree.contacts.collections;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import org.junit.Test;

public class TestSearchIndecies {
	public int MAX_ARRAY_VALUE = 3000;
	ContactDatabase cd;
	String pathFile = "Contact_DataBase";
	Random rand = new Random();

	String[] firstNamesArray = new String[3000];
	String[] lastNamesArray = new String[3000];
	String[] primaryEmailsArray = new String[3000];
	String[] secondaryEmailsArray = new String[3000];
	String[] primaryPhonesArray = new String[3000];
	String[] secondaryPhonesArray = new String[3000];

	public String[] nameMaker(String txtFileName) throws FileNotFoundException {
		File path = new File("C:\\Users\\Prvora89\\workspace_open_source\\contact-person1\\" + txtFileName);

		Scanner file = new Scanner(path);
		String[] nameType = new String[100];
		for (int i = 0; i < nameType.length; i++) {
			nameType[i] = file.nextLine();
			nameType[i].trim().replaceAll(" ", "");
		}
		return nameType;
	}

	public String[] emailTypeMaker(String suffix) throws FileNotFoundException {

		String[] emailType = new String[100];

		for (int i = 0; i < emailType.length; i++) {
			emailType[i] = firstNamesArray[i] + lastNamesArray[i] + i + suffix;
		}
		return emailType;
	}

	public String[] phoneNumberGenerator() {
		Random rand = new Random();
		String[] numArray = new String[] { "234-234-345", "346-234-345", "423-923-434" };
		for (int i = 0; i < 3000; i++) {
			primaryPhonesArray[i] = numArray[rand.nextInt(3)];
		}
		// System.out.println("Generated Numbers is : " + number);
		return primaryPhonesArray;
	}

	public void setInformation() throws IOException, ClassNotFoundException {
		cd = new ContactDatabase(pathFile);

		firstNamesArray = nameMaker("firstnames.txt");
		lastNamesArray = nameMaker("lastnames.txt");

		primaryEmailsArray = emailTypeMaker("@gmail.com");
		secondaryEmailsArray = emailTypeMaker("@yahoo.com");

		primaryPhonesArray = phoneNumberGenerator();
		secondaryPhonesArray = phoneNumberGenerator();
	}

//	@Test
//	public void makeContactToDatabase() throws IOException, ClassNotFoundException {
//		setInformation();
//		cd = new ContactDatabase(pathFile);
//		Contact addContact = null;
//		for (int i = 0; i < MAX_ARRAY_VALUE; i++) {
//			int randomNum = rand.nextInt(99);
//			int randomGen = rand.nextInt(99);
//			addContact = new Contact(firstNamesArray[randomNum], lastNamesArray[randomNum],
//					primaryEmailsArray[randomGen], secondaryEmailsArray[randomGen], primaryPhonesArray[randomGen],
//					secondaryPhonesArray[randomGen]);
//			System.out.println((i + 1) + addContact.toString());
//			cd.insert(addContact);
//		}
//	}
	
	@Test
	public void searchByFirstNameTest() throws ClassNotFoundException, IOException {
		ContactDatabase cd = new ContactDatabase(pathFile);
		
		long startTime = System.currentTimeMillis();
			
			List<Contact> returnList = cd.searchByFirstName("Adam");
			System.out.println(returnList);
			for (Contact result : returnList) {
				assertTrue(result.getFirstName().equals("Adam"));
			}
			long endTime = System.currentTimeMillis();
			long elaspedTime = endTime - startTime;
			System.out.println("\nElasped Time: " + elaspedTime + " milliseconds\n");

	}
	
	@Test
	public void searchByLastNameTest() throws ClassNotFoundException, IOException {
		ContactDatabase cd = new ContactDatabase(pathFile);
		long startTime = System.currentTimeMillis();
			
			List<Contact> returnList = cd.searchByLastName("Aarestad");
			System.out.println(returnList);
			for (Contact result : returnList) {
				assertTrue(result.getLastName().equals("Aarestad"));
			}
			long endTime = System.currentTimeMillis();
			long elaspedTime = endTime - startTime;
			System.out.println("\nElasped Time: " + elaspedTime + " milliseconds\n");

	}
	
	@Test
	public void searchByPrimaryEmailTest() throws ClassNotFoundException, IOException {
		ContactDatabase cd = new ContactDatabase(pathFile);
		long startTime = System.currentTimeMillis();
			
			List<Contact> returnList = cd.searchByPrimaryEmail("AbeAalbers6@gmail.com");
			System.out.println(returnList);
			for (Contact result : returnList) {
				assertTrue(result.getPrimaryEmailAddress().equals("AbeAalbers6@gmail.com"));
			}
			long endTime = System.currentTimeMillis();
			long elaspedTime = endTime - startTime;
			System.out.println("\nElasped Time: " + elaspedTime + " milliseconds\n");

	}

	@Test
	public void searchByPrimaryPhoneTest() throws ClassNotFoundException, IOException {
		ContactDatabase cd = new ContactDatabase(pathFile);
		long startTime = System.currentTimeMillis();
			
			List<Contact> returnList = cd.searchByPrimaryPhone("423-923-434");
			System.out.println(returnList);
			for (Contact result : returnList) {
				assertTrue(result.getPrimaryPhone().equals("423-923-434"));
			}
			long endTime = System.currentTimeMillis();
			long elaspedTime = endTime - startTime;
			System.out.println("\nElasped Time: " + elaspedTime + " milliseconds\n");

	}

	 

	

}
