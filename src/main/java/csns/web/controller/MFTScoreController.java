package csns.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import csns.helper.highcharts.Chart;
import csns.helper.highcharts.Series;
import csns.model.academics.Department;
import csns.model.academics.dao.DepartmentDao;
import csns.model.assessment.MFTDistribution;
import csns.model.assessment.MFTDistributionType;
import csns.model.assessment.MFTScore;
import csns.model.assessment.dao.MFTDistributionDao;
import csns.model.assessment.dao.MFTDistributionTypeDao;
import csns.model.assessment.dao.MFTScoreDao;

@Controller
public class MFTScoreController {

    @Autowired
    private MFTScoreDao mftScoreDao;

    @Autowired
    private MFTDistributionDao mftDistributionDao;

    @Autowired
    private MFTDistributionTypeDao mftDistributionTypeDao;

    @Autowired
    private DepartmentDao departmentDao;

    @InitBinder
    public void initBinder( WebDataBinder binder, WebRequest request )
    {
        binder.registerCustomEditor( Date.class, new CustomDateEditor(
            new SimpleDateFormat( "yyyy-MM-dd" ), true ) );
    }

    @RequestMapping("/department/{dept}/mft/overview")
    public String overview()
    {
        return "mft/overview";
    }

    @RequestMapping("/department/{dept}/mft/score")
    public String score( @PathVariable String dept,
        @RequestParam(required = false) Date date, ModelMap models )
    {
        Department department = departmentDao.getDepartment( dept );
        List<Date> dates = mftScoreDao.getDates( department );
        if( dates.size() == 0 ) return "mft/score";

        if( date == null ) date = dates.get( 0 );
        List<MFTScore> scores = mftScoreDao.getScores( department, date );
        getPercentile( department, date, scores );

        models.put( "dates", dates );
        models.put( "selectedDate", date );
        models.put( "scores", scores );

        List<Integer> years = mftScoreDao.getYears( department );
        if( years.size() > 0 )
        {
            Integer beginYear = years.size() < 6 ? years.get( 0 )
                : years.get( years.size() - 6 );
            Integer endYear = years.get( years.size() - 1 );
            models.put( "beginYear", beginYear );
            models.put( "endYear", endYear );
            models.put( "years", years );
        }

        return "mft/score";
    }

    @RequestMapping("/department/{dept}/mft/score/chart")
    public String chart( @PathVariable String dept,
        @RequestParam Integer beginYear, @RequestParam Integer endYear,
        ModelMap models )
    {
        Department department = departmentDao.getDepartment( dept );
        List<MFTScore> medianScores = mftScoreDao.getMedianScores( department,
            beginYear, endYear );
        getPercentile( department, medianScores );

        Chart chart = new Chart( "MFT Median Score Percentile (" + beginYear
            + "-" + endYear + ")", "Year", "National Percentile" );
        chart.getxAxis().setCategories( beginYear, endYear );
        chart.getyAxis().setMax( 100 );
        List<Integer> medianPercentiles = new ArrayList<Integer>();
        for( MFTScore medianScore : medianScores )
            medianPercentiles.add( medianScore.getPercentile() );
        chart.getSeries().add(
            new Series( "Median Percentile", medianPercentiles, false ) );

        models.put( "chart", chart );
        return "jsonView";
    }

    private void getPercentile( Department department, List<MFTScore> scores )
    {
        MFTDistributionType distType = mftDistributionTypeDao.getDistributionType(
            department, "Student" );
        if( distType == null ) return;

        for( MFTScore score : scores )
        {
            MFTDistribution distribution = mftDistributionDao.getDistribution(
                score.getDate(), distType );
            if( distribution != null && !distribution.isDeleted() )
                score.setPercentile( distribution.getPercentile( (double) score.getValue() ) );
        }
    }

    private void getPercentile( Department department, Date date,
        List<MFTScore> scores )
    {
        MFTDistributionType distType = mftDistributionTypeDao.getDistributionType(
            department, "Student" );
        if( distType == null ) return;

        MFTDistribution distribution = mftDistributionDao.getDistribution(
            date, distType );
        if( distribution == null || distribution.isDeleted() ) return;

        for( MFTScore score : scores )
            score.setPercentile( distribution.getPercentile( (double) score.getValue() ) );
    }

}
