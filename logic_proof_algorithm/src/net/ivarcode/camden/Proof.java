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
		// til = total inserted lines variable, keeps track of whether or 
		// not the proof is still making progress
		int til = 0;
		for (int i = 0; i < this.getProof().size(); i++) {
			// if the line contains an operator, we want to do something with it,
			// depending on the operator
			if (this.getProof().get(i).hasOperator()) {
				// implication
				if (this.getProof().get(i).getOperator().equals("->")) {
					// modus ponens
					til += this.modusPonens(this.getProof().get(i));
					// modus tollens
					til += this.modusTollens(this.getProof().get(i));
				}
				// ambersand (&)?
				if (this.getProof().get(i).getOperator().equals("&")) {
					// simplification
					til += this.simplify(this.getProof().get(i));
				}
				if (this.getProof().get(i).getOperator().equals("<->")) {
					til += this.biconditionalExit(this.getProof().get(i));
				}
				// or operator 'v'
				if (this.getProof().get(i).getOperator().equals("v")) {
					// DS rule
					til += this.applyDSRule(this.getProof().get(i));
				}
			}
		}
		return til;
	}
	
	// returns if the line already exists in the proof
	public boolean lineAlreadyExists(String s) {
		for (int k = 0; k < this.getProof().size(); k++) {
			if (this.getProof().get(k).getStatement().equals(s)) {
				return true;
			}
		}
		return false;
	}
	
	// simplification function returns number of inserted lines
	public int simplify(Line line) {
		// finds the reference line to add to the proof
		ArrayList<String> referr = new ArrayList<String>();
		for (int a = 0; a < this.getProof().size(); a++) {
			if (this.getProof().get(a) == line) {
				referr.add("" + a);
			}
		}
		// sets to 0 and then increments if line is added to proof
		int til = 0;
		boolean dont_add = this.lineAlreadyExists(line.getPremise().getStatement());
		if (!dont_add) {
			this.addLine(line.getPremise().getStatement(),"simplification",referr);
			til++;
		}
		dont_add = this.lineAlreadyExists(line.getConsequent().getStatement());
		if (!dont_add) {
			this.addLine(line.getConsequent().getStatement(),"simplification",referr);
			til++;
		}
		return til;
	}
	
	// modus ponens function returns the number of added lines based on the modus ponens rule
	public int modusPonens(Line line) {
		int til = 0;
		for (int j = 0; j < this.getProof().size(); j++) {
			// if != line
			if (this.getProof().get(j) != line) {
				// check if the premise of line is equivalent to proof.get(j)
				if (line.getPremise().getStatement().equals(this.getProof().get(j).getStatement())) {
					// if line doesn't already exist in proof, add it
					if (!this.lineAlreadyExists(line.getConsequent().getStatement())) {
						ArrayList<String> references = new ArrayList<String>();
						// build references to include the working line
						for (int a = 0; a < this.getProof().size(); a++) {
							if (this.getProof().get(a) == line) {
								references.add("" + a);
							}
						}
						// add current line
						references.add("" + j);
						this.addLine(line.getConsequent().getStatement(), "MP", references);
						til++; // increment one, added one line
					}
				}
			}
		}
		return til; // return total inserted lines
	}
	
	
	
	public int biconditionalExit(Line line) {
		ArrayList<String> reff = new ArrayList<String>();
		for (int b = 0; b < this.getProof().size(); b++) {
			if (this.getProof().get(b) == line) {
				reff.add("" + b);
			}
		}
		int til = 0;
		boolean dont_add = this.lineAlreadyExists(line.getPremise().getStatement());
		if (!dont_add) {
			this.addLine(line.getPremise().getStatement(),"biconditional exit",reff);
			til++;
		}
		dont_add = this.lineAlreadyExists(line.getConsequent().getStatement());
		if (!dont_add) {
			this.addLine(line.getConsequent().getStatement(),"biconditional exit",reff);
			til++;
		}
		return til;
	}

	// DS rule function returns number of inserted lines
	public int applyDSRule(Line line) {
		int til = 0;
		ArrayList<String> ref = new ArrayList<String>();
		for (int a = 0; a < this.getProof().size(); a++) {
			if (this.getProof().get(a) == line) {
				ref.add("" + a);
			}
		}
		for (int a = 0; a < this.getProof().size(); a++) {
			if (this.isEquivalentTo(this.getProof().get(a).getStatement(), this.not(line.getPremise().getStatement()))) {
				boolean dont_add = this.lineAlreadyExists(line.getConsequent().getStatement());
				if (!dont_add) {
					ref.add("" + a);
					this.addLine(line.getConsequent().getStatement(), "DS", ref);
					til++;
				}
			}
		}
		// reset ref
		ref = new ArrayList<String>();
		for (int a = 0; a < this.getProof().size(); a++) {
			if (this.getProof().get(a) == line) {
				ref.add("" + a);
			}
		}
		for (int a = 0; a < this.getProof().size(); a++) {
			if (this.isEquivalentTo(this.getProof().get(a).getStatement(), this.not(line.getConsequent().getStatement()))) {
				boolean dont_add = this.lineAlreadyExists(line.getPremise().getStatement());
				if (!dont_add) {
					ref.add("" + a);
					this.addLine(line.getPremise().getStatement(), "DS", ref);
					til++;
				}
			}
		}
		return til;
	}
	
	// returns whether String are equivalent with or w/o parenthesis
	public boolean isEquivalentTo(String a, String b) {
//		System.out.println(a + "  .  " + b);
		Line l = new Line("irrelevant line", 0);
		return l.trimParenthesis(a).equals(l.trimParenthesis(b));
	}
	
	// returns the addition of a tilda on any statement
	public String not(String str) {
		return "~" + str;
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
