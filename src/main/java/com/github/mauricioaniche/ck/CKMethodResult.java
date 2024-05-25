package com.github.mauricioaniche.ck;

import org.eclipse.jdt.core.dom.Modifier;

import java.util.*;

public class CKMethodResult extends CKResult {
	private String methodName;
	private String qualifiedMethodName;
	private boolean isVisible;
	private int parametersQty;
	private Map<String, Integer> variablesUsage;
	private int startLine;

	//all local field accesses
	private Map<String, Integer> fieldUsage;
	private boolean isConstructor;
	private int logStatementsQty;
	private boolean hasJavadoc;

	//All direct invocations of methods
	private Set<String> methodInvocations;
	//All direct invocations of methods of the same class
	private Set<String> methodInvocationsLocal;
	//All indirect invocations of methods of the same class
	private Map<String, Set<String>> methodInvocationsIndirectLocal;

	public CKMethodResult(String methodName, String qualifiedMethodName, boolean isConstructor, int modifiers) {
		super(modifiers);
		this.methodName = methodName;
		this.qualifiedMethodName = qualifiedMethodName;
		this.isConstructor = isConstructor;
		this.isVisible = !Modifier.isPrivate(modifiers);
	}

	public boolean getIsVisible(){ return isVisible;}

	public void setParametersQty(int parametersQty) {
		this.parametersQty = parametersQty;
	}

	public String getMethodName() {
		return methodName;
	}

	public String getQualifiedMethodName() {
		return qualifiedMethodName;
	}

	@Override
	public String toString() {
		return "CKMethodResult{" +
				"cbo=" + this.getCbo() +
				", rfc=" + this.getRfc() +
				", wmc=" + this.getWmc() +
				", methodName='" + methodName + '\'' +
				'}';
	}

	public int getParametersQty() {
		return parametersQty;
	}

	public void setVariablesUsage(Map<String, Integer> variablesUsage) {
		this.variablesUsage = variablesUsage;
	}

	public Map<String, Integer> getVariablesUsage() {
		if(this.variablesUsage==null)
			this.variablesUsage = new HashMap<>();

		return variablesUsage;
	}

	public void setStartLine(int startLine) {
		this.startLine = startLine;
	}

	public int getStartLine() {
		return startLine;
	}

	@Override
	protected int getValueFanOut() {
		return couplingExtras.getValueFanOutMethod(this.qualifiedMethodName);
	}

	@Override
	protected int getValueFanIn() {
		return couplingExtras.getValueFanInMethod(this.qualifiedMethodName);
	}

	@Override
	protected int getValueCBO() {
		return couplingExtras.getValueCBOMethod(this.qualifiedMethodName);
	}

	public void setFieldUsage(Map<String, Integer> fieldUsage) {
		this.fieldUsage = fieldUsage;
	}

	public Map<String, Integer> getFieldUsage() {
		if(this.fieldUsage==null)
			fieldUsage = new HashMap<>();

		return fieldUsage;
	}

	public Set<String> getFieldsAccessed() {
		if(this.fieldUsage==null)
			fieldUsage = new HashMap<>();

		return fieldUsage.keySet();
	}

	public boolean isConstructor() {
		return isConstructor;
	}

	public int getLogStatementsQty() {
		return logStatementsQty;
	}

	public void setLogStatementsQty(int logStatementsQty) {
		this.logStatementsQty = logStatementsQty;
	}

	public void setHasJavadoc(boolean hasJavadoc) {
		this.hasJavadoc = hasJavadoc;
	}

	public boolean getHasJavadoc() {
		return hasJavadoc;
	}

	public void setMethodInvocations(Set<String> methodInvocations) {
		this.methodInvocations = methodInvocations;
	}

	public Set<String> getMethodInvocations() {
		if(methodInvocations==null)
			methodInvocations = new HashSet<>();

		return methodInvocations;
	}

	public void setMethodInvocationLocal(Set<String> methodInvocationsLocal) {
		this.methodInvocationsLocal = methodInvocationsLocal;
	}

	public Set<String> getMethodInvocationsLocal() {
		return methodInvocationsLocal;
	}

	public void setMethodInvocationsIndirectLocal(Map<String, Set<String>> methodInvocationsIndirect) {
		this.methodInvocationsIndirectLocal = methodInvocationsIndirect;
	}

	public Map<String, Set<String>> getMethodInvocationsIndirectLocal() {
		return methodInvocationsIndirectLocal;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CKMethodResult that = (CKMethodResult) o;
		return startLine == that.startLine &&
				methodName.equals(that.methodName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(methodName, startLine);
	}

}
