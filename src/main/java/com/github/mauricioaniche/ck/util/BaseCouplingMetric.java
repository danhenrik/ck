package com.github.mauricioaniche.ck.util;

import com.github.mauricioaniche.ck.metric.CKASTVisitor;
import com.github.mauricioaniche.ck.metric.ClassLevelMetric;
import com.github.mauricioaniche.ck.metric.MethodLevelMetric;
import org.eclipse.jdt.core.dom.*;

import java.util.List;

public abstract class BaseCouplingMetric implements CKASTVisitor, ClassLevelMetric, MethodLevelMetric {

    @Override
    public void visit(VariableDeclarationStatement node) {
        if (shouldCouple()) {
            coupleTo(node.getType());
        }
    }

    @Override
    public void visit(ArrayCreation node) {
        if (shouldCouple()) {
            coupleTo(node.getType());
        }
    }

    @Override
    public void visit(FieldDeclaration node) {
        if (shouldCouple()) {
            coupleTo(node.getType());
        }
    }

    @Override
    public void visit(ReturnStatement node) {
        if (shouldCouple()) {
            if (node.getExpression() != null) {
                coupleTo(node.getExpression().resolveTypeBinding());
            }
        }
    }

    @Override
    public void visit(TypeLiteral node) {
        if (shouldCouple()) {
            coupleTo(node.getType());
        }
    }

    @Override
    public void visit(ThrowStatement node) {
        if (shouldCouple()) {
            if (node.getExpression() != null)
                coupleTo(node.getExpression().resolveTypeBinding());
        }
    }

    @Override
    public void visit(TypeDeclaration node) {
        if (!shouldCouple()) {
            return;
        }
        ITypeBinding resolvedType = node.resolveBinding();

        if (resolvedType != null) {
            ITypeBinding binding = resolvedType.getSuperclass();
            if (binding != null)
                coupleTo(binding);

            for (ITypeBinding interfaces : resolvedType.getInterfaces()) {
                coupleTo(interfaces);
            }
        } else {
            coupleTo(node.getSuperclassType());
            List<Type> list = node.superInterfaceTypes();
            list.forEach(this::coupleTo);
        }
    }

    @Override
    public void visit(MethodDeclaration node) {
        if (!shouldCouple()) {
            return;
        }
        IMethodBinding resolvedMethod = node.resolveBinding();
        if (resolvedMethod != null) {

            coupleTo(resolvedMethod.getReturnType());

            for (ITypeBinding param : resolvedMethod.getParameterTypes()) {
                coupleTo(param);
            }
        } else {
            coupleTo(node.getReturnType2());
            List<TypeParameter> list = node.typeParameters();
            list.forEach(x -> coupleTo(x.getName()));
        }
    }

    @Override
    public void visit(CastExpression node) {
        if (shouldCouple()) {
            coupleTo(node.getType());
        }

    }

    @Override
    public void visit(InstanceofExpression node) {
        if (shouldCouple()) {
            coupleTo(node.getRightOperand());
            coupleTo(node.getLeftOperand().resolveTypeBinding());
        }
    }

    @Override
    public void visit(NormalAnnotation node) {
        if (shouldCouple()) {
            coupleTo(node);
        }
    }

    @Override
    public void visit(MarkerAnnotation node) {
        if (shouldCouple()) {
            coupleTo(node);
        }
    }

    @Override
    public void visit(SingleMemberAnnotation node) {
        if (shouldCouple()) {
            coupleTo(node);
        }
    }

    @Override
    public void visit(ParameterizedType node) {
        if (!shouldCouple()) {
            return;
        }

        try {
            ITypeBinding binding = node.resolveBinding();
            if (binding != null) {

                coupleTo(binding);

                for (ITypeBinding types : binding.getTypeArguments()) {
                    coupleTo(types);
                }
            } else {
                coupleTo(node.getType());
            }
        } catch (NullPointerException e) {
            // TODO: handle exception
        }

    }

    protected void coupleTo(Annotation type) {
        if (!shouldCouple()) {
            return;
        }
        ITypeBinding resolvedType = type.resolveTypeBinding();
        if (resolvedType != null)
            coupleTo(resolvedType);
        else {
            addToSet(type.getTypeName().getFullyQualifiedName());
        }
    }

    protected void coupleTo(Type type) {
        if (type == null) {
            return;
        }

        if (!shouldCouple()) {
            return;
        }

        ITypeBinding resolvedBinding = type.resolveBinding();
        if (resolvedBinding != null)
            coupleTo(resolvedBinding);
        else {
            if (type instanceof SimpleType) {
                SimpleType castedType = (SimpleType) type;
                addToSet(castedType.getName().getFullyQualifiedName());
            } else if (type instanceof QualifiedType) {
                QualifiedType castedType = (QualifiedType) type;
                addToSet(castedType.getName().getFullyQualifiedName());
            } else if (type instanceof NameQualifiedType) {
                NameQualifiedType castedType = (NameQualifiedType) type;
                addToSet(castedType.getName().getFullyQualifiedName());
            } else if (type instanceof ParameterizedType) {
                ParameterizedType castedType = (ParameterizedType) type;
                coupleTo(castedType.getType());
            } else if (type instanceof WildcardType) {
                WildcardType castedType = (WildcardType) type;
                coupleTo(castedType.getBound());
            } else if (type instanceof ArrayType) {
                ArrayType castedType = (ArrayType) type;
                coupleTo(castedType.getElementType());
            } else if (type instanceof IntersectionType) {
                IntersectionType castedType = (IntersectionType) type;
                List<Type> types = castedType.types();
                types.forEach(this::coupleTo);
            } else if (type instanceof UnionType) {
                UnionType castedType = (UnionType) type;
                List<Type> types = castedType.types();
                types.forEach(this::coupleTo);
            }
        }
    }

    protected void coupleTo(SimpleName name) {
        if (shouldCouple()) {
            addToSet(name.getFullyQualifiedName());
        }
    }

    protected void coupleTo(ITypeBinding binding) {
        if (!shouldCouple()) {
            return;
        }
        if (binding == null)
            return;
        if (binding.isWildcardType())
            return;
        if (binding.isNullType())
            return;

        String type = binding.getQualifiedName();
        if (type.equals("null"))
            return;

        if (isFromJava(type) || binding.isPrimitive())
            return;


        String cleanedType = cleanClassName(type);
        addToSet(cleanedType);
    }

    private String cleanClassName(String type) {
        // remove possible array(s) in the class name
        String cleanedType = type.replace("[]", "").replace("\\$", ".");

        // remove generics declaration, let's stype with the type
        if(cleanedType.contains("<"))
            cleanedType = cleanedType.substring(0, cleanedType.indexOf("<"));

        return cleanedType;
    }

    protected boolean isFromJava(String type) {
        return type.startsWith("java.") || type.startsWith("javax.");
    }

    protected abstract void addToSet(String name);

    protected abstract Boolean shouldCouple();
}
