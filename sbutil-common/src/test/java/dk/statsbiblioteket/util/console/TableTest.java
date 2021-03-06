package dk.statsbiblioteket.util.console;


import junit.framework.TestCase;

/**
 * Test the Table class
 */

public class TableTest extends TestCase {

    //FIXME: Write a set of real unit tests for the Table class


    public void testNothing() {
        Table table = new Table("Foo");
        table.appendColumns("Barisimo", "QQ");

        /*table.setHeaderFormatter(new Formatter(Color.BLUE, Color.RED, Hint.BRIGHT));
        Formatter delimForm = new Formatter();
        delimForm.setHint(Hint.BRIGHT);
        delimForm.setForeground(Color.GREEN);
        delimForm.setBackground(Color.BLACK);
        table.setDelimiterFormatter(delimForm);
        table.setDataFormatter(new Formatter(Color.WHITE, Color.YELLOW));*/

        table.setDelimiter(" | ");
        table.appendRow("Foo", "gosh",
                        "Barisimo", "1");
        table.appendRow("Foo", "Frobnicated!",
                        "QQ", "Peekabo",
                        "KablooeyGabooey", "GarbageDontShowMe!!!!!!!!!!!!!!");
        table.appendRow("Foo", null);
        System.out.println(table.toString());

    }
}