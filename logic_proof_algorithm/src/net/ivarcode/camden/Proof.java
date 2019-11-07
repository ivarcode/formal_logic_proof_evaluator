package net.ivarcode.camden;

import java.util.ArrayList;

public class Proof {
	
	private ArrayList<Line> proof;
	private Line consequent;
	
	// empty constructor
	public Proof() {
		this.proof = new ArrayList<Line>();
	}
	// constructor w/ premises
	public Proof(String premises) {
		this.proof = new ArrayList<Line>();
		this.loadPremises(premises);
	}
	// constructor w/ premises and consequent
	public Proof(String premises, String consequent) {
		this.proof = new ArrayList<Line>();
		this.loadPremises(premises);
		this.setConsequent(new Line(consequent));
	}
	
	// builds the first few lines of the proof from the premises string
	public void loadPremises(String premises) {
		for (int i = 0; i < premises.length(); i++) {
			int iter = i;
			while (iter != premises.length() && premises.charAt(iter) != ',') {
				iter++;
			}
			this.proof.add(new Line(premises.substring(i,iter)));
			i = iter;
		}
	}
	
	// function responsible for evaluating the proof
	public void prove() {
		
	}

	// function responsible for returning the proof
	// returns proof if not null, else evaluates proof, then returns
	public ArrayList<Line> getProof() {
		if (proof == null) {
			System.out.println("this proof has not been calculated\ncalculating proof now\n");
			this.prove();
		}
		return proof;
	}

	// getter
	public Line getConsequent() {
		return consequent;
	}

	public void setConsequent(Line consequent) {
		this.consequent = consequent;
	}
	
	public String toString() {
		String r = "";
		for (int i = 0; i < this.getProof().size(); i++) {
			r += this.getProof().get(i).getStatement() + "\n";
		}
		return r;
	}

}
