package com.masivian.restful.objects;

import java.io.Serializable;

public class ObjectBet implements Serializable{
	private String idUser;
	private String colorBet;
	private int amountBet;
	private int numberBet;	
	
	public ObjectBet() {}	
	public ObjectBet(int amountBet, int numberBet) {
		this.amountBet = amountBet;
		this.numberBet = numberBet;
		
	}
	public ObjectBet(int amountBet, String colorBet) {
		this.amountBet = amountBet;
		this.colorBet = colorBet;
	}
	public String getIdUser() {
		return idUser;
	}
	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}
	public int getAmountBet() {
		return amountBet;
	}
	public void setAmountBet(int amountBet) {
		this.amountBet = amountBet;
	}
	public int getNumberBet() {
		return numberBet;
	}
	public void setNumberBet(int numberBet) {
		this.numberBet = numberBet;
	}
	public String getColorBet() {
		return colorBet;
	}
	public void setColorBet(String colorBet) {
		this.colorBet = colorBet;
	}
	
}
