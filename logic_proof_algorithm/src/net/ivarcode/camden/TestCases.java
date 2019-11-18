/*
 * Camden Wagner
 * */

package net.ivarcode.camden;

public class TestCases {
	
	public static void main(String[] args) {
//		Proof proof = new Proof();
//		proof.loadPremises("E->(A&C),A->(F&E),E");
//		proof.loadPremises("~(A&B),~F,(A&B)vF");
//		proof.setConsequent(new Line("F", 0));
		
		Proof proof = new Proof("A,~~B");
//		Proof proof = new Proof("A&B,~X,C->X","C");
//		Proof proof = new Proof("G->(~O->(G->D)),OvG,~O","X");
//		System.out.println(proof.getLine(0));
//		Proof proof = new Proof("A,A->B,A->(CvD),(CvD)->Z","B");
		
//		System.out.println(proof.getLine(0));
//		System.out.println(proof.getLine(1));
		
//		Proof pe = new Proof("A,A->B", "B");
		
		//System.out.println(proof);
		
		// prove recursively until exhausted options
		
//		proof.prove();
		int a = 1;//start
		while (a != 0) {
			a = proof.prove();
//			System.out.println("full pass :: " + a);
		}

		System.out.println(proof);
		
		
		/// CAM ^
		/// ENRICO v
		
		
		
		
//		
//		Proof tollensProof = new Proof();
//		tollensProof.loadPremises("~B,A->B");
//		tollensProof.setConsequent(new Line("~A", 0));
//		
//		//System.out.println(proof.getLine(0));
//		//System.out.println(proof.getLine(1));
//		
////		Proof pe = new Proof("A,A->B", "B");
//		
//		//System.out.println(proof);
//		
//		// prove recursively until exhausted options
//		
////		proof.prove();
//		int b = 1;//start
//		while (b != 0) {
//			b = tollensProof.prove();
//			System.out.println("full pass :: " + b);
//		}
//
//		System.out.println(tollensProof);
//		
	}

}
