public class TabFixExpression implements Expression {
    @Override
    public String interpret(String context) {
        return context.replaceAll("\t", " ");
    }
}
