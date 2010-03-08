package com.ora.jsp.beans.motd;

import java.util.*;

/**
 * This is an example of a bean with one writeable property for
 * selecting a message category and one readable property that
 * cycles through messages in the selected category.
 *
 * @author Hans Bergsten, Gefion software <hans@gefionsoftware.com>
 * @version 1.0
 */
public class MixedMessageBean implements java.io.Serializable {
    private static int quoteIndex = -1;
    private static int thoughtIndex = -1;
    private List quotes;
    private List thoughts;
    private String category;

    /**
     * Contructor that initializes the data structures that hold
     * the messages.
     */
    public MixedMessageBean() {
        initMessageLists();
    }

    /**
     * Sets the message category, one of "quotes" or "thoughts".
     */
    public void setCategory(String category) {
	this.category = category;
    }

    /**
     * Returns a new message from the selected category every time
     * it's called, cycling through all available messages.
     */
    public String getMessage() {
	String msg = "No valid category selected";
	if ("quotes".equals(category)) {
	    quoteIndex++;
	    if (quoteIndex > quotes.size() - 1) {
		quoteIndex = 0;
	    }
	    msg = (String) quotes.get(quoteIndex);
	}
	else if ("thoughts".equals(category)) {
	    thoughtIndex++;
	    if (thoughtIndex > thoughts.size() - 1) {
		thoughtIndex = 0;
	    }
	    msg = (String) thoughts.get(thoughtIndex);
	}
	return msg;
    }

    /**
     * Creates and initalizes the data structures that hold the messages.
     */
    private void initMessageLists() {
        quotes = new ArrayList();
        quotes.add("The most likely way for the world to be destroyed, most experts agree, is by accident. That's where we come in; we're computer professionals. We cause accidents. --Nathaniel Borenstein");
	quotes.add("Any sufficiently advanced bug is indistinguishable from a feature. --Kulawiec");
	quotes.add("A computer lets you make mistakes faster than any other invention in human history, with the possible exception of handguns and tequila. --D.W. McArthur");
	quotes.add("The goal of Computer Science is to build something that will last at least until we've finished building it. --Unknown");

	thoughts = new ArrayList();
	thoughts.add("Maybe in order to understand mankind, we have to look at the word itself: 'Mankind'. Basically, it's made up of two separate words - 'mank' and 'ind'. What do these words mean ? It's a mystery, and that's why so is mankind.");
	thoughts.add("If you're robbing a bank and you're pants fall down, I think it's okay to laugh and to let the hostages laugh too, because, come on, life is funny.");
	thoughts.add("We tend to scoff at the beliefs of the ancients. But we can't scoff at them personally, to their faces, and this is what annoys me.");
	thoughts.add("If trees could scream, would we be so cavalier about cutting them down? We might, if they screamed all the time, for no good reason.");
    }
}
