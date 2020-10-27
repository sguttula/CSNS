package csns.model.site;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class InfoEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String value;

    public InfoEntry()
    {
    }

    public InfoEntry( String name, String value )
    {
        this.name = name;
        this.value = value;
    }

    public InfoEntry clone()
    {
        InfoEntry newInfoEntry = new InfoEntry();
        newInfoEntry.name = name;
        newInfoEntry.value = value;
        return newInfoEntry;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue( String value )
    {
        this.value = value;
    }

}
