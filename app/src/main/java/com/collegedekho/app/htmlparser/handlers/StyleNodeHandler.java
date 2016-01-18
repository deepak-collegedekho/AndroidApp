package com.collegedekho.app.htmlparser.handlers;

import android.text.SpannableStringBuilder;
import android.util.Log;

import com.collegedekho.app.htmlparser.SpanStack;
import com.collegedekho.app.htmlparser.TagNodeHandler;
import com.collegedekho.app.htmlparser.css.CSSCompiler;
import com.collegedekho.app.htmlparser.cssparser.CSSParser;
import com.collegedekho.app.htmlparser.cssparser.Rule;

import org.htmlcleaner.ContentNode;
import org.htmlcleaner.TagNode;

/**
 * TagNodeHandler that reads <style> blocks and parses the CSS rules within.
 */
public class StyleNodeHandler extends TagNodeHandler {

    @Override
    public void handleTagNode(TagNode node, SpannableStringBuilder builder, int start, int end, SpanStack spanStack) {

        if ( getSpanner().isAllowStyling() ) {

            if ( node.getChildren().size() == 1 ) {
                Object childNode = node.getChildren().get(0);

                if ( childNode instanceof ContentNode ) {
                    parseCSSFromText( ( (ContentNode) childNode ).getContent(),
                            spanStack );
                }
            }
        }

    }

    private void parseCSSFromText( StringBuilder text, SpanStack spanStack ) {
        try {
            for ( Rule rule: CSSParser.parse( text.toString() ) ) {
                spanStack.registerCompiledRule(CSSCompiler.compile(rule, getSpanner()));
            }
        } catch ( Exception e ) {
            Log.e( "StyleNodeHandler", "Unparseable CSS definition", e );
        }
    }

    @Override
    public boolean rendersContent() {
        return true;
    }
}
