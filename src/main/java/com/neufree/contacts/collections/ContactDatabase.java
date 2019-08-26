package com.neufree.contacts.collections;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactDatabase {
	private RandomAccessFile file;

	private int nextOffset = 8;
	// index that we will pass in to loop up the contact
	private Map<String, List<Integer>> firstNameIndex = new HashMap<>();
	private Map<String, List<Integer>> lastNameIndex = new HashMap<>();
	private Map<String, List<Integer>> primaryEmailIndex = new HashMap<>();
	private Map<String, List<Integer>> primaryPhoneIndex = new HashMap<>();

	private String firstNameFilePath = "firstName_Hashmap.txt";
	
	private String lastNameFilePath = "lastName_Hashmap.txt";

	private String primaryEmailFilePath = "primaryEmail_Hashmap.txt";

	private String primaryPhoneFilePath = "primaryPhone_Hashmap.txt";

	public ContactDatabase(String path) throws IOException, ClassNotFoundException {
		
		boolean readOffsetFromFile = false;
		if(new File(path).exists()) { 
			readOffsetFromFile = true;
		}
		firstNameIndex = load(firstNameFilePath, firstNameIndex);
		lastNameIndex = load(lastNameFilePath, lastNameIndex);
		primaryEmailIndex = load(primaryEmailFilePath, primaryEmailIndex);
		primaryPhoneIndex = load(primaryPhoneFilePath, primaryPhoneIndex);
		
		file = new RandomAccessFile(path, "rw");
		if(readOffsetFromFile) { 
			nextOffset = file.readInt();
		} else { 
			file.writeInt(nextOffset);
		}
	}

	// Recommendation : use a separate file for each hash map and index
	// use object output stream / serialize
	private void addToFirstNameIndex(Contact c, int index) throws IOException { 															// firstNameIndex
		List<Integer> firstNameList = firstNameIndex.get(c.getFirstName());
		if (firstNameList == null) {
			firstNameList = new ArrayList<>();
			firstNameIndex.put(c.getFirstName(), firstNameList);
			// call serialize method -(HashMap to File) : Save
		}
		firstNameList.add(index);
		save(firstNameFilePath, firstNameIndex);
	}

	private void addToLastNameIndex(Contact c, int index) throws IOException {
		List<Integer> lastNameList = lastNameIndex.get(c.getLastName());
		if (lastNameList == null) {
			lastNameList = new ArrayList<>();
			lastNameIndex.put(c.getLastName(), lastNameList);
			// call serialize method -(HashMap to File) : Save
		}
		lastNameList.add(index);
		save(lastNameFilePath, lastNameIndex);
	}

	private void addToPrimaryEmailIndex(Contact c, int index) throws IOException {
		List<Integer> primaryEmailList = primaryEmailIndex.get(c.getPrimaryEmailAddress());
		if (primaryEmailList == null) {
			primaryEmailList = new ArrayList<>();
			primaryEmailIndex.put(c.getPrimaryEmailAddress(), primaryEmailList);
			// call serialize method -(HashMap to File) : Save
		}
		primaryEmailList.add(index);
		save(primaryEmailFilePath, primaryEmailIndex);
	}

	private void addToPrimaryPhoneIndex(Contact c, int index) throws IOException {
		List<Integer> primaryPhoneList = primaryPhoneIndex.get(c.getPrimaryPhone());
		if (primaryPhoneList == null) {
			primaryPhoneList = new ArrayList<>();
			primaryPhoneIndex.put(c.getPrimaryPhone(), primaryPhoneList);
			// call serialize method -(HashMap to File) : Save
		}
		primaryPhoneList.add(index);
		save(primaryPhoneFilePath, primaryPhoneIndex);
	}

	public List<Contact> searchByFirstName(String firstName) throws ClassNotFoundException, IOException { // returns a list of contacts that matches certain criteria																								
		// deserialized, grab the dta and put it inside the firstname
		
		List<Integer> firstNameindecies = firstNameIndex.get(firstName);
		List<Contact> firstNameResults = new ArrayList<>();
		if (firstNameindecies == null) {
			System.out.println("THIS WAS NULL");
			return firstNameResults;
		}
		for (Integer index : firstNameindecies) {
			Contact c = this.read(index); // look up method
			firstNameResults.add(c);
		}
		return firstNameResults;
	}

	public List<Contact> searchByLastName(String lastName) throws ClassNotFoundException, IOException { 
		
		List<Integer> lastNameIndecies = lastNameIndex.get(lastName);
		List<Contact> lastNameResults = new ArrayList<>();
		if (lastNameIndecies == null) {
			return lastNameResults;
		}
		for (Integer index : lastNameIndecies) {
			Contact c = this.read(index); // look up method
			lastNameResults.add(c);
		}
		return lastNameResults;
	}
	
	public List<Contact> searchByPrimaryEmail(String primaryEmail) throws ClassNotFoundException, IOException { 

		List<Integer> primaryEmailIndecies = primaryEmailIndex.get(primaryEmail);
		List<Contact> primaryEmailResults = new ArrayList<>();
		if (primaryEmailIndecies == null) {
			return primaryEmailResults;
		}
		for (Integer index : primaryEmailIndecies) {
			Contact c = this.read(index); // look up method
			primaryEmailResults.add(c);
		}
		return primaryEmailResults;
	}
	
	public List<Contact> searchByPrimaryPhone(String primaryPhone) throws ClassNotFoundException, IOException { 
	
		List<Integer> primaryPhoneIndecies = primaryPhoneIndex.get(primaryPhone);
		List<Contact> primaryPhoneResults = new ArrayList<>();
		if (primaryPhoneIndecies== null) {
			return primaryPhoneResults;
		}
		for (Integer index : primaryPhoneIndecies) {
			Contact c = this.read(index); // look up method
			primaryPhoneResults.add(c);
		}
		return primaryPhoneResults;
	}

	public void insert(Contact c) throws IOException {
		addToFirstNameIndex(c, (nextOffset - 8) / 1044);
		addToLastNameIndex(c, (nextOffset - 8) / 1044);
		addToPrimaryEmailIndex(c, (nextOffset - 8) / 1044);
		addToPrimaryPhoneIndex(c, (nextOffset - 8) / 1044);
		
		byte[] buffer = c.serialize().getBytes();
		file.seek(nextOffset);
		file.write(buffer);
		nextOffset += buffer.length;
		updateNextOffSet();
	}

	private void updateNextOffSet() throws IOException {
		file.seek(0);
		file.writeInt(nextOffset);
		file.seek(nextOffset);
	}

	public void save(String path, Map<String, List<Integer>> hash) throws IOException { // serialize it
		
		try (FileOutputStream out = new FileOutputStream(path)) {
			try (ObjectOutputStream objOut = new ObjectOutputStream(out)) {
				objOut.writeObject(hash);
			}
		}
		
	}

	public Map<String, List<Integer>> load(String filePath, Map<String, List<Integer>> loaded) throws IOException, ClassNotFoundException { // deserialize
		//File fi = new File(filePath);
			
		if(new File(filePath).exists()) { 
			try (FileInputStream input = new FileInputStream(filePath)) {
				try (ObjectInputStream objIn = new ObjectInputStream(input)) {
					Object raw = objIn.readObject();
					loaded = ((Map<String, List<Integer>>) raw);
				}
			}	
		} 		
		return loaded;	
	}

	public Contact read(int index) throws IOException, ClassNotFoundException {
		int totalSizeOfContact = 1044;
		int findingIndex = ((index * totalSizeOfContact) + 8);
		file.seek(findingIndex);
		byte[] getInfoArray = new byte[totalSizeOfContact];
		file.read(getInfoArray);

		String theContact = new String(getInfoArray, "UTF-8");
		String firstNameline = theContact.substring(0, 255).trim().replace(" ", "");
		String lastNameLine = theContact.substring(255, 510).trim().replace(" ", "");
		String primaryEmailLine = theContact.substring(510, 765).trim().replace(" ", "");
		String secondaryEmailAddressLine = theContact.substring(765, 1020).trim().replace(" ", "");
		String primaryPhoneLine = theContact.substring(1020, 1032).trim().replace(" ", "");
		String secondaryPhoneLine = theContact.substring(1032, 1044).trim().replace(" ", "");

		Contact c = new Contact(firstNameline, lastNameLine, primaryEmailLine, secondaryEmailAddressLine,
				primaryPhoneLine, secondaryPhoneLine);
		return c;
	}

}
