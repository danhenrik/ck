package com.github.mauricioaniche.ck;

import com.github.mauricioaniche.ck.metric.CouplingExtras;

public abstract class CKResult {
    private int wmc;
    private int cbo;
    private int cboModified = -1;
    private int fanin = -1;
    private int fanout = -1;
    private int rfc;
    private int loc;
    private int loopQty;
    private int comparisonsQty;
    private int tryCatchQty;
    private int parenthesizedExpsQty;
    private int stringLiteralsQty;
    private int numbersQty;
    private int assignmentsQty;
    private int mathOperationsQty;
    private int anonymousClassesQty;
    private int innerClassesQty;
    private int lambdasQty;
    private int uniqueWordsQty;
    private int returnQty;
    private int variablesQty;
    private int maxNestedBlocks;
    private int modifiers;

    protected CouplingExtras couplingExtras = CouplingExtras.getInstance();

    protected CKResult(int modifiers) {
        this.modifiers = modifiers;
    }

    public void setWmc(int cc) {
        this.wmc = cc;
    }
    public int getWmc() {
        return wmc;
    }


    public int getCbo() {
        return cbo;
    }

    public void setCbo(int cbo) {
        this.cbo = cbo;
    }

    public int getCboModified() {
        if(this.cboModified == -1){
            this.setCboModified(getValueCBO());
        }
        return cboModified;
    }

    public void setCboModified(int cboModified) {
        this.cboModified = cboModified;
    }

    public int getFanin() {

        if(this.fanin == -1){
            this.setFanin(getValueFanIn());
        }

        return fanin;
    }

    public void setFanin(int fanin) {
        this.fanin = fanin;
    }

    public int getFanout() {

        if(this.fanout == -1){
            this.setFanout(getValueFanOut());
        }

        return fanout;
    }

    public void setFanout(int fanout) {
        this.fanout = fanout;
    }

    public int getRfc() {
        return rfc;
    }

    public void setRfc(int rfc) {
        this.rfc = rfc;
    }

    public int getLoc() {
        return loc;
    }

    public void setLoc(int loc) {
        this.loc = loc;
    }

    public int getLoopQty() {
        return loopQty;
    }

    public void setLoopQty(int loopQty) {
        this.loopQty = loopQty;
    }

    public int getComparisonsQty() {
        return comparisonsQty;
    }

    public void setComparisonsQty(int comparisonsQty) {
        this.comparisonsQty = comparisonsQty;
    }

    public int getTryCatchQty() {
        return tryCatchQty;
    }

    public void setTryCatchQty(int tryCatchQty) {
        this.tryCatchQty = tryCatchQty;
    }

    public int getParenthesizedExpsQty() {
        return parenthesizedExpsQty;
    }

    public void setParenthesizedExpsQty(int parenthesizedExpsQty) {
        this.parenthesizedExpsQty = parenthesizedExpsQty;
    }

    public int getStringLiteralsQty() {
        return stringLiteralsQty;
    }

    public void setStringLiteralsQty(int stringLiteralsQty) {
        this.stringLiteralsQty = stringLiteralsQty;
    }

    public int getNumbersQty() {
        return numbersQty;
    }

    public void setNumbersQty(int numbersQty) {
        this.numbersQty = numbersQty;
    }

    public int getAssignmentsQty() {
        return assignmentsQty;
    }

    public void setAssignmentsQty(int assignmentsQty) {
        this.assignmentsQty = assignmentsQty;
    }

    public int getMathOperationsQty() {
        return mathOperationsQty;
    }

    public void setMathOperationsQty(int mathOperationsQty) {
        this.mathOperationsQty = mathOperationsQty;
    }

    public int getAnonymousClassesQty() {
        return anonymousClassesQty;
    }

    public void setAnonymousClassesQty(int anonymousClassesQty) {
        this.anonymousClassesQty = anonymousClassesQty;
    }

    public int getInnerClassesQty() {
        return innerClassesQty;
    }

    public void setInnerClassesQty(int innerClassesQty) {
        this.innerClassesQty = innerClassesQty;
    }

    public int getLambdasQty() {
        return lambdasQty;
    }

    public void setLambdasQty(int lambdasQty) {
        this.lambdasQty = lambdasQty;
    }

    public int getUniqueWordsQty() {
        return uniqueWordsQty;
    }

    public void setUniqueWordsQty(int uniqueWordsQty) {
        this.uniqueWordsQty = uniqueWordsQty;
    }

    public void setReturnQty(int returnQty) {
        this.returnQty = returnQty;
    }

    public int getReturnQty() {
        return returnQty;
    }

    public void setVariablesQty(int variablesQty) {
        this.variablesQty = variablesQty;
    }

    public int getVariablesQty() {
        return variablesQty;
    }

    public void setMaxNestedBlocks(int maxNestedBlocks) {
        this.maxNestedBlocks = maxNestedBlocks;
    }

    public int getMaxNestedBlocks() {
        return maxNestedBlocks;
    }

    /**
     * public/static/private and other org.eclipse.jdt.core.dom.Modifier modifiers
     *
     * @see org.eclipse.jdt.core.dom.Modifier to decode int
     * @return
     */
    public int getModifiers() {
        return modifiers;
    }

    protected abstract int getValueFanOut();

    protected abstract int getValueFanIn();

    protected abstract int getValueCBO();
}
