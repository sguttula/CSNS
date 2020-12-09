package csns.web.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import csns.model.assessment.MFTDistributionType;
import csns.model.assessment.dao.MFTDistributionTypeDao;

@Component("mftDistributionTypePropertyEditor")
@Scope("prototype")
public class MFTDistributionTypePropertyEditor extends PropertyEditorSupport {

    @Autowired
    MFTDistributionTypeDao mftDistributionTypeDao;

    @Override
    public void setAsText( String text ) throws IllegalArgumentException
    {
        if( StringUtils.hasText( text ) )
            setValue( mftDistributionTypeDao.getDistributionType( Long.valueOf( text ) ) );
    }

    @Override
    public String getAsText()
    {
        MFTDistributionType distributionType = (MFTDistributionType) getValue();
        return distributionType != null ? distributionType.getId().toString()
            : "";
    }

}
