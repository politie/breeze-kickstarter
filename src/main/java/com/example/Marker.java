package com.example;


public class Marker {

	public static class Attributes {

		private String source;
		private boolean isEven;


		public String getSource() {
			return source;
		}

		public void setSource(String value) {
			source = value;
		}

		public boolean getIsEven() {
			return isEven;
		}

		public void setIsEven(boolean value) {
			isEven = value;
		}
	}

	public Attributes mark(long x) {
		Attributes attributes = new Attributes();
		attributes.setSource(toString());
		attributes.setIsEven(x % 2 == 0);
		return attributes;
	}

}
