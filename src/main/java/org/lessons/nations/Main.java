package org.lessons.nations;

import java.sql.*;

public class Main {

    private final static String url = "jdbc:mysql://localhost:3306/db-nations";
    private final static String user = "root";
    private final static String password = "Jack1997";

    private final static String sql = "select country_id, countries.name as nome_paese, regions.name as nome_regione, continents.name as nome_continente from countries \n" +
            "join regions on regions.region_id = countries.region_id\n" +
            "join continents on continents.continent_id = regions.region_id \n" +
            "order by nome_paese;";

    public static void main(String[] args) {

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            try(PreparedStatement ps = conn.prepareStatement(sql)){

               try(ResultSet rs = ps.executeQuery()){
                   System.out.println("COUNTRY" +" | " + "ID" + " | " + "REGIONE"+ " | " + "CONTINENT");
                   while (rs.next()) {
                       String nomePaese = rs.getString("nome_paese");
                       int idPaese = rs.getInt("country_id");
                       String nomeRegione = rs.getString("nome_regione");
                       String nomeContinente = rs.getString("nome_continente");
                       System.out.println(nomePaese + " | " + idPaese + " | "+ nomeRegione + " | " + nomeContinente);
                   }
               }
           }

        } catch (SQLException e){

            System.out.println("unable to connect" );
            e.printStackTrace();

        }


    }
}
