package csns.model.news.dao.jpa;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.academics.Department;
import csns.model.news.News;
import csns.model.news.dao.NewsDao;

@Repository
public class NewsDaoImpl implements NewsDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public News getNews( Long id )
    {
        return entityManager.find( News.class, id );
    }

    @Override
    public List<News> getNews( Department department )
    {
        String query = "from News where department = :department "
            + "and expireDate > :now order by id desc";

        return entityManager.createQuery( query, News.class )
            .setParameter( "department", department )
            .setParameter( "now", Calendar.getInstance() )
            .getResultList();
    }

    @Override
    @Transactional
    public News saveNews( News news )
    {
        return entityManager.merge( news );
    }

}
