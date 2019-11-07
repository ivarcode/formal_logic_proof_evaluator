package net.ivarcode.camden;

public class TestCases {

	public static void main(String[] args) {
		Proof proof = new Proof();
		proof.loadPremises("A,A->B");
		proof.setConsequent(new Line("B"));
		
//		Proof pe = new Proof("A,A->B", "B");
		
		System.out.println(proof);
		
		System.out.print("prove()\n");
		proof.prove();
		System.out.println("end prove()");
//		pe.getProof();
	}

}
