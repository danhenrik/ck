package com.github.mauricioaniche.ck.metric;

import com.github.mauricioaniche.ck.CKClassResult;
import com.github.mauricioaniche.ck.CKMethodResult;
import com.github.mauricioaniche.ck.util.BaseCouplingMetric;
import org.eclipse.jdt.core.dom.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CBO extends BaseCouplingMetric {

	private final Set<String> coupling = new HashSet<>();

	@Override
	public void visit(MethodInvocation node) {

		IMethodBinding binding = node.resolveMethodBinding();
		if(binding!=null)
			coupleTo(binding.getDeclaringClass());

	}

	@Override
	public void visit(ClassInstanceCreation node) {
		coupleTo(node.getType());
	}

	@Override
	protected void addToSet(String name) {
		this.coupling.add(name);
	}

	@Override
	protected Boolean shouldCouple() {
		return true;
	}

	@Override
	public void setResult(CKClassResult result) {
		clean();
		result.setCbo(getValue());
	}

	// given that some resolvings might fail, we remove types that might
	// had appeared here twice.
	// e.g. if the set contains 'A.B.Class' and 'Class', it is likely that
	// 'Class' == 'A.B.Class'
	private void clean() {
		Set<String> singleQualifiedTypes = coupling.stream().filter(x -> !x.contains(".")).collect(Collectors.toSet());

		for(String singleQualifiedType : singleQualifiedTypes) {
			long count = coupling.stream().filter(x -> x.endsWith("." + singleQualifiedType)).count();

			boolean theSameFullyQualifiedTypeExists = count > 0;
			if(theSameFullyQualifiedTypeExists)
				coupling.remove(singleQualifiedType);
		}
	}

	@Override
	public void setResult(CKMethodResult result) {
		clean();
		result.setCbo(getValue());
	}

	private int getValue() {
		return coupling.size();
	}
}
