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
		this.setConsequent(new Line(consequent, 0));
	}
	
	// builds the first few lines of the proof from the premises string
	public void loadPremises(String premises) {
		for (int i = 0; i < premises.length(); i++) {
			int iter = i;
			while (iter != premises.length() && premises.charAt(iter) != ',') {
				iter++;
			}
			this.proof.add(new Line(premises.substring(i,iter), i));
			i = iter;
		}
	}
	
	// function responsible for evaluating the proof
	public void prove() {
		for (int i = 0; i < this.getProof().size(); i++) {
			if (this.getProof().get(i).hasOperator()) {
				// implication
				if (this.getProof().get(i).getOperator().equals("->")) {
					// modus ponens
					for (int j = 0; j < this.getProof().size(); j++) {
						if (i != j) {
							if (this.getProof().get(i).getPremise().getStatement().equals(this.getProof().get(j).getStatement())) {
								ArrayList<Line> references = new ArrayList<Line>();
								references.add(this.getProof().get(i));
								references.add(this.getProof().get(j));
								this.addLine(this.getProof().get(i).getConsequent().getStatement(),"MP",references);
							}
						}
					}
				}
			}
		}
	}
	
	// function responsible for adding a line with rules
	public void addLine(String s, String rule, ArrayList<Line> references) {
		// exit function if line will be a duplicate
		for (int i = 0; i < this.getProof().size(); i++) {
			if (this.getProof().get(i).getStatement().equals(s)) {
				System.out.println("this line already exists!");
				return; // exit function as to not add line
			}
		}
		System.out.println("adding a proof while size " + this.getProof().size());
		Line n = new Line(s, this.proof.size(), rule, references);
		System.out.println(n.getLineNumber());
		this.proof.add(n);
	}

	// function responsible for returning an individual line
	public Line getLine(int index) {
		return this.getProof().get(index);
	}
	
	// function responsible for returning the proof
	public ArrayList<Line> getProof() {
		return proof;
	}

	// getter
	public Line getConsequent() {
		return consequent;
	}

	// setter
	public void setConsequent(Line consequent) {
		this.consequent = consequent;
	}
	
	// output string
	public String toString() {
		String r = "";
		for (int i = 0; i < this.getProof().size(); i++) {
			r += i + ". " + this.getProof().get(i).getFormalLine() + "\n";
		}
		return r;
	}

}
