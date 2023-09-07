package org.lessons.nations;

import java.sql.*;
import java.util.Scanner;


public class Main {

    private final static String url = "jdbc:mysql://localhost:3306/db-nations";
    private final static String user = "root";
    private final static String password = "Jack1997";

    private final static String sql = "select countries.country_id, countries.name as nome_paese, regions.name as nome_regione, continents.name as nome_continente from countries \n" +
            "join regions on regions.region_id = countries.region_id\n" +
            "join continents on continents.continent_id = regions.continent_id \n" +
            "where countries.name like ?\n" +
            "order by nome_paese;";

    private final static String squlStats = "select languages.`language`, countries.name, country_stats.population, country_stats.`year`, country_stats.gdp  from countries\n" +
            "join country_stats on country_stats.country_id = countries.country_id\n" +
            "join country_languages on country_languages.country_id = countries.country_id\n" +
            "join languages on languages.language_id = country_languages.language_id\n" +
            "where countries.country_id = ?;";

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        System.out.println("quale paese vuoi cercare?");
        String search = scan.nextLine();
        System.out.println("cerco per: " + "'" + search + "'");


        try (Connection conn = DriverManager.getConnection(url, user, password)) {


            try(PreparedStatement ps = conn.prepareStatement(sql)){

                ps.setString(1, "%" + search + "%");

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

            System.out.println("scegli un id del paese per visualizzare maggiori informaiozni");
            String idChoice = scan.nextLine();

            try(PreparedStatement ps = conn.prepareStatement(squlStats)){
                ps.setString(1, idChoice);

                try(ResultSet rs = ps.executeQuery()){

                        System.out.println("informazioni sul paese con id" + idChoice + ":");
                        String nomePaese = rs.getString("paese");
                        String lingua = rs.getString("lingua");
                        int popolazione = rs.getInt("popolazione");
                        int anno = rs.getInt("year");
                        int gdp = rs.getInt("GDP");
                        System.out.println(nomePaese + "lingue" + lingua + "popolazione" + popolazione + " anno " + anno + "GDP" + gdp);
                }
            }


        } catch (SQLException e){

            System.out.println("unable to connect" );
            e.printStackTrace();

        }


    }
}
