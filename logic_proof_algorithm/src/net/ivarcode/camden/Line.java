package net.ivarcode.camden;

public class Line {

	private String statement;
	private String operator;
	private Line premise;
	private Line consequent;
	
	public Line(String s) {
		this.setStatement(s);
		this.setOperator(null);
		this.setPremise(null);
		this.setConsequent(null);
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
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
	
}
