package com.apacheJena;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.util.FileManager;

public class RdfReader {

    public void readRdfFile() {
        FileManager.get().addLocatorClassLoader(RdfReader.class.getClassLoader());
        Model model = FileManager.get().loadModel("static/rdfResources/IlieVieru.rdf");

        String queryString =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-sytax-ns#> " +
                        "PREFIX foaf: <http://xmlns.com/foaf/0.1/> " +
                        "SELECT * " +
                        "where {?Description foaf:lastName ?x}";

        Query query = QueryFactory.create(queryString);
        QueryExecution queryExecution = QueryExecutionFactory.create(query, model);
        ResultSet resultSet = queryExecution.execSelect();
        while (resultSet.hasNext()) {
            QuerySolution solution = resultSet.nextSolution();
            System.out.println(solution + "");
        }
    }
}
