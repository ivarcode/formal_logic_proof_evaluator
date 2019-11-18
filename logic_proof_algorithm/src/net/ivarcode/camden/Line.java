package net.ivarcode.camden;

import java.util.ArrayList;

public class Line {

	private int lineNumber;
	private String statement;
	private String operator;
	private Line premise;
	private Line consequent;
	private String rule;
	private ArrayList<String> references;
	
	// Constructor
	public Line(String s, int ln) {
		this.setLineNumber(ln);
		this.setReferences(new ArrayList<String>());
		this.setStatement(s);
		this.setRule(null);
		this.setOperator(null);
		this.setPremise(null);
		this.setConsequent(null);
		this.constructFragments();
	}
	
	// Constructor 
	public Line(String s, int ln, String rule, ArrayList<String> references) {
		this.setLineNumber(ln);
		this.setReferences(references);
		this.setStatement(s);
		this.setRule(rule);
		this.setOperator(null);
		this.setPremise(null);
		this.setConsequent(null);
		this.constructFragments();
	}
	// function that parses the proof statements into variables and operators 
	public void constructFragments() {
		// Grabs the statement the user passes in (proof)
		String s = this.getStatement();
//		System.out.println("construct fragments on line : " + s);
		// checks if it is a simple statement and does not need to be spliced 
		if (s.length() == 1) {
//			System.out.println("length is one, variable only, no further construction");
		} else {
//			System.out.println("splicing statement into fragments and operator");
			int i = 0;
			int start = 0;
			int end;
			int counter = 0;
			while (i < s.length()) {
//				System.out.println(s.charAt(i) + " " + counter);
				if (s.charAt(i) == '(') {
					counter++;
				}
				if (s.charAt(i) == ')') {
					counter--;
				}
				// implication operator '->'
				if (s.charAt(i) == '-') {
//					System.out.println("implication" + counter);
					// sets the operators, premises and consequent to the value the user inputs
					if (counter == 0) {
						end = i;
						String prms = this.trimParenthesis(s.substring(start,end));
						String op = s.substring(end,end + 2);
						String cnsq = this.trimParenthesis(s.substring(end + 2, s.length()));
//						System.out.println(prms + " " + op + " " + cnsq);
						this.setPremise(new Line(prms,0));
						this.setOperator(op);
						this.setConsequent(new Line(cnsq,0));
						break;
					}
				}
				// ambersand '&'
				if (s.charAt(i) == '&') {
					if (counter == 0) {
						end = i;
						this.setPremise(new Line(this.trimParenthesis(s.substring(start,end)),0));
						this.setOperator(s.substring(end,end + 1));
						this.setConsequent(new Line(this.trimParenthesis(s.substring(end + 1, s.length())),0));
						break;
					}
				}
				// or operator 'v'
				if (s.charAt(i) == 'v') {
					if (counter == 0) {
						end = i;
						this.setPremise(new Line(this.trimParenthesis(s.substring(start,end)),0));
						this.setOperator(s.substring(end,end + 1));
						this.setConsequent(new Line(this.trimParenthesis(s.substring(end + 1, s.length())),0));
						break;
					}
				}
				i++;
			}
		}
//		System.out.println(this);
	}
	//function that takes the parenthesis out of the user imputed statement 
	public String trimParenthesis(String s) {
		if (s.charAt(0) == '(' && s.charAt(s.length()-1) == ')') {
			return s.substring(1,s.length()-1);
		}
		return s;
	}
	
	// function that formats the output into a formal proof
	public String getFormalLine() {
		String r = this.getStatement();
		if (wasGenerated()) {
			r += "        " + this.getRule() + " ";
			for (int i = 0; i < this.getReferences().size(); i++) {
				
//				System.out.println(this.getReferences().get(i)); //.getLineNumber()
				r += this.getReferences().get(i); //.getLineNumber()
				if (i != this.getReferences().size()-1) {
					r += ",";
				}
			}
		}
		return r;
	}
	// function that gets the users statement without the parenthesis 
	public String getStatement() {
		return this.statement;
	}
	
	// function that sets the statement equal to what is being passed in 
	public void setStatement(String statement) {
		this.statement = statement;
	}

	// returns whether a rule and references exist
	public boolean wasGenerated() {
		if (this.getRule() != null) {
			return true;
		}
		return false;
	}
	
	// returns true if the operator is not null
	public boolean hasOperator() {
		return this.getOperator() != null;
	}
	
	// function that gets the operator 
	public String getOperator() {
		return operator;
	}

	// function that sets the operator equal to whatever is passed in 
	public void setOperator(String operator) {
		this.operator = operator;
	}

	// function that gets the premise
	public Line getPremise() {
		return premise;
	}
	
	// function that sets the premise equal to whatever is passed in 
	public void setPremise(Line premise) {
		this.premise = premise;
	}

	// function that gets the consequent 
	public Line getConsequent() {
		return consequent;
	}

	// function that sets the consequent to whatever is passed in 
	public void setConsequent(Line consequent) {
		this.consequent = consequent;
	}
	
	// function that overrides what prints out and prints the line information 
	public String toString() {
		String r = "line: " + this.getStatement();
		if (this.getOperator() != null) {
			r += "\n      premise: " + this.getPremise().getStatement()
			+ "\n      operator: " + this.getOperator()
			+ "\n      consequent: " + this.getConsequent().getStatement();
		}
		r += "\n";
		return r;		
	}

	// function that gets the references 
	public ArrayList<String> getReferences() {
		return references;
	}

	// function that sets the references 
	public void setReferences(ArrayList<String> references) {
		this.references = references;
	}

	// function that gets the rule 
	public String getRule() {
		return rule;
	}

	// function that sets the rule 
	public void setRule(String rule) {
		this.rule = rule;
	}
	
	// function that gets the line number 
	public int getLineNumber() {
		return lineNumber;
	}

	// function sets the line number 
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	
}
