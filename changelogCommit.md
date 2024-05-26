NOC.java - tornei Name uma variável local
CKVisitor (329) - substitui um ternário aninhado por ifs
MethodInvocationsLocal (42) - subsitui "new HashMap()" por "new HashMap<>()", mas poderia ser "new HashMap<String, Set<String>>()"
DIT (9) - renomeado "dit" (nome identico a classe) por "ditObject"
CouplingTest - Teste parametrizado "countCoupling" substitui "ignoreJavaTypes", "countDifferentPossibilitiesOfDependencies", "countEvenWhenNotResolved", "countInterfacesAndInheritances", "countClassCreations", "countMethodParameters", "fullOfNonResolvedTypes"
(observação: pode haver uma forma de simplificar os testes "countFanIn", "countFanOut" e "countCBOModified", porem como nãoforam detectadas como issue não alterei)
Coupling (56 & 70) - If's aninhados colocados em uma única condicional
MethodLevelFieldUsageCount (60) - substitui o containsKey por putIfAbsent (o sonarqube sugere computeIfAbsent, mas foi indicado pelo intellij)
NOCExtras (23) - Identico ao anterior
CKVisitor (160) - removi a linha que dá valor ao stringlist, por não ter uso
NOC(36) - removido "type" não usado
JDTUtils (130) - removido int argumentCount
CKVisitor (307) - removido código comentado return classLevelMetrics.call();
*         (316) - removido codigo comentado return methodLevelMetrics.call();
WMC (16) - Sonar identificou erroneamente como commented out code
MethodLevelFieldUsageCount (38 & 58-66) - alterado "var" para "temporaryVariable"
VariableOrParameterUsageCount (36-44) - alterado "var" para "temporaryVariable"
FileUtils (10) - Adicionado construtor com throw new IllegalStateException("Utility class");
JDTUtils (21) - Adicionado construtor com throw new IllegalStateException("Utility class");
LOCCalculator (13) - Adicionado construtor com throw new IllegalStateException("Utility class");
WordCounter (8) - Adicionado construtor com throw new IllegalStateException("Utility class");

Logging:
Adicionado diretório main/resources
Adicionado arquivo logging.properties
Runner (19) - substituido system.out por logger.info
Runner (64) - substituido system.err por logger.log
Runner (80) - substituido system.out por logger.info
ATENÇÃO - essas linhas originais, para teste, foram comentadas, remover quando funcional