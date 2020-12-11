package csns.web.support;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.support.SessionAttributeStore;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

public class ConversationalSessionAttributeStore implements SessionAttributeStore, InitializingBean {

    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
    private static final Logger logger = LoggerFactory.getLogger(ConversationalSessionAttributeStore.class);

    private int keepAliveConversations = 10;

    public final static String CID_FIELD = "_cid";
    public final static String SESSION_MAP = "sessionConversationMap";

    @Override
    public void storeAttribute(WebRequest request, String attributeName, Object attributeValue) {
	Assert.notNull(request, "WebRequest must not be null");
	Assert.notNull(attributeName, "Attribute name must not be null");
	Assert.notNull(attributeValue, "Attribute value must not be null");

	String cId = getConversationId(request);
	if (cId == null || cId.trim().length() == 0) {
	    cId = UUID.randomUUID().toString();
	}
	request.setAttribute(CID_FIELD, cId, WebRequest.SCOPE_REQUEST);
	logger.debug("storeAttribute - storing bean reference for (" + attributeName + ") for conversation (" + cId + ").");
	store(request, attributeName, attributeValue, cId);
    }

    @Override
    public Object retrieveAttribute(WebRequest request, String attributeName) {
	Assert.notNull(request, "WebRequest must not be null");
	Assert.notNull(attributeName, "Attribute name must not be null");

	if (getConversationId(request) != null) {
	    if (logger.isDebugEnabled()) {
		logger.debug("retrieveAttribute - retrieving bean reference for (" + attributeName + ") for conversation ("
		        + getConversationId(request) + ").");
	    }
	    return getConversationStore(request, getConversationId(request)).get(attributeName);
	} else {
	    return null;
	}
    }

    @Override
    public void cleanupAttribute(WebRequest request, String attributeName) {
	Assert.notNull(request, "WebRequest must not be null");
	Assert.notNull(attributeName, "Attribute name must not be null");

	if (logger.isDebugEnabled()) {
	    logger.debug("cleanupAttribute - removing bean reference for (" + attributeName + ") from conversation ("
		    + getConversationId(request) + ").");
	}

	Map<String, Object> conversationStore = getConversationStore(request, getConversationId(request));
	conversationStore.remove(attributeName);

	// Delete the conversation store from the session if empty
	if (conversationStore.isEmpty()) {
	    getSessionConversationsMap(request).remove(getConversationId(request));
	}
    }

    /**
     * Retrieve a specific conversation's map of objects from the session. Will
     * create the conversation map if it does not exist.
     * 
     * The conversation map is stored inside a session map - which is a map of
     * maps. If this does not exist yet- it will be created too.
     * 
     * @param request
     *            - the incoming request
     * @param conversationId
     *            - the conversation id we are dealing with
     * @return - the conversation's map
     */
    private Map<String, Object> getConversationStore(WebRequest request, String conversationId) {

	Map<String, Object> conversationMap = getSessionConversationsMap(request).get(conversationId);
	if (conversationId != null && conversationMap == null) {
	    conversationMap = new HashMap<String, Object>();
	    getSessionConversationsMap(request).put(conversationId, conversationMap);
	}
	return conversationMap;
    }

    private LinkedHashMap<String, Map<String, Object>> getSessionConversationsMap(WebRequest request) {
	@SuppressWarnings("unchecked")
	LinkedHashMap<String, Map<String, Object>> sessionMap = (LinkedHashMap<String, Map<String, Object>>) request.getAttribute(
	        SESSION_MAP, WebRequest.SCOPE_SESSION);
	if (sessionMap == null) {
	    sessionMap = new LinkedHashMap<String, Map<String, Object>>();
	    request.setAttribute(SESSION_MAP, sessionMap, WebRequest.SCOPE_SESSION);
	}
	return sessionMap;
    }

    private void store(WebRequest request, String attributeName, Object attributeValue, String cId) {
	LinkedHashMap<String, Map<String, Object>> sessionConversationsMap = getSessionConversationsMap(request);
	if (keepAliveConversations > 0 && sessionConversationsMap.size() >= keepAliveConversations
	        && !sessionConversationsMap.containsKey(cId)) {
	    // clear oldest conversation
	    String key = sessionConversationsMap.keySet().iterator().next();
	    sessionConversationsMap.remove(key);
	}
	getConversationStore(request, cId).put(attributeName, attributeValue);

    }

    public int getKeepAliveConversations() {
	return keepAliveConversations;
    }

    public void setKeepAliveConversations(int numConversationsToKeep) {
	keepAliveConversations = numConversationsToKeep;
    }

    /**
     * Helper method to get conversation id from the web request
     * 
     * @param request
     *            - Incoming request
     * @return - the conversationId (note that this is a request parameter, and
     *         only gets there on form submit)
     */
    private String getConversationId(WebRequest request) {
	String cid = request.getParameter(CID_FIELD);
	if (cid == null) {
	    cid = (String) request.getAttribute(CID_FIELD, WebRequest.SCOPE_REQUEST);
	}
	return cid;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
	requestMappingHandlerAdapter.setSessionAttributeStore(this);
    }

}
