package csns.web.support;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.support.RequestDataValueProcessor;

public class ConversationIdRequestProcessor
    implements RequestDataValueProcessor {

    @Override
    public Map<String, String> getExtraHiddenFields(
        HttpServletRequest request )
    {
        Map<String, String> hiddenFields = new HashMap<String, String>();
        if( request.getAttribute(
            ConversationalSessionAttributeStore.CID_FIELD ) != null )
        {
            hiddenFields.put( ConversationalSessionAttributeStore.CID_FIELD,
                request
                    .getAttribute(
                        ConversationalSessionAttributeStore.CID_FIELD )
                    .toString() );
        }
        return hiddenFields;
    }

    @Override
    public String processAction( HttpServletRequest request, String action,
        String method )
    {
        return action;
    }

    @Override
    public String processFormFieldValue( HttpServletRequest request,
        String name, String value, String type )
    {
        return value;
    }

    @Override
    public String processUrl( HttpServletRequest request, String url )
    {
        return url;
    }

}
