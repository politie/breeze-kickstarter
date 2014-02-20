package com.example;


public class Marker {

	public static class Attributes {

		private Marker source;
		private boolean isEven;


		public Marker getSource() {
			return source;
		}

		public void setSource(Marker value) {
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
		attributes.setSource(this);
		attributes.setIsEven(x % 2 == 0);
		return attributes;
	}

}
