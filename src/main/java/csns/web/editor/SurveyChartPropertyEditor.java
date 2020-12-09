package csns.web.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import csns.model.survey.SurveyChart;
import csns.model.survey.dao.SurveyChartDao;

@Component
@Scope("prototype")
public class SurveyChartPropertyEditor extends PropertyEditorSupport {

    @Autowired
    private SurveyChartDao surveyChartDao;

    @Override
    public void setAsText( String text ) throws IllegalArgumentException
    {
        if( StringUtils.hasText( text ) )
            setValue( surveyChartDao.getSurveyChart( Long.valueOf( text ) ) );
    }

    @Override
    public String getAsText()
    {
        SurveyChart chart = (SurveyChart) getValue();
        return chart != null ? chart.getId().toString() : "";
    }

}
