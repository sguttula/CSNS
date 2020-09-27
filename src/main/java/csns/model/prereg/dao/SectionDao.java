package csns.model.prereg.dao;

import csns.model.prereg.Section;

public interface SectionDao {

    Section getSection( Long id );

    Section saveSection( Section section );

}
