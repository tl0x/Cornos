/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: RandomHelper
# Created by constantin at 16:25, Mär 02 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.zeroX150.cornos.etc.helper;

import java.util.Random;

public class Rnd {
	public static String rndStr(int size) {
		StringBuilder end = new StringBuilder();
		for (int i = 0; i < size; i++) {
			// 65+57
			end.append((char) (new Random().nextInt(65) + 57));
		}
		return end.toString();
	}

	public static String rndAscii(int size) {
		StringBuilder end = new StringBuilder();
		for (int i = 0; i < size; i++) {
			// 97+25
			end.append((char) (new Random().nextInt(25) + 97));
		}
		return end.toString();
	}

	public static String rndBinStr(int size) {
		StringBuilder end = new StringBuilder();
		for (int i = 0; i < size; i++) {
			// 65+57
			end.append((char) (new Random().nextInt(0xFFFF)));
		}
		return end.toString();
	}

	public static double rndD(double rad) {
		Random r = new Random();
		return r.nextDouble() * rad;
	}
}
