package fr.armotik.naurelliamoderation.utiles;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExceptionsManager {

    private static final Logger logger = Logger.getLogger(ExceptionsManager.class.getName());

    private static void getStackTrace(StackTraceElement[] stackTraceElements) {

        for (StackTraceElement stackTraceElement : stackTraceElements) {

            logger.log(Level.INFO, "At " + stackTraceElement + "\n");
        }
    }

    private ExceptionsManager(){
        throw new IllegalStateException("Utility Class");
    }

    /**
     * Handle SQL exceptions
     * @param e SQLException
     */
    public static void sqlExceptionLog(SQLException e) {

        logger.log(Level.SEVERE, "SQLException \nError Code N°" + e.getErrorCode() +  "\nSQLState : " + e.getSQLState() + "\nMessage : " +e.getMessage() + "\n");

        getStackTrace(e.getStackTrace());
    }

    /**
     * Handle File Not Found exceptions
     * @param e FileNotFoundException
     */
    public static void fileNotFoundExceptionLog(FileNotFoundException e) {

        logger.log(Level.SEVERE, "FileNotFoundException \nMessage : " + e.getMessage());

        getStackTrace(e.getStackTrace());
    }

    /**
     * Handle IOExceptions
     * @param e IOException
     */
    public static void ioExceptionLog(IOException e) {

        logger.log(Level.SEVERE, "IOException \nMessage : " + e.getMessage());

        getStackTrace(e.getStackTrace());
    }

    /**
     * Handle ParseException
     * @param e ParseException
     */
    public static void parseExceptionLog(ParseException e) {

        logger.log(Level.SEVERE, "ParseException \nErrorOffset : " + e.getErrorOffset() + "\nMessage : " + e.getMessage());

        getStackTrace(e.getStackTrace());
    }

    /**
     * Handle NumberFormatException
     * @param e NumberFormatException
     */
    public static void numberFormatExceptionLog(NumberFormatException e) {

        logger.log(Level.SEVERE, "ParseException \nMessage : " + e.getMessage());

        getStackTrace(e.getStackTrace());
    }

    /**
     * Handle NoSuchFieldException
     * @param e NoSuchFiledException
     */
    public static void noSuchFieldExceptionLog(NoSuchFieldException e) {

        logger.log(Level.SEVERE, "NoSuchFieldException \nMessage : " + e.getMessage());

        getStackTrace(e.getStackTrace());
    }

    /**
     * Handle IllegalAccessException
     * @param e IllegalAccessException
     */
    public static void illegalAccessExceptionLog(IllegalAccessException e) {

        logger.log(Level.SEVERE, "IllegalAccessException \nMessage : " + e.getMessage());

        getStackTrace(e.getStackTrace());
    }
}
