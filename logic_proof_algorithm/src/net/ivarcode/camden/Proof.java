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
	// returns number of inserted lines
	public int prove() {
		int til = 0;
		for (int i = 0; i < this.getProof().size(); i++) {
			if (this.getProof().get(i).hasOperator()) {
				// implication
				if (this.getProof().get(i).getOperator().equals("->")) {
					// modus ponens
					for (int j = 0; j < this.getProof().size(); j++) {
						if (i != j) {
							// i is A j is B
							String result = this.modusPonens(this.getProof().get(i), this.getProof().get(j));
							if (result != null) {
//								ArrayList<Line> references = new ArrayList<Line>();
								ArrayList<String> referr = new ArrayList<String>();
								for (int a = 0; a < this.getProof().size(); a++) {
									if ((this.getProof().get(i) == this.getProof().get(a)) || (this.getProof().get(j) == this.getProof().get(a))) {
//										System.out.println("ayyy " + a);
										referr.add("" + a);
									}
								}
								boolean dont_add = false;
								
								for (int k = 0; k < this.getProof().size(); k++) {
									if (this.getProof().get(k).getStatement().equals(result)) {
										dont_add = true;
									}
								}
								if (!dont_add) {
									this.addLine(result,"MP",referr);
									til++;
								}
							}
						}
					}
					// modus tollens
					for (int j = 0; j < this.getProof().size(); j++) {
						if (i != j) {
							String result = this.modusTollens(this.getProof().get(i), this.getProof().get(j));
							if (result != null) {
								ArrayList<String> ref = new ArrayList<String>();
								for (int a = 0; a < this.getProof().size(); a++) {
									if ((this.getProof().get(i) == this.getProof().get(a)) || (this.getProof().get(j) == this.getProof().get(a))) {
//										System.out.println("ayyy " + a);
										ref.add("" + a);
									}
									
								}
								boolean dont_add = false;
								
								for (int k = 0; k < this.getProof().size(); k++) {
									if (this.getProof().get(k).getStatement().equals(result)) {
										dont_add = true;
									}
								}
								if (!dont_add) {
									this.addLine(result,"MT",ref);
									til++;
								}
							}
								
						}
					}
				}
				// ambersand (&)?
				if (this.getProof().get(i).getOperator().equals("&")) {
					// simplification
					til += this.simplify(this.getProof().get(i));
				}
			}
		}
		return til;
	}
	
	// simplification function returns number of inserted lines
	public int simplify(Line line) {
		ArrayList<String> referr = new ArrayList<String>();
		for (int a = 0; a < this.getProof().size(); a++) {
			if (this.getProof().get(a) == line) {
//				System.out.println("ayyy " + a);
				referr.add("" + a);
			}
		}
		int til = 0;
		boolean dont_add = false;
		for (int k = 0; k < this.getProof().size(); k++) {
			if (this.getProof().get(k).getStatement().equals(line.getPremise().getStatement())) {
				dont_add = true;
			}
		}
		if (!dont_add) {
			this.addLine(line.getPremise().getStatement(),"simplification",referr);
			til++;
		}
		dont_add = false;
		for (int k = 0; k < this.getProof().size(); k++) {
			if (this.getProof().get(k).getStatement().equals(line.getConsequent().getStatement())) {
				dont_add = true;
			}
		}
		if (!dont_add) {
			this.addLine(line.getConsequent().getStatement(),"simplification",referr);
			til++;
		}
		return til;
	}
	
	// modus ponens function returns if there is a modus ponens relationship
	// between a and b and returns the resulting line statement if so
	public String modusPonens(Line a, Line b) {
		if (a.getPremise().getStatement().equals(b.getStatement())) {
			return a.getConsequent().getStatement();
		}
		return null;
	}
	
	// returns the addition of a tilda on any statement
	public String not(String str) {
		return "~" + str;
	}
	
	public String modusTollens(Line a, Line b) {
		if (this.not(a.getConsequent().getStatement()).equals(b.getStatement())) {
			return this.not(a.getPremise().getStatement());
		}
		return null;
	}
	
	// function responsible for adding a line with rules
	public void addLine(String s, String rule, ArrayList<String> references) {
		// exit function if line will be a duplicate
		for (int i = 0; i < this.getProof().size(); i++) {
			if (this.getProof().get(i).getStatement().equals(s)) {
				System.out.println("this line already exists!");
				return; // exit function as to not add line
			}
		}
//		System.out.println("adding a proof while size " + this.getProof().size());
		Line n = new Line(s, this.proof.size(), rule, references);
//		System.out.println(n.getLineNumber());
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
		String r = "-- FORMAL PROOF --\n";
		for (int i = 0; i < this.getProof().size(); i++) {
			r += i + ". " + this.getProof().get(i).getFormalLine() + "\n";
		}
		r += "-- END OF PROOF --";
		return r;
	}

}
