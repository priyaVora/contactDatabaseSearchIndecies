package com.neufree.contacts.collections;

import java.io.Serializable;

public class Contact implements Serializable {
	private String firstName;
	private String lastName;
	private String primaryEmailAddress;
	private String secondaryEmailAddress;
	private String primaryPhone;
	private String secondaryPhone;

	public Contact() {

	}

	public Contact(String firstName, String lastName, String primaryEmailAddress, String secondaryEmailAddress, String primaryPhoneNumber, String secondaryPhoneNumber) {
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setPrimaryEmailAddress(primaryEmailAddress);
		this.setSecondaryEmailAddress(secondaryEmailAddress);
		this.setPrimaryPhone(primaryPhoneNumber);
		this.setSecondaryPhone(secondaryPhoneNumber);
	}

//	@Override 
//	public boolean equals(Object obj) {
//		if(!(obj instanceof Contact)) { 
//			return false;
//		}
//		Contact other = (Contact)obj;
//		//return this.getName().equals(other.getName()) && this.getPhone().equals(other.getPhone());
//		return this.getFirstName().equals(other.getFirstName());
//	}
//	
	public String getFirstName() {
		return firstName;
	}

	@Override
	public String toString() {
		return "Contact [firstName=" + firstName + ", lastName=" + lastName + ", primaryEmailAddress="
				+ primaryEmailAddress + ", secondaryEmailAddress=" + secondaryEmailAddress + ", primaryPhone="
				+ primaryPhone + ", secondaryPhone=" + secondaryPhone + "]";
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPrimaryEmailAddress() {
		return primaryEmailAddress;
	}

	public void setPrimaryEmailAddress(String primaryEmailAddress) {
		this.primaryEmailAddress = primaryEmailAddress;
	}

	public String getSecondaryEmailAddress() {
		return secondaryEmailAddress;
	}

	public void setSecondaryEmailAddress(String secondaryEmailAddress) {
		this.secondaryEmailAddress = secondaryEmailAddress;
	}

	public String getPrimaryPhone() {
		return primaryPhone;
	}

	public void setPrimaryPhone(String primaryPhone) {
		this.primaryPhone = primaryPhone;
	}

	public String getSecondaryPhone() {
		return secondaryPhone;
	}

	public void setSecondaryPhone(String secondaryPhone) {
		this.secondaryPhone = secondaryPhone;
	}

	public String serialize() { 
		//automatically padded with spaces...
		String formatted =  String.format("%255s%255s%255s%255s%12s%12s", this.getFirstName(), this.getLastName(), this.getPrimaryEmailAddress(), this.getSecondaryEmailAddress(), this.getPrimaryPhone(), this.getSecondaryPhone());
		return formatted;
		//String firstName = formatted.substring(0,255);
	}
}
