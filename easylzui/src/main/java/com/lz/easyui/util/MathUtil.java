package com.lz.easyui.util;

import java.util.ArrayList;

public class MathUtil {

	public static int getMax(int[] arr) {
		int max = Integer.MIN_VALUE;

		for (int i = 0; i < arr.length; i++) {
			if (arr[i] > max)
				max = arr[i];
		}

		return max;
	}

	public static int getMin(int[] arr) {
		int min = Integer.MAX_VALUE;

		for (int i = 0; i < arr.length; i++) {
			if (arr[i] < min)
				min = arr[i];
		}

		return min;
	}
	
	public static float getMaxfloat(float[] arr) {
		float max = Float.MIN_VALUE;

		for (int i = 0; i < arr.length; i++) {
			if (arr[i] > max)
				max = arr[i];
		}

		return max;
	}

	public static float getMinfloat(float[] arr) {
		float min = Float.MAX_VALUE;

		for (int i = 0; i < arr.length; i++) {
			if (arr[i] < min)
				min = arr[i];
		}

		return min;
	}
	
	public static double getMaxdouble(double[] arr) {
		double max = Double.MIN_VALUE;

		for (int i = 0; i < arr.length; i++) {
			if (arr[i] > max)
				max = arr[i];
		}

		return max;
	}
	public static double getMaxdouble(ArrayList<Double> arr) {
		double max = Double.MIN_VALUE;
		
		for (int i = 0; i < arr.size(); i++) {
			double d = arr.get(i);
			if (d > max)
				max = d;
		}

		return max;
	}
	public static double getMindouble(double[] arr) {
		double min = Double.MAX_VALUE;

		for (int i = 0; i < arr.length; i++) {
			
			if (arr[i] < min)
				min = arr[i];
		}

		return min;
	}
	public static double getMindouble(ArrayList<Double> arr) {
		double min = Double.MAX_VALUE;

		for (int i = 0; i < arr.size(); i++) {
			double d = arr.get(i);
			if (d < min)
				min = d;
		}

		return min;
	}
}
