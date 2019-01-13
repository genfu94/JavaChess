package org.Project_MP_2014;

import java.util.Vector;

public class Utils {
	public static Vector<Square> CloneSquareVector(Vector<Square> vector) {
		Vector<Square> clone=new Vector<Square>();
		for(int i=0;i<vector.size();i++) {
			clone.add(vector.get(i).clone());
		}
		return clone;
	}
	
	public static <A> void MergeVectors(Vector<A> vectorA, Vector<A> vectorB, int index) {
		if(vectorA!=null && vectorB!=null && vectorB.size()>=index) {
			vectorA.addAll(vectorB.subList(index, vectorB.size()));
		}
	}
	
	public static <A> void AddToVector(Vector<A> vector, A element) {
		if(element!=null) {
			vector.add(element);
		}
	}
}
