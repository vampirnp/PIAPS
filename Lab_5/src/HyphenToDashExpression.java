public class HyphenToDashExpression implements Expression {
    @Override
    public String interpret(String context) {
        return context.replaceAll("\\s-\\s", " — ");
    }
}
