package neu;
/* ************************************************************************* *\
*                Programmierung 1 HS 2016 - Serie 4-1                         * 
\* ************************************************************************* */


/**
 * Basis enum to provide player tokens
 */
public enum Token {
       empty( " " ),
       player1( "O" ),
       player2( "X" );
       private String representation; // string representation of value
       Token( String s ) { this.representation = s; }
       public String toString() { return this.representation; }
}
