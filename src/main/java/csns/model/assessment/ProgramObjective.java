package csns.model.assessment;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * ABET Educational Objective.
 */
@Entity
@Table(name = "assessment_program_objectives")
public class ProgramObjective implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;

    @Column(name = "objective_index", nullable = false)
    private int index;

    private String text;

    private String description;

    public ProgramObjective()
    {
    }

    public ProgramObjective( Program program )
    {
        this.program = program;
        this.index = program.getObjectives().size();
    }

    public ProgramObjective( Program program, String text )
    {
        this( program );
        this.text = text;
    }

    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public Program getProgram()
    {
        return program;
    }

    public void setProgram( Program program )
    {
        this.program = program;
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex( int index )
    {
        this.index = index;
    }

    public String getText()
    {
        return text;
    }

    public void setText( String text )
    {
        this.text = text;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

}
