package csns.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelReader {

    private Workbook workbook;

    private Sheet sheet;

    private int rows, cols;

    private int current;

    private String[] currentRow;

    Map<String, Integer> colIndexes;

    private DataFormatter dataFormatter;

    private static final Logger logger = LoggerFactory
        .getLogger( ExcelReader.class );

    public ExcelReader( InputStream input )
    {
        try
        {
            workbook = WorkbookFactory.create( input );
            sheet = workbook.getSheetAt( 0 );
            rows = sheet.getPhysicalNumberOfRows();
            cols = sheet.getRow( 0 ).getPhysicalNumberOfCells();
            dataFormatter = new DataFormatter();

            // First row should be the header row
            current = -1;
            next();
            logger.debug( Arrays.toString( currentRow ) );

            colIndexes = new HashMap<String, Integer>();
            for( int i = 0; i < currentRow.length; ++i )
                colIndexes.put( currentRow[i].toUpperCase().trim(), i );
        }
        catch( Exception e )
        {
            rows = 0;
            cols = 0;
            logger.error( "Failed to read Excel file", e );
        }
    }

    public void close()
    {
        try
        {
            workbook.close();
        }
        catch( IOException e )
        {
            logger.error( "Failed to close Excel file", e );
        }
    }

    public boolean hasNext()
    {
        return current + 1 < rows;
    }

    public boolean next()
    {
        if( ++current >= rows ) return false;

        currentRow = new String[cols];
        for( int i = 0; i < cols; ++i )
            currentRow[i] = dataFormatter
                .formatCellValue( sheet.getRow( current ).getCell( i ) ).trim();

        return true;
    }

    public String get( int colIndex )
    {
        return currentRow[colIndex];
    }

    public String get( String colName )
    {
        return currentRow[colIndexes.get( colName.toUpperCase() )];
    }

    public String[] getRow()
    {
        return currentRow;
    }

    public boolean hasColumn( String colName )
    {
        return colIndexes.keySet().contains( colName.toUpperCase() );
    }

}
