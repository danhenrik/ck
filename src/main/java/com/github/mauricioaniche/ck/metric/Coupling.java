package com.github.mauricioaniche.ck.metric;

import com.github.mauricioaniche.ck.CKClassResult;
import com.github.mauricioaniche.ck.CKMethodResult;
import com.github.mauricioaniche.ck.util.BaseCouplingMetric;
import com.github.mauricioaniche.ck.util.JDTUtils;

import org.eclipse.jdt.core.dom.*;

public class Coupling extends BaseCouplingMetric {

	private CouplingExtras extras;
	private String className;
	private String methodName;
	
	public Coupling() {
		this.extras = CouplingExtras.getInstance();
	}

	@Override
	public void visit(MethodInvocation node) {
		
		IMethodBinding binding = node.resolveMethodBinding();
		if(binding!=null) {
			if(this.className != null) {
				coupleTo(binding.getDeclaringClass());
			} else if(this.methodName != null) {
				coupleTo(binding);
			}
		}

	}
	
	private void coupleTo(IMethodBinding binding) {
		
		if(binding == null)
			return;
		
		String methodNameInvoked = JDTUtils.getQualifiedMethodFullName(binding);
		
		if (methodNameInvoked.equals("null"))
			return;

		if (isFromJava(methodNameInvoked))
			return;
		
		addToSet(methodNameInvoked);
		
	}

	@Override
	public void visit(ClassInstanceCreation node) {
		if(this.className != null) {
			coupleTo(node.getType());
		} else if(this.methodName != null) {
			IMethodBinding binding = node.resolveConstructorBinding();
			coupleTo(binding);
		}
	}

	@Override
	protected void addToSet(String name) {
		if(className != null){
			this.extras.addToSetClassIn(name, this.className);
			this.extras.addToSetClassOut(this.className, name);
		} else {
			this.extras.addToSetMethodIn(name, this.methodName);
			this.extras.addToSetMethodOut(this.methodName, name);
		}
	}

	@Override
	protected Boolean shouldCouple() {
		return this.className != null;
	}

	@Override
	public void setResult(CKClassResult result) {
		// This override is empty on purpose because we need to implement the interface, but we don't want the underlying implementation collaterals.
	}

	@Override
	public void setResult(CKMethodResult result) {
		// This override is empty on purpose because we need to implement the interface, but we don't want the underlying implementation collaterals.
	}
	
	@Override
	public void setClassName(String className) {
		this.className = className;
	}
	
	@Override
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
}
