package csns.model.news.dao;

import java.util.List;

import csns.model.academics.Department;
import csns.model.news.News;

public interface NewsDao {

    News getNews( Long id );

    List<News> getNews( Department department );

    News saveNews( News news );

}
