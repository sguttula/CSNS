package csns.web.tag;

import org.apache.taglibs.standard.tag.common.core.Util;

import csns.model.qa.ChoiceAnswer;
import csns.model.qa.ChoiceQuestion;

public class Functions {

    public static boolean isSelectionCorrect( ChoiceQuestion question, Integer i )
    {
        return question.getCorrectSelections().contains( i );
    }

    public static boolean isChoiceSelected( ChoiceAnswer answer, Integer i )
    {
        return answer.getSelections().contains( i );
    }

    public static boolean endsWith( String s, String suffix )
    {
        return s.endsWith( suffix );
    }

    public static String textToHtml( String text )
    {
        if( text == null || text.length() == 0 ) return "";
        return Util.escapeXml( text ).replaceAll( "\r?\n", "<br />" );
    }

}
