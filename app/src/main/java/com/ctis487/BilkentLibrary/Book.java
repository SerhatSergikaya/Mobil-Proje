package com.ctis487.BilkentLibrary;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable{
	private int id;
	private String name;
	private String author;
	private String type;
	private String imgName;

	public Book(int id, String name, String artist, String type, String imgName) {
		super();
		this.id = id;
		this.name = name;
		this.author = artist;
		this.type = type;
		this.imgName = imgName;
	}
	public Book(String name, String artist, String type, String imgName) {
		super();
		this.name = name;
		this.author = artist;
		this.type = type;
		this.imgName = imgName;
	}


	protected Book(Parcel in) {
		id = in.readInt();
		name = in.readString();
		author = in.readString();
		type = in.readString();
		imgName = in.readString();
	}
	public static final Creator<Book> CREATOR = new Creator<Book>() {
		@Override
		public Book createFromParcel(Parcel in) {
			return new Book(in);
		}
		@Override
		public Book[] newArray(int size) {
			return new Book[size];
		}
	};
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(name);
		dest.writeString(author);
		dest.writeString(type);
		dest.writeString(imgName);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getArtist() {
		return author;
	}

	public void setArtist(String artist) {
		this.author = artist;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
}
