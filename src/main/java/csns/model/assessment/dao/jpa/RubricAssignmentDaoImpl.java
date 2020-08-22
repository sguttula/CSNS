package csns.model.assessment.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.assessment.RubricAssignment;
import csns.model.assessment.dao.RubricAssignmentDao;

@Repository
public class RubricAssignmentDaoImpl implements RubricAssignmentDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @PostAuthorize("returnObject.section.isInstructor(principal) or returnObject.section.isEnrolled(principal) or returnObject.isExternalEvaluator(principal) or principal.admin")
    public RubricAssignment getRubricAssignment( Long id )
    {
        return entityManager.find( RubricAssignment.class, id );
    }

    @Override
    @Transactional
    public RubricAssignment saveRubricAssignment( RubricAssignment assignment )
    {
        return entityManager.merge( assignment );
    }

}
