package com.warrior.informationsearch.searcher.tree;

import com.warrior.informationsearch.searcher.queryparser.QueryLexer;
import com.warrior.informationsearch.searcher.queryparser.QueryParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.List;
import java.util.Map;

/**
 * Created by warrior on 08.03.15.
 */
public abstract class Tree {

    public abstract List<Integer> search(Map<String, List<Integer>> invertedIndex);

    private static final BaseErrorListener ERROR_LISTENER = new BaseErrorListener() {
        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
            throw new ParseException(msg, e);
        }
    };

    public static Tree parse(String query) {
        ANTLRInputStream inputStream = new ANTLRInputStream(query);
        QueryLexer lexer = new QueryLexer(inputStream);
        lexer.addErrorListener(ERROR_LISTENER);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        QueryParser parser = new QueryParser(tokenStream);
        parser.addErrorListener(ERROR_LISTENER);
        try {
            QueryParser.DisContext context = parser.dis();
            return buildTree(context);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Tree buildTree(ParseTree tree) {
        if (tree instanceof QueryParser.DisContext) {
            return or(tree);
        }
        if (tree instanceof QueryParser.ConContext) {
            return and(tree);
        }
        if (tree instanceof QueryParser.TermContext) {
            return term(tree);
        }
        return null;
    }

    private static Tree or(ParseTree tree) {
        if (tree.getChildCount() == 1) {
            return buildTree(tree.getChild(0));
        }
        Tree left = buildTree(tree.getChild(0));
        Tree right = buildTree(tree.getChild(2));
        return new Or(left, right);
    }

    private static Tree and(ParseTree tree) {
        if (tree.getChildCount() == 1) {
            return buildTree(tree.getChild(0));
        }
        Tree left = buildTree(tree.getChild(0));
        Tree right = buildTree(tree.getChild(2));
        return new And(left, right);
    }

    private static Tree term(ParseTree tree) {
        if (tree.getChildCount() == 1) {
            return new Term(tree.getText().toLowerCase());
        }
        return buildTree(tree.getChild(1));
    }

    private static class ParseException extends RuntimeException {
        public ParseException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
