/*
 * Camden Wagner
 * */

package net.ivarcode.camden;

public class TestCases {
	
	public static void main(String[] args) {
		Proof proof = new Proof();
		proof.loadPremises("A,A->B,A->C,A->D,A->Z");
		proof.setConsequent(new Line("B", 0));
		
		//System.out.println(proof.getLine(0));
		//System.out.println(proof.getLine(1));
		
//		Proof pe = new Proof("A,A->B", "B");
		
		//System.out.println(proof);
		
		// prove recursively until exhausted options
		
		proof.prove();
//		int a = 1;//start
//		while (a != 0) {
//			a = proof.prove();
//			System.out.println("full pass :: " + a);
//		}

		System.out.println(proof);
		
	}

}
