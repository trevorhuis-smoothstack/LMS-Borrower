package com.ss.training.lms.entity;

import java.io.Serializable;
import java.util.List;

public class Publisher implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -4973659968249782246L;
	private int publisherId;
	private String publisherName;
	private String address;
	private String phone;
	private List<Book> books;

	public Publisher(int publisherId, String publisherName, String address, List<Book> books) {
		this.publisherId = publisherId;
		this.publisherName = publisherName;
		this.address = address;
		this.books = books;
	}

	public Publisher() {
	}

	public Publisher(Integer publisherId, String publisherName, String address, String phone) {
		this.publisherId = publisherId;
		this.publisherName = publisherName;
		this.address = address;
		this.phone = phone;
	}

	public Publisher(String publisherName, String address, String phone) {
		this.publisherName = publisherName;
		this.address = address;
		this.phone = phone;
	}

	@Override
	public String toString() {
		return publisherId + ", " + publisherName + ", " + address;
	}

	public int getPublisherID() {
		return publisherId;
	}

	public void setPublisherID(int publisherId) {
		this.publisherId = publisherId;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((books == null) ? 0 : books.hashCode());
		result = prime * result + publisherId;
		result = prime * result + ((publisherName == null) ? 0 : publisherName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Publisher other = (Publisher) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (books == null) {
			if (other.books != null)
				return false;
		} else if (!books.equals(other.books))
			return false;
		if (publisherId != other.publisherId)
			return false;
		if (publisherName == null) {
			if (other.publisherName != null)
				return false;
		} else if (!publisherName.equals(other.publisherName))
			return false;
		return true;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}