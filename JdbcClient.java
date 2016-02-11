/**
 *
 *
 */
 
   

/**
 * Ligne de commande pour lancer le code :
 * > "C:\Program Files\Java\jdk1.7.0_45\bin\java" -classpath ".;sqlite-jdbc-3.8.11.2.jar" JdbcClient
 */
 
/**
 * Ligne de commande pour compiler le code :
 * > "C:\Program Files\Java\jdk1.7.0_45\bin\javac" JdbcClient.java
 */

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.DriverManager;

import java.util.Properties;
import java.util.Enumeration;

import java.lang.Math;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * Client JDBC.
 * 
 * Un client pour tester des connecteurs JDBC.
 *  
 * @author Marc Sibert
 * @version 0.1
 */ 
public class JdbcClient {
    
  private static void howTo() {
    System.out.print( new StringBuffer()
      .append("Ligne de commande attendue :\n")
      .append("  java [-D<property>=<value>] JdbcClient <chemin jdbc>\n")
      .append("    -D<property>=<value> : Proprietes definies pour la connection jdbc,\n")
      .append("                           comme <user> ou <password>, suivant les valeurs\n")
      .append("                           acceptees par le driver JDBC designe dans le chemin jdbc\n")
      .append("\n")
      .append("Après le lancement de l'application, les requêtes SQL sont saisies\n")
      .append("en ligne de commande et terminées par un point-virgule.\n")
      .toString() 
    );
  }

/**
 * Affiche le résultat de la requête sous la forme d'un tableau.
 * @param res ResultSet de la requête.
 * @throws SQLexception en cas d'erreur SQL pendant l'analys de la réponse.
 * @throws IOException en cas d'erreur de saisie.   
 */  
  private static void executeSql(ResultSet res) throws SQLException, IOException {
    ResultSetMetaData rsmd = res.getMetaData();
    int numberOfColumns = rsmd.getColumnCount();

// Génération des headers
    System.out.print("|");
    for (int i = 0; i < numberOfColumns; ++i) {
      StringBuffer sb = new StringBuffer(" " + rsmd.getColumnLabel(i+1) + " ");
      int w = Math.min(20, rsmd.getColumnDisplaySize(i+1));
      while (sb.length() < w) {
          sb.append(' ');
      }
      sb.append('|');
      System.out.print(sb.toString());
    }
    System.out.println();

// Génération de la ligne de séparation
    System.out.print("+");
    for (int i = 0; i < numberOfColumns; ++i) {
      StringBuffer sb = new StringBuffer();
      int w = Math.min(20, rsmd.getColumnDisplaySize(i+1));
      while (sb.length() < w) {
          sb.append('-');
      }
      sb.append('+');
      System.out.print(sb.toString());
    }
    System.out.println();

// Génération des lignes
    while (res.next()) {
      System.out.print("|");
      for (int i = 0; i < numberOfColumns; ++i) {
        StringBuffer sb = new StringBuffer(" " + res.getString(i+1) + " ");
        int w = Math.min(20, rsmd.getColumnDisplaySize(i+1));
        while (sb.length() < w) {
            sb.append(' ');
        }
        sb.append('|');
        System.out.print(sb.toString());
      }
      System.out.println();
    }
  }   
    
/**
 * Fonction principale.
 * Elle assure la lecture des requetes SQL et les transmet au serveur(s) identifiés
 * dans le chemin jdbc transmis en paramètre.
 * @param args Doit contenir le chemin jdbc dans le premier paramètre.    
 */
  public static void main(String[] args) throws IOException {
    if (args.length != 1) {
      System.out.println("Erreur car l'application attend le chemin JDBC en paramètre.");
      System.out.println("");
      howTo();
      System.exit(1);
    }

    String url = args[0];
    Properties props = new Properties(System.getProperties());

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    StringBuffer sbSql = new StringBuffer();

    try {
      Connection con = DriverManager.getConnection(url, props);
      Statement stmt = con.createStatement();

      System.out.print("> ");
      String ligne = null;
      while (( ligne = reader.readLine()) != null ) {
        int p = ligne.indexOf(";");
        if (p == -1) {
          sbSql.append(ligne);
          sbSql.append(' ');
        } else {
          if (p > 0) sbSql.append(ligne.substring(0, p));
          try {
            executeSql(stmt.executeQuery( sbSql.toString() ));
          } catch (SQLException e) {
            System.err.println(e.getMessage());
            System.err.println(sbSql.toString());
          }   
          sbSql = new StringBuffer();
          sbSql.append(ligne.substring(p+1));
        }          
        System.out.print("> ");
      }
      
    } catch (SQLException e) {
      e.printStackTrace(System.err);
      System.err.println(e.getMessage());
      System.exit(1);
    }

  }
}

    
