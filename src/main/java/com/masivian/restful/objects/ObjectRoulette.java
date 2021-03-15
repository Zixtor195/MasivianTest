package com.masivian.restful.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class ObjectRoulette implements Serializable{
	private String status;
	private ArrayList<ObjectBet> arrayBets;
	
	public ObjectRoulette () {}
	public ObjectRoulette (String status) {
		this.status = status;
	}
	public ObjectRoulette (String status, ArrayList<ObjectBet> arrayBets) {
		this.status = status;
		this.arrayBets = arrayBets;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public ArrayList<ObjectBet> getArrayBets() {
		return arrayBets;
	}
	public void setArrayBets(ArrayList<ObjectBet> arrayBets) {
		this.arrayBets = arrayBets;
	}
}
