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
	
	public void constructFragments() {
		String s = this.getStatement();
		if (s.length() == 1) {
//			System.out.println("length is one, variable only, no further construction");
		} else {
			System.out.println("splicing statement into fragments and operator");
			int i = 0;
			int start = 0;
			int end;
			int counter = 0;
			while (i < s.length()) {
				if (s.charAt(i) == '(') {
					counter++;
				}
				if (s.charAt(i) == ')') {
					counter++;
				}
				// implication operator '->'
				if (s.charAt(i) == '-') {
					if (counter == 0) {
						end = i;
						this.setPremise(new Line(this.trimParenthesis(s.substring(start,end)),0));
						this.setOperator(s.substring(end,end + 2));
						this.setConsequent(new Line(this.trimParenthesis(s.substring(end + 2, s.length())),0));
						break;
					} else {
						counter--;
					}
				}
				i++;
			}
		}
	}
	
	public String trimParenthesis(String s) {
		if (s.charAt(0) == '(' && s.charAt(s.length()-1) == ')') {
			return s.substring(1,s.length()-1);
		}
		return s;
	}

	public String getFormalLine() {
		String r = this.getStatement();
		if (wasGenerated()) {
			r += "        " + this.getRule() + " ";
			for (int i = 0; i < this.getReferences().size(); i++) {
				
				System.out.println(this.getReferences().get(i)); //.getLineNumber()
				r += this.getReferences().get(i); //.getLineNumber()
				if (i != this.getReferences().size()-1) {
					r += ",";
				}
			}
		}
		return r;
	}
	
	public String getStatement() {
		return this.trimParenthesis(statement);
	}

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
	
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Line getPremise() {
		return premise;
	}

	public void setPremise(Line premise) {
		this.premise = premise;
	}

	public Line getConsequent() {
		return consequent;
	}

	public void setConsequent(Line consequent) {
		this.consequent = consequent;
	}
	
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

	public ArrayList<String> getReferences() {
		return references;
	}

	public void setReferences(ArrayList<String> references) {
		this.references = references;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	
}
