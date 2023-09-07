package org.lessons.nations;

import java.sql.*;

public class Main {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/db_university";
        String user = "root";
        String password = "Jack1997";


        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "select country_id, countries.name as nome_paese, regions.name as nome_regione, continents.name as nome_continente from countries \n" +
                    "join regions on regions.region_id = countries.region_id\n" +
                    "join continents on continents.continent_id = regions.region_id \n" +
                    "order by nome_paese;";

           try(PreparedStatement ps = conn.prepareStatement(sql)){

               ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    String nomePaese = rs.getString("nome_paese");
                    String idPaese = rs.getString("country_id");
                    String nomeRegione = rs.getString("nome_regione");
                    String nomeContinente = rs.getString("nome_continente");
                    System.out.println(nomePaese);
                }
           }

        } catch (SQLException e){
            System.out.println("unable to connect");
        }


    }
}
