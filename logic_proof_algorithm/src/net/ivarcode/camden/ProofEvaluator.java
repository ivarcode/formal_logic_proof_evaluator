package net.ivarcode.camden;

public class ProofEvaluator {
	
	private String premises;
	private String consequent;
	private String proof;
	
	// empty constructor
	public ProofEvaluator() {
		this.setProof(null);
	}
	// constructor w/ premises
	public ProofEvaluator(String premises) {
		this.setProof(premises);
	}
	// constructor w/ premises and consequent
	public ProofEvaluator(String premises, String consequent) {
		this.setProof(premises);
		this.setConsequent(consequent);
	}
	
	// function responsible for evaluating the proof
	public void prove() {
		String premises = this.getPremises();
		String consequent = this.getConsequent();
		// evaluate the number of premises
		int n = 1;
		for (int i = 0; i < premises.length(); i++) {
			if (premises.charAt(i) == ',') {
				n++;
			}
		}
		// generate the array of premises
		String[] lines = new String[n];
		int c = 0;
		for (int i = 0; i < premises.length(); i++) {
			int iter = i;
			while (iter != premises.length() && premises.charAt(iter) != ',') {
				iter++;
			}
			lines[c] = premises.substring(i,iter);
			c++;
			i = iter;
		}
		// print the contents of the lines (of the proof) array
		for (int i = 0; i < lines.length; i++) {
			System.out.println(lines[i]);
		}
	}

	// function responsible for returning the proof
	// returns proof if not null, else evaluates proof, then returns
	public String getProof() {
		if (proof == null) {
			System.out.println("this proof has not been calculated\ncalculating proof now\n");
			this.prove();
		}
		return proof;
	}

	// setter
	public void setProof(String proof) {
		this.proof = proof;
	}
	// getter
	public String getConsequent() {
		return consequent;
	}
	// setter
	public void setConsequent(String consequent) {
		this.consequent = consequent;
	}
	// getter
	public String getPremises() {
		return premises;
	}
	// setter
	public void setPremises(String premises) {
		this.premises = premises;
	}

}
