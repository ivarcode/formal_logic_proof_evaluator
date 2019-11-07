package net.ivarcode.camden;

public class TestCases {

	public static void main(String[] args) {
		ProofEvaluator pe = new ProofEvaluator();
		pe.setPremises("A,A->B");
		pe.setConsequent("B");
		System.out.print("proving\n");
		pe.prove();
		System.out.println("end prove()");
//		pe.getProof();
	}

}
