package csns.util;

import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParsePosition;

public class RomanNumberFormat extends Format {

    private static final long serialVersionUID = 1L;

    static char A2R[][] = { { 0, 'M' }, { 0, 'C', 'D', 'M' },
        { 0, 'X', 'L', 'C' }, { 0, 'I', 'V', 'X' }, };

    public String format( double n )
    {
        return format( (long) n );
    }

    public String format( long n )
    {
        if( n <= 0 || n >= 4000 )
            throw new NumberFormatException( n + " must be > 0 && < 4000" );
        StringBuffer sb = new StringBuffer();
        format( Integer.valueOf( (int) n ), sb, new FieldPosition(
            NumberFormat.INTEGER_FIELD ) );
        return sb.toString();
    }

    public StringBuffer format( Object on, StringBuffer sb, FieldPosition fp )
    {
        if( !(on instanceof Number) )
            throw new IllegalArgumentException( on + " must be a Number object" );
        if( fp.getField() != NumberFormat.INTEGER_FIELD )
            throw new IllegalArgumentException( fp
                + " must be FieldPosition(NumberFormat.INTEGER_FIELD" );
        int n = ((Number) on).intValue();

        // First, put the digits on a tiny stack. Must be 4 digits.
        for( int i = 0; i < 4; i++ )
        {
            int d = n % 10;
            push( d );
            n = n / 10;
        }

        for( int i = 0; i < 4; i++ )
        {
            int ch = pop();
            if( ch == 0 )
                continue;
            else if( ch <= 3 )
            {
                for( int k = 1; k <= ch; k++ )
                    sb.append( A2R[i][1] ); // I
            }
            else if( ch == 4 )
            {
                sb.append( A2R[i][1] ); // I
                sb.append( A2R[i][2] ); // V
            }
            else if( ch == 5 )
            {
                sb.append( A2R[i][2] ); // V
            }
            else if( ch <= 8 )
            {
                sb.append( A2R[i][2] ); // V
                for( int k = 6; k <= ch; k++ )
                    sb.append( A2R[i][1] ); // I
            }
            else
            { // 9
                sb.append( A2R[i][1] );
                sb.append( A2R[i][3] );
            }
        }
        return sb;
    }

    /** Parse a generic object, returning an Object */
    public Object parseObject( String what, ParsePosition where )
    {
        throw new IllegalArgumentException( "Parsing not implemented" );
    }

    /* Implement a toy stack */
    protected int stack[] = new int[10];

    protected int depth = 0;

    /* Implement a toy stack */
    protected void push( int n )
    {
        stack[depth++] = n;
    }

    /* Implement a toy stack */
    protected int pop()
    {
        return stack[--depth];
    }

}
