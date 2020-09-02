package csns.model.core;

/**
 * A Subscribable is something a user can subscribe to, like a forum, a forum
 * post, or a wiki page.
 */
public interface Subscribable {

    Long getId();

    String getName();

    String getType();

}
