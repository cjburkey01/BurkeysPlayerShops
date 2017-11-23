package com.cjburkey.playershops.shop;

import java.util.regex.Pattern;

public class ShopItemData {
	
	private final double buy;
	private final double sell;
	private int amt;
	
	public ShopItemData(double buy, double sell) {
		this (buy, sell, 0);
	}
	
	public ShopItemData(double buy, double sell, int amt) {
		this.buy = buy;
		this.sell = sell;
		this.amt = amt;
	}
	
	public double getBuy() {
		return buy;
	}
	
	public double getSell() {
		return sell;
	}
	
	public void setStock(int amt) {
		this.amt = amt;
	}
	
	public int getStock() {
		return amt;
	}
	
	public String toString() {
		return buy + ";" + sell + ";" + amt;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(buy);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(sell);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ShopItemData other = (ShopItemData) obj;
		if (Double.doubleToLongBits(buy) != Double.doubleToLongBits(other.buy)) {
			return false;
		}
		if (Double.doubleToLongBits(sell) != Double.doubleToLongBits(other.sell)) {
			return false;
		}
		return true;
	}
	
	public static ShopItemData parse(String input) {
		if (input == null || (input = input.trim()).isEmpty()) {
			return null;
		}
		String[] spl = input.split(Pattern.quote(";"));
		if (spl.length != 3) {
			return null;
		}
		try {
			double b = Double.parseDouble(spl[0]);
			double s = Double.parseDouble(spl[1]);
			int a = Integer.parseInt(spl[2]);
			return new ShopItemData(b, s, a);
		} catch (Exception e) {
		}
		return null;
	}
	
}