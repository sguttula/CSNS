package csns.model.site;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import csns.model.core.Resource;
import csns.model.core.ResourceType;

@Entity
@Table(name = "site_items")
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "resource_id")
    private Resource resource;

    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST },
        fetch = FetchType.EAGER)
    @JoinTable(name = "site_item_additional_resources",
        joinColumns = @JoinColumn(name = "item_id"),
        inverseJoinColumns = @JoinColumn(name = "resource_id"))
    @OrderColumn(name = "resource_order")
    private List<Resource> additionalResources;

    @Column(nullable = false)
    private boolean hidden;

    @Column(nullable = false)
    private boolean deleted;

    public Item()
    {
        resource = new Resource( ResourceType.FILE );
        additionalResources = new ArrayList<Resource>();
        hidden = false;
        deleted = false;
    }

    public Item clone()
    {
        Item newItem = new Item();
        newItem.hidden = true;

        newItem.resource = resource.clone();
        for( Resource additionalResource : additionalResources )
            newItem.additionalResources.add( additionalResource.clone() );

        return newItem;
    }

    public String getName()
    {
        return resource.getName();
    }

    public boolean toggle()
    {
        hidden = !hidden;
        return hidden;
    }

    public void delete()
    {
        deleted = true;
        resource.delete();
    }

    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public Resource getResource()
    {
        return resource;
    }

    public void setResource( Resource resource )
    {
        this.resource = resource;
    }

    public List<Resource> getAdditionalResources()
    {
        return additionalResources;
    }

    public void setAdditionalResources( List<Resource> additionalResources )
    {
        this.additionalResources = additionalResources;
    }

    public boolean isHidden()
    {
        return hidden;
    }

    public void setHidden( boolean hidden )
    {
        this.hidden = hidden;
    }

    public boolean isDeleted()
    {
        return deleted;
    }

    public void setDeleted( boolean deleted )
    {
        this.deleted = deleted;
    }

}
